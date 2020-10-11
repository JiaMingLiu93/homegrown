package com.homegrown.coding.compiler.lang.script.normal.scope;

import com.homegrown.coding.compiler.lang.script.normal.AnnotatedTree;
import com.homegrown.coding.compiler.lang.script.normal.JamScriptBaseListener;
import com.homegrown.coding.compiler.lang.script.normal.JamScriptParser;
import com.homegrown.coding.compiler.lang.script.normal.scope.NameSpace;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Stack;

/**
 * 第一遍扫描：识别出所有类型（包括类和函数)，以及Scope。（但函数的参数信息要等到下一个阶段才会添加进去。）
 * @author youyu
 */
public class TypeAndScopeScanner extends JamScriptBaseListener {
    private AnnotatedTree at;
    private Stack<Scope> scopeStack = new Stack<>();

    public TypeAndScopeScanner(AnnotatedTree at) {
        this.at = at;
    }

    /**
     * 在遍历树的过程中，当前的Scope
     * @return
     */
    private Scope currentScope() {
        if (scopeStack.size() > 0) {
            return scopeStack.peek();
        } else {
            return null;
        }
    }

    private Scope pushScope(Scope scope, ParserRuleContext ctx) {
        at.node2Scope(ctx, scope);

        scope.setCtx(ctx);

        scopeStack.push(scope);
        return scope;
    }

    private void popScope() {
        scopeStack.pop();
    }

    @Override
    public void enterProg(JamScriptParser.ProgContext ctx) {
        NameSpace scope = new NameSpace("", currentScope(), ctx);
        at.setNameSpace(scope);
        pushScope(scope,ctx);
    }

    @Override
    public void exitProg(JamScriptParser.ProgContext ctx) {
        popScope();
    }

    @Override
    public void enterBlock(JamScriptParser.BlockContext ctx) {
        //对于函数，不需要再额外建一个scope
        if (!(ctx.getParent() instanceof JamScriptParser.FunctionBodyContext)){
            BlockScope blockScope = new BlockScope(currentScope(), ctx);
            currentScope().addSymbol(blockScope);
            pushScope(blockScope,ctx);
        }
    }

    @Override
    public void exitBlock(JamScriptParser.BlockContext ctx) {
        if (!(ctx.parent instanceof JamScriptParser.FunctionBodyContext)) {
            popScope();
        }
    }

    @Override
    public void enterStatement(JamScriptParser.StatementContext ctx) {
        //为for建立额外的Scope
        if (ctx.FOR()!=null){
            BlockScope scope = new BlockScope(currentScope(), ctx);
            currentScope().addSymbol(scope);
            pushScope(scope, ctx);
        }
    }

    @Override
    public void exitStatement(JamScriptParser.StatementContext ctx) {
        //释放for语句的外层scope
        if (ctx.FOR() != null) {
            popScope();
        }
    }
}
