package com.homegrown.coding.compiler.lang.script.normal.scope;

import org.antlr.v4.runtime.ParserRuleContext;

/**
 * 符号：编译过程中产生的变量、函数、类、块，都可被称作符号
 * @author youyu
 */
public abstract class Symbol {
    //符号名称
    protected String name;
    //所属作用域
    protected Scope enclosingScope;
    //可见性，比如public还是private
    protected int visibility = 0;
    //Symbol关联的AST节点
    private ParserRuleContext ctx;

    public String getName() {
        return name;
    }

    public Scope getEnclosingScope() {
        return enclosingScope;
    }

    public ParserRuleContext getCtx() {
        return ctx;
    }

    public void setCtx(ParserRuleContext ctx) {
        this.ctx = ctx;
    }
}
