package com.homegrown.coding.compiler.lang.script.normal.scope;

import java.util.LinkedList;
import java.util.List;

/**
 * 作用域
 * @author youyu
 */
public abstract class Scope extends Symbol{
    // 该Scope中的成员，包括变量、方法、类等。
    protected List<Symbol> symbols = new LinkedList<>();

    /**
     * 向scope中添加符号，同时设置好该符号的enclosingScope
     * @param symbol
     */
    protected void addSymbol(Symbol symbol){
        symbols.add(symbol);
        symbol.enclosingScope = this;
    }

    /**
     * 获取Variable
     * @param name
     * @return
     */
    public Variable getVariable(String name){
        return getVariable(this,name);
    }

    protected static Variable getVariable(Scope scope, String name){
        for (Symbol s : scope.symbols) {
            if (s instanceof Variable && s.name.equals(name)){
                return (Variable) s;
            }
        }
        return null;
    }

    public List<Symbol> getSymbols() {
        return symbols;
    }

    /**
     * 是否包含某个Symbol
     * @param symbol
     * @return
     */
    public boolean containsSymbol(Symbol symbol){
        return symbols.contains(symbol);
    }

    @Override
    public String toString(){
        return "Scope: " + name;
    }
}
