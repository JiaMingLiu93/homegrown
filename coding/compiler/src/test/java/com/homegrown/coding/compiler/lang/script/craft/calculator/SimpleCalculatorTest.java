package com.homegrown.coding.compiler.lang.script.craft.calculator;

import org.junit.Test;

/**
 * @author youyu
 */
public class SimpleCalculatorTest {

    @Test
    public void testExpression(){
        SimpleCalculator simpleCalculator = new SimpleCalculator();
        String script = "2+3*5";
        System.out.println("\n计算: " + script + "，看上去一切正常。");
        simpleCalculator.evaluate(script);
    }

    @Test
    public void testAssociative(){
        System.out.println("\n计算: " + "2+3+4" + "，结合性出现错误。");
        SimpleCalculator simpleCalculator = new SimpleCalculator();
        simpleCalculator.evaluate("2+3+4");
    }

    @Test
    public void testGrammarException(){
        String script = "2+";
        System.out.println("\n: " + script + "，应该有语法错误。");
        new SimpleCalculator().evaluate(script);
    }

    @Test
    public void testParseVariableDeclare(){
        String script = "int a=b+3;";
        System.out.println("解析变量声明语句: " + script);
    }
}
