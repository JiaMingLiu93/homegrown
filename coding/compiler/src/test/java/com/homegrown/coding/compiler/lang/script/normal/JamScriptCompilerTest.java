package com.homegrown.coding.compiler.lang.script.normal;

import org.junit.Before;
import org.junit.Test;

/**
 * @author youyu
 */
public class JamScriptCompilerTest {
    private JamScriptCompiler jamScriptCompiler;

    @Before
    public void init(){
        jamScriptCompiler = new JamScriptCompiler();
    }

    @Test
    public void testParseSimpleScript(){
        jamScriptCompiler.compile("int a=1;");
    }

    /**
     * 测试作用域
     */
    @Test
    public void testScope(){
        AnnotatedTree annotatedTree = jamScriptCompiler.compile("int age=18;for(int i=0;i<10;i++){age = age+2;}int i = 8;");
        Object result = jamScriptCompiler.execute(annotatedTree);
    }
}
