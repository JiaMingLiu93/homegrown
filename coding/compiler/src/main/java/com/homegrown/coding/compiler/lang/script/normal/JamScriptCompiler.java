package com.homegrown.coding.compiler.lang.script.normal;

import com.homegrown.coding.compiler.lang.script.normal.scope.TypeAndScopeScanner;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

/**
 * @author youyu
 */
public class JamScriptCompiler {
    private AnnotatedTree at;
    private JamScriptLexer lexer;
    private JamScriptParser parser;

    public AnnotatedTree compile(String script) {
        at = new AnnotatedTree();

        //词法分析
        lexer = new JamScriptLexer(CharStreams.fromString(script));
        CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);

        //语法分析
        parser = new JamScriptParser(commonTokenStream);
        at.setAst(parser.prog());

        //语义分析
        ParseTreeWalker walker = new ParseTreeWalker();

        TypeAndScopeScanner typeAndScopeScanner = new TypeAndScopeScanner(at);
        walker.walk(typeAndScopeScanner,at.getAst());

        dumpAST();
        dumpSymbols();


        return at;
    }

    public Object execute(AnnotatedTree annotatedTree) {
        ASTEvaluator astEvaluator = new ASTEvaluator(annotatedTree);
        return astEvaluator.visit(at.getAst());
    }

    /**
     * 打印AST，以lisp格式
     */
    public void dumpAST(){
        if (at!=null) {
            System.out.println(at.getAst().toStringTree(parser));
        }
    }

    /**
     * 打印符号表
     */
    public void dumpSymbols(){
        if (at != null){
            System.out.println(at.getScopeTreeString());
        }
    }
}
