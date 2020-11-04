package com.homegrown.coding.compiler.lang.script.normal.scope;

import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 命名空间
 * @author youyu
 */
public class NameSpace extends BlockScope{
    private NameSpace parent = null;
    private List<NameSpace> subNameSpaces = new LinkedList<>();

    protected NameSpace(String name, Scope enclosingScope, ParserRuleContext ctx) {
        super.setCtx(ctx);
        this.name = name;
        this.enclosingScope = enclosingScope;
    }

    public List<NameSpace> getSubNameSpaces() {
        return Collections.unmodifiableList(subNameSpaces);
    }

    public void addSubNameSpace(NameSpace child){
        child.parent = this;
        subNameSpaces.add(child);
    }

    public void removeSubNameSpace(NameSpace child){
        child.parent = null;
        subNameSpaces.remove(child);
    }
}
