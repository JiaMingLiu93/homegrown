package com.homegrown.coding.compiler.lang.script.normal.scope;

import com.homegrown.coding.compiler.lang.script.normal.Type;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * 用来表示"this"关键字的符号
 * @author youyu
 */
public class This extends Variable{
    This(Class theClass, ParserRuleContext ctx){
        super("this", theClass, ctx);
    }

    private Class Class(){
        return (Class) enclosingScope;
    }
}
