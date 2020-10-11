package com.homegrown.coding.compiler.lang.script.craft.parser;

import com.homegrown.coding.compiler.lang.script.craft.calculator.ASTNode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author youyu
 */
public class SimpleParserTest {

    private SimpleParser parser;
    @Before
    public void init(){
        parser = new SimpleParser();
    }

    @Test
    public void testIntDeclare() throws Exception {
        String declare = "int a=1+1;";
        ASTNode tree = parser.parse(declare);
        parser.dumpAST(tree,"");
    }

    @Test
    public void testExpressionStatement() throws Exception {
        String expression = "1+3;";
        ASTNode tree = parser.parse(expression);
        parser.dumpAST(tree,"");
    }

    @Test
    public void testAssignmentStatement() throws Exception {
        String assignment = "a=1+1;";
        ASTNode tree = parser.parse(assignment);
        parser.dumpAST(tree,"");
    }
}
