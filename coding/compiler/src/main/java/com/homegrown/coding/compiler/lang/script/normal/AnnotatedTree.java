package com.homegrown.coding.compiler.lang.script.normal;

import com.homegrown.coding.compiler.lang.script.normal.scope.NameSpace;
import com.homegrown.coding.compiler.lang.script.normal.scope.Scope;
import com.homegrown.coding.compiler.lang.script.normal.scope.Symbol;
import com.homegrown.coding.compiler.lang.script.normal.scope.Variable;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.HashMap;
import java.util.Map;

/**
 * 注释树
 * 语义分析的结果都放在这里。跟AST的节点建立关联。包括：
 * 1.类型信息，包括基本类型和用户自定义类型
 * 2.变量和函数调用的消解
 * 3.作用域Scope。在Scope中包含了该作用域的所有符号。Variable、Function、Class等都是符号
 * @author youyu
 */
public class AnnotatedTree {
    //AST
    private ParseTree ast;

    // 全局命名空间
    private NameSpace nameSpace = null;

    // AST节点对应的Symbol
    private Map<ParserRuleContext, Symbol> symbolOfNode = new HashMap<>();

    // AST节点对应的Scope，如for、函数调用会启动新的Scope
    private Map<ParserRuleContext, Scope> node2Scope = new HashMap<>();

    // 用于做类型推断，每个节点推断出来的类型
    private Map<ParserRuleContext, Type> typeOfNode = new HashMap<ParserRuleContext, Type>();

    public void setNameSpace(NameSpace nameSpace) {
        this.nameSpace = nameSpace;
    }

    public void node2Scope(ParserRuleContext node,Scope scope){
        node2Scope.put(node,scope);
    }

    public Scope getScopeWith(ParserRuleContext node){
        return node2Scope.get(node);
    }

    public Symbol getSymbolWith(ParserRuleContext node){
        return symbolOfNode.get(node);
    }

    public Type getTypeWith(ParserRuleContext node){
        return typeOfNode.get(node);
    }

    public ParseTree getAst() {
        return ast;
    }

    public void setAst(ParseTree ast) {
        this.ast = ast;
    }

    /**
     * 输出本Scope中的内容，包括每个变量的名称、类型。
     * @return 树状显示的字符串
     */
    public String getScopeTreeString(){
        StringBuffer sb = new StringBuffer();
        scopeToString(sb, nameSpace, "");
        return sb.toString();
    }

    private void scopeToString(StringBuffer sb, Scope scope, String indent){
        sb.append(indent).append(scope).append('\n');
        for (Symbol symbol : scope.getSymbols()){
            if (symbol instanceof Scope){
                scopeToString(sb, (Scope)symbol, indent+"\t");
            }
            else{
                sb.append(indent).append("\t").append(symbol).append('\n');
            }
        }
    }

    /**
     * 通过名称查找Variable。逐级Scope查找。
     *
     * @param scope
     * @param idName
     * @return
     */
    public Variable lookupVariable(Scope scope, String idName) {
        Variable rtn = scope.getVariable(idName);

        if (rtn == null && scope.getEnclosingScope() != null) {
            rtn = lookupVariable(scope.getEnclosingScope(), idName);
        }
        return rtn;
    }
}
