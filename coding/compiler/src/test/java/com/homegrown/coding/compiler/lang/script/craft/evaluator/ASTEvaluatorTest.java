package com.homegrown.coding.compiler.lang.script.craft.evaluator;

import com.homegrown.coding.compiler.lang.script.craft.antlrtest.evaluator.ASTEvaluator;
import com.homegrown.coding.compiler.lang.script.craft.antlrtest.grammar.PlayScriptLexer;
import com.homegrown.coding.compiler.lang.script.craft.antlrtest.grammar.PlayScriptParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author youyu
 */
public class ASTEvaluatorTest {
    private PlayScriptLexer lexer;
    private PlayScriptParser parser;
    private ASTEvaluator evaluator;

    @Before
    public void init(){
        evaluator = new ASTEvaluator();
    }

    private void init(String script){
        lexer = new PlayScriptLexer(CharStreams.fromString(script));
        CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);

        parser = new PlayScriptParser(commonTokenStream);
    }

    @Test
    public void testExecuteSimpleScript(){
        init("1+2;");
        Integer result = evaluator.visit(parser.additiveExpression());
        System.out.println(result);
    }
}
