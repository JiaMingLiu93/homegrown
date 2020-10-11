package com.homegrown.coding.compiler.lang.script.normal.scope;

import org.antlr.v4.runtime.ParserRuleContext;

/**
 * @author youyu
 */
public class Super extends Variable{
    Super(Class theClass, ParserRuleContext ctx){
        super("super", theClass, ctx);
    }

    private Class Class(){
        return (Class) enclosingScope;
    }
}
