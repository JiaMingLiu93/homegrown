package com.homegrown.coding.compiler.lang.script.normal.scope;

import com.homegrown.coding.compiler.lang.script.normal.Type;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author youyu
 */
public class Function extends Scope{
    // 参数
    protected List<Variable> parameters = new LinkedList<Variable>();

    //返回值
    protected Type returnType = null;

    //闭包变量，即它所引用的外部环境变量
    protected Set<Variable> closureVariables = null;

    private List<Type> paramTypes = null;

    protected Function(String name, Scope enclosingScope, ParserRuleContext ctx) {
        super.setCtx(ctx);
        this.name = name;
        this.enclosingScope = enclosingScope;
    }

    public Set<Variable> getClosureVariables() {
        return closureVariables;
    }
}
