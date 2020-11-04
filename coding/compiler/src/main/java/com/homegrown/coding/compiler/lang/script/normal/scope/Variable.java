package com.homegrown.coding.compiler.lang.script.normal.scope;

import com.homegrown.coding.compiler.lang.script.normal.Type;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * 变量
 * @author youyu
 */
public class Variable extends Symbol{
    //变量类型
    protected Type type;

    // 作为parameter的变量的属性
    //缺省值
    protected Object defaultValue = null;

    //是否允许多次重复，这是一个创新的参数机制
    protected Integer multiplicity = 1;

    public Variable(String name, Scope enclosingScope, ParserRuleContext ctx){
        this.name = name;
        this.enclosingScope = enclosingScope;
        super.setCtx(ctx);
    }

    public Variable(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString(){
        return "Variable " + name + " -> "+ type;
    }
}
