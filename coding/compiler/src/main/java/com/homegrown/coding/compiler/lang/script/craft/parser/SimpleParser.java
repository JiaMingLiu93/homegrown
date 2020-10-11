package com.homegrown.coding.compiler.lang.script.craft.parser;

import com.homegrown.coding.compiler.lang.script.craft.calculator.ASTNode;
import com.homegrown.coding.compiler.lang.script.craft.calculator.ASTNodeType;
import com.homegrown.coding.compiler.lang.script.craft.calculator.SimpleCalculator;
import com.homegrown.coding.compiler.lang.script.craft.lexer.SimpleLexer;
import com.homegrown.coding.compiler.lang.script.craft.lexer.Token;
import com.homegrown.coding.compiler.lang.script.craft.lexer.TokenReader;
import com.homegrown.coding.compiler.lang.script.craft.lexer.TokenType;

/**
 *  一个简单的语法解析器
 *  能够解析简单的表达式、变量声明和初始化语句、赋值语句
 *  它支持的语法规则为：
 *
 * program -> intDeclare | expressionStatement | assignmentStatement
 * intDeclare -> 'int' Id ( = additive) ';'
 * expressionStatement -> additive ';'
 * assignmentStatement -> Id = additive ';'
 * additive -> multiplicative ( (+ | -) multiplicative)*
 * multiplicative -> primary ( (* | /) primary)*
 * primary -> IntLiteral | Id | (additive)
 *
 * @author youyu
 */
public class SimpleParser {

    /**
     * 解析脚本
     * @param script
     * @throws Exception
     * @return
     */
    public ASTNode parse(String script) throws Exception{
        SimpleLexer lexer = new SimpleLexer();
        TokenReader tokens = lexer.tokenize(script);
        return parseProgram(tokens);
    }

    /**
     * AST的根节点，解析的入口
     * @param tokens
     * @return
     * @throws Exception
     */
    private ASTNode parseProgram(TokenReader tokens) throws Exception{
        SimpleASTNode root = new SimpleASTNode(ASTNodeType.Program, "program");

        while (tokens.peek()!=null){
            ASTNode child = parseIntDeclare(tokens);
            if (isNotNull(root, child)) continue;

            child = parseExpressionStatement(tokens);
            if (isNotNull(root, child)) continue;

            child = parseAssignmentStatement(tokens);
            if (isNotNull(root,child)) continue;

            if (child == null){
                throw new RuntimeException("unknown statement");
            }
        }
        return root;
    }

    /**
     * 解析赋值语句：assignmentStatement -> Id = additive ';'
     * 如：
     * a = 10*2;
     * @param tokens
     * @return
     * @throws Exception
     */
    private ASTNode parseAssignmentStatement(TokenReader tokens) throws Exception{
        SimpleASTNode node = null;
        //预读，看看下面是不是标识符
        Token token = tokens.peek();
        if (token != null && token.getType() == TokenType.Identifier) {
            //读入标识符
            tokens.read();
            node = new SimpleASTNode(ASTNodeType.AssignmentStmt,token.getText());
            //预读，看看下面是不是等号
            token = tokens.peek();
            if (token != null && token.getType() == TokenType.Assignment){
                //取出等号
                tokens.read();
                ASTNode child = parseAdditive(tokens);
                //出错，等号右面没有一个合法的表达式
                if (child == null){
                    throw new RuntimeException("invalide assignment statement, expecting an expression");
                }
                //添加子节点
                node.addChild(child);

                //预读，看看后面是不是分号
                token = tokens.peek();
                if (token != null && token.getType() == TokenType.SemiColon){
                    //消耗掉这个分号
                    tokens.read();
                }else {
                    //报错，缺少分号
                    throw new RuntimeException("invalid statement, expecting semicolon");
                }
            }else {
                //回溯，吐出之前消化掉的标识符
                tokens.unread();
                node = null;
            }
        }
        return node;
    }

    private boolean isNotNull(SimpleASTNode root, ASTNode child) {
        if (child != null){
            root.addChild(child);
            return true;
        }
        return false;
    }

    /**
     * 表达式语句，即表达式后面跟个分号：expressionStatement -> additive ';'
     * @param tokens
     * @return
     * @throws Exception
     */
    private ASTNode parseExpressionStatement(TokenReader tokens) throws Exception{
        int position = tokens.getPosition();
        ASTNode node = parseAdditive(tokens);
        if (node != null){
            Token token = tokens.peek();
            if (token != null && token.getType() == TokenType.SemiColon){
                tokens.read();
            }else {
                node = null;
                //回溯
                tokens.setPosition(position);
            }
        }
        return node;
    }

    /**
     * 整型变量声明：intDeclare -> 'int' Id ( = additive) ';'
     * 如：
     * int a;
     * int b = 2*3;
     *
     * @param tokens
     * @return
     * @throws Exception
     */
    private ASTNode parseIntDeclare(TokenReader tokens) throws Exception{
        ASTNode node = null;
        Token token = tokens.peek();
        if (token != null && token.getType() == TokenType.Int) {
            tokens.read();

            if (tokens.peek().getType() == TokenType.Identifier) {
                //取出变量
                token = tokens.read();
                node = new SimpleASTNode(ASTNodeType.IntDeclaration, token.getText());
                token = tokens.peek();
                if (token != null && token.getType() == TokenType.Assignment) {
                    //取出等号
                    tokens.read();
                    ASTNode child = parseAdditive(tokens);
                    if (child == null){
                        throw new RuntimeException("invalid variable initialization, expecting an expression");
                    }
                    node.addChild(child);
                }
            }else {
                throw new RuntimeException("variable name expected");
            }

            token = tokens.peek();
            if (token != null && token.getType() == TokenType.SemiColon){
                tokens.read();
            }else {
                throw new RuntimeException("invalid statement, expecting semicolon");
            }
        }
        return node;
    }

    /**
     * 解析加法表达式：additive -> multiplicative ( (+ | -) multiplicative)*
     *
     * 与 {@link SimpleCalculator#parseAdditive}相比，消除了递归
     * @param tokens
     * @return
     * @throws Exception
     */
    private ASTNode parseAdditive(TokenReader tokens) throws Exception{
        ASTNode child1 = parseMultiplicative(tokens);
        ASTNode node = child1;
        if (child1 != null){
            while (true){
                Token token = tokens.peek();
                if (token != null && (token.getType() == TokenType.Plus || token.getType() == TokenType.Minus)){
                    tokens.read();
                    //计算下级节点
                    ASTNode child2 = parseMultiplicative(tokens);
                    if (child2 == null){
                        throw new RuntimeException("invalid additive expression, expecting the right part.");
                    }
                    node = new SimpleASTNode(ASTNodeType.Additive, token.getText());
                    //注意，新节点在顶层，保证正确的结合性
                    //新节点这个概念要放在循环的过程中来看，每次循环，child1都是新生成的一个AST节点，所以它叫新节点
                    node.addChild(child1);
                    node.addChild(child2);

                    child1 = node;
                }else {
                    break;
                }
            }
        }
        return node;
    }

    /**
     * 解析乘法表达式：multiplicative -> primary ( (* | /) primary)*
     *
     * 与{@link com.homegrown.coding.compiler.lang.script.craft.calculator.SimpleCalculator}中的parseMultiplicative相比，
     * 消除了递归，主要是在产生式的写法上发生了变化
     *
     * @param tokens
     * @return
     * @throws Exception
     */
    private ASTNode parseMultiplicative(TokenReader tokens) throws Exception{
        ASTNode child1 = parsePrimary(tokens);
        ASTNode node = child1;

        while (true){
            Token token = tokens.peek();
            if (token != null && (token.getType() == TokenType.Star || token.getType() == TokenType.Slash)) {
                tokens.read();
                ASTNode child2 = parsePrimary(tokens);
                if (child2 == null){
                    throw new RuntimeException("invalid multiplicative expression, expecting the right part.");
                }
                node = new SimpleASTNode(ASTNodeType.Multiplicative,token.getText());
                node.addChild(child1);
                node.addChild(child2);

                //循环迭代的关键
                child1 = node;
            }else {
                break;
            }
        }
        return node;
    }

    /**
     * 解析基础表达式：primary -> IntLiteral | Id | (additive)
     * 这个方法也做了AST的简化，就是不用构造一个primary节点，直接返回子节点。因为它只有一个子节点
     * @param tokens
     * @return
     * @throws Exception
     */
    private ASTNode parsePrimary(TokenReader tokens) throws Exception{
        Token token = tokens.peek();
        ASTNode node = null;

        if (token != null){
            if (token.getType() == TokenType.IntLiteral){
                tokens.read();
                node = new SimpleASTNode(ASTNodeType.IntLiteral,token.getText());
            }else if (token.getType() == TokenType.Identifier){
                tokens.read();
                node = new SimpleASTNode(ASTNodeType.Identifier,token.getText());
            }else if (token.getType() == TokenType.LeftParen){
                tokens.read();
                node = parseAdditive(tokens);

                if (node == null){
                    throw new RuntimeException("expecting an additive expression inside parenthesis");
                }
                token = tokens.peek();
                if (token != null && token.getType() == TokenType.RightParen) {
                    tokens.read();
                }else {
                    throw new RuntimeException("expecting right parenthesis");
                }
            }
        }
        return node;
    }

    /**
     * 打印AST
     * @param node
     * @param indent
     */
    void dumpAST(ASTNode node,String indent){
        //打印本节点
        System.out.println(indent + node.getType() + " " + node.getText());
        //打印下级节点
        for (ASTNode child:node.getChildren()){
            dumpAST(child,indent+"\t");
        }
    }
}
