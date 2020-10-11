package com.homegrown.coding.compiler.lang.script.craft.calculator;

import com.homegrown.coding.compiler.lang.script.craft.lexer.SimpleLexer;
import com.homegrown.coding.compiler.lang.script.craft.lexer.Token;
import com.homegrown.coding.compiler.lang.script.craft.lexer.TokenReader;
import com.homegrown.coding.compiler.lang.script.craft.lexer.TokenType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 实现一个计算器，但计算的结合性是有问题的。因为它使用了下面的语法规则：
 *
 * additive -> multiplicative | multiplicative + additive
 * multiplicative -> primary | primary * multiplicative
 *
 * 递归项在右边，会自然的对应右结合。我们真正需要的是左结合
 * @author youyu
 */
public class SimpleCalculator {

    /**
     * 执行脚本，并打印输出AST和求值过程
     * @param script 输入脚本
     */
    public void evaluate(String script) {
        //语法分析，上下文无关文法转换成算法
        //1. 输入是什么，输出是什么。输入一个字符串类型的表达式，输出一个AST节点，先简单定义一个节点
        ASTNode tree = parse(script);
        dumpAST(tree,"");
        evaluate(tree,"");

    }

    /**
     * 对某个AST节点求值，并打印求值过程
     * @param node 要求值的节点
     * @param indent 打印输出时的缩进量，用tab控制
     */
    private int evaluate(ASTNode node, String indent) {
        int result = 0;
        System.out.println(indent + "Calculating: " + node.getType());

        ASTNode left;
        ASTNode right;
        int leftValue;
        int rightValue;

        switch (node.getType()){
            case Program:
                for (ASTNode child:node.getChildren()){
                    result = evaluate(child,indent+"\t");
                }
                break;
            case Additive:
                left = node.getChildren().get(0);
                leftValue = evaluate(left, indent+"\t");
                right = node.getChildren().get(1);
                rightValue = evaluate(right, indent+"\t");
                if (node.getText().equals("+")){
                    result = leftValue + rightValue;
                }else {
                    result = leftValue - rightValue;
                }
                break;
            case Multiplicative:
                left = node.getChildren().get(0);
                leftValue = evaluate(left, indent+"\t");
                right = node.getChildren().get(1);
                rightValue = evaluate(right, indent+"\t");
                if (node.getText().equals("*")){
                    result = leftValue * rightValue;
                }else {
                    result = leftValue / rightValue;
                }
                break;
            case IntLiteral:
                result = Integer.parseInt(node.getText());
                break;
            default:
        }
        System.out.println(indent + "Result: " + result);
        return result;
    }

    /**
     * 解析脚本，并返回根节点
     * @param script
     * @return
     */
    private ASTNode parse(String script) {
        //词法分析
        SimpleLexer lexer = new SimpleLexer();
        TokenReader reader = lexer.tokenize(script);
        //语法分析
        return parseProgram(reader);
    }

    /**
     * 语法解析：根节点
     * @param reader
     * @return
     */
    private ASTNode parseProgram(TokenReader reader) {
        ASTNode root = new SimpleASTNode(ASTNodeType.Program, "Program");
        try {
            ASTNode child = parseAdditive(reader);
            if (child != null){
                root.addChild(child);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return root;
    }

    /**
     * 语法解析：加法表达式
     * @param reader
     * @return
     * @throws Exception
     */
    private ASTNode parseAdditive(TokenReader reader) throws Exception{
        ASTNode multiNode = parseMultiplicative(reader);
        ASTNode node = multiNode;

        Token token = reader.peek();

        if (multiNode != null && token != null){
            if (token.getType() == TokenType.Plus || token.getType() == TokenType.Minus) {
                //消耗当前token
                reader.read();
                ASTNode addNode = parseAdditive(reader);
                if (addNode != null){
                    node = new SimpleASTNode(ASTNodeType.Additive, token.getText());
                    node.addChild(multiNode);
                    node.addChild(addNode);
                }else {
                    throw new RuntimeException("invalid additive expression, expecting the right part.");
                }
            }
        }
        return node;
    }

    private ASTNode parseMultiplicativeAndAdditive(TokenReader reader) {

        return null;
    }

    /**
     * 语法解析：乘法表达式
     * @param reader
     * @return
     * @throws Exception
     */
    private ASTNode parseMultiplicative(TokenReader reader) throws Exception{
        ASTNode primaryNode = parsePrimary(reader);
        ASTNode node = primaryNode;

        Token token = reader.peek();

        if (primaryNode != null && token != null){
            if (token.getType() == TokenType.Star || token.getType() == TokenType.Slash){
                //消耗当前token
                reader.read();
                ASTNode multiNode = parseMultiplicative(reader);
                if (multiNode != null){
                    node = new SimpleASTNode(ASTNodeType.Multiplicative, token.getText());
                    node.addChild(primaryNode);
                    node.addChild(multiNode);
                }else {
                    throw new RuntimeException("invalid multiplicative expression, expecting the right part.");
                }
            }
        }
        return node;
    }

    /**
     * 为什么会想出这个方法？
     * 因为没有意识到解析脚本的过程是一个消耗token的过程，是一个顺序的过程，或者说这个意识还不够深
     * 与其说脑力不够，还不如说是对稳定的、不变的东西的重视程度不够，大脑只能在同一时刻做一件事，需要
     * 步步为营
     * 所以，从一开始，就要知道思路是什么，这决定着如何高效正确地解决问题
     * @param reader
     * @return
     */
    private ASTNode parsePrimaryMultiplyMultiplicative(TokenReader reader) {
        return null;
    }

    /**
     * 语法解析：基础表达式
     *
     * 这个方法也做了AST的简化，就是不用构造一个primary节点，直接返回子节点。因为它只有一个子节点
     * @param reader
     * @return
     * @throws Exception
     */
    private ASTNode parsePrimary(TokenReader reader) throws Exception{
        ASTNode node = null;
        Token token = reader.peek();
        if (token.getType() == TokenType.IntLiteral){
            reader.read();
            node = new SimpleASTNode(ASTNodeType.IntLiteral,token.getText());
        }else if (token.getType() == TokenType.Identifier){
            reader.read();
            node = new SimpleASTNode(ASTNodeType.Identifier,token.getText());
        }else if (token.getType() == TokenType.LeftParen){
            reader.read();
            node = parseAdditive(reader);
            if (node != null){
                Token token1 = reader.peek();
                if (token1 != null && token1.getType() == TokenType.RightParen){
                    reader.read();
                }else {
                    throw new RuntimeException("expecting right parenthesis");
                }
            }else {
                throw new RuntimeException("expecting an additive expression inside parenthesis");
            }
        }
        return node;
    }

    /**
     * 打印输出AST的树状结构
     * @param node
     * @param indent 缩进字符，由tab组成，每一级多一个tab
     */
    private void dumpAST(ASTNode node, String indent) {
        System.out.println(indent + node.getType() + " " + node.getText());
        for (ASTNode child : node.getChildren()) {
            dumpAST(child, indent + "\t");
        }
    }

    /**
     * 一个简单的AST节点的实现。
     * 属性包括：类型、文本值、父节点、子节点。
     */
    private class SimpleASTNode implements ASTNode {
        ASTNode parent = null;
        List<ASTNode> children = new ArrayList<ASTNode>();
        List<ASTNode> readonlyChildren = Collections.unmodifiableList(children);
        ASTNodeType nodeType = null;
        String text = null;


        public SimpleASTNode(ASTNodeType nodeType, String text) {
            this.nodeType = nodeType;
            this.text = text;
        }

        @Override
        public ASTNode getParent() {
            return parent;
        }

        @Override
        public List<ASTNode> getChildren() {
            return readonlyChildren;
        }

        @Override
        public ASTNodeType getType() {
            return nodeType;
        }

        @Override
        public String getText() {
            return text;
        }

        @Override
        public void addChild(ASTNode child) {
            children.add(child);
            child.setParent(this);
        }

        @Override
        public void setParent(ASTNode parent) {
            this.parent = parent;
        }

    }
}
