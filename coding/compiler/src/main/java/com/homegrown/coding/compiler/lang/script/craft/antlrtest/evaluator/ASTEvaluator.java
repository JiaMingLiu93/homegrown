package com.homegrown.coding.compiler.lang.script.craft.antlrtest.evaluator;

import com.homegrown.coding.compiler.lang.script.craft.antlrtest.grammar.PlayScriptBaseVisitor;
import com.homegrown.coding.compiler.lang.script.craft.antlrtest.grammar.PlayScriptParser;

/**
 * 一个Visitor类，只简单的实现了整数的加减乘除
 * @author youyu
 */
public class ASTEvaluator extends PlayScriptBaseVisitor<Integer> {
    @Override
    public Integer visitLiteral(PlayScriptParser.LiteralContext ctx) {
        if (ctx.IntegerLiteral() != null){
            return Integer.valueOf(ctx.IntegerLiteral().getText());
        }
        return 0;
    }

    @Override
    public Integer visitAdditiveExpression(PlayScriptParser.AdditiveExpressionContext ctx) {
        if (ctx.ADD() != null || ctx.SUB() != null){
            Integer left = visitAdditiveExpression(ctx.additiveExpression());
            Integer right = visitMultiplicativeExpression(ctx.multiplicativeExpression());
            if (ctx.ADD() != null){
                return left + right;
            }else {
                return left - right;
            }
        }else {
            return visitMultiplicativeExpression(ctx.multiplicativeExpression());
        }
    }

    @Override
    public Integer visitMultiplicativeExpression(PlayScriptParser.MultiplicativeExpressionContext ctx) {
        if (ctx.MUL() != null || ctx.DIV() != null || ctx.MOD() != null) {
            Integer left = visitMultiplicativeExpression(ctx.multiplicativeExpression());
            Integer right = visitPrimaryExpression(ctx.primaryExpression());
            if (ctx.MUL() != null) {
                return left * right;
            } else if (ctx.DIV() != null) {
                return left / right;
            } else {
                return left % right;
            }
        } else {
            return visitPrimaryExpression(ctx.primaryExpression());
        }
    }

    @Override
    public Integer visitPrimaryExpression(PlayScriptParser.PrimaryExpressionContext ctx) {
        if (ctx.literal() != null) {
            return visitLiteral(ctx.literal());
        }
        return 0;
    }
}
