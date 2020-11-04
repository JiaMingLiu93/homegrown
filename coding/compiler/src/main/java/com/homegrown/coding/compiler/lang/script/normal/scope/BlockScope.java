package com.homegrown.coding.compiler.lang.script.normal.scope;

import org.antlr.v4.runtime.ParserRuleContext;

/**
 * 块作用域
 * @author youyu
 */
public class BlockScope extends Scope{
    //给block编号的数字
    private static int index = 1;

    protected BlockScope() {
        this.name = "block" +index++;
    }
    protected BlockScope(Scope enclosingScope, ParserRuleContext ctx) {
        super.setCtx(ctx);
        this.name = "block" +index++;
        this.enclosingScope = enclosingScope;
    }
}
