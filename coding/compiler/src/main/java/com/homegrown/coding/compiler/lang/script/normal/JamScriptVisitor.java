// Generated from com/homegrown/coding/compiler/lang/script/normal/JamScript.g4 by ANTLR 4.7.2

package com.homegrown.coding.compiler.lang.script.normal;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link JamScriptParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface JamScriptVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#classDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDeclaration(JamScriptParser.ClassDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#classBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassBody(JamScriptParser.ClassBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#classBodyDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassBodyDeclaration(JamScriptParser.ClassBodyDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#memberDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberDeclaration(JamScriptParser.MemberDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#functionDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDeclaration(JamScriptParser.FunctionDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#functionBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionBody(JamScriptParser.FunctionBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#typeTypeOrVoid}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeTypeOrVoid(JamScriptParser.TypeTypeOrVoidContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#qualifiedNameList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQualifiedNameList(JamScriptParser.QualifiedNameListContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#formalParameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormalParameters(JamScriptParser.FormalParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#formalParameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormalParameterList(JamScriptParser.FormalParameterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#formalParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormalParameter(JamScriptParser.FormalParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#lastFormalParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLastFormalParameter(JamScriptParser.LastFormalParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#variableModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableModifier(JamScriptParser.VariableModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#qualifiedName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQualifiedName(JamScriptParser.QualifiedNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#fieldDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldDeclaration(JamScriptParser.FieldDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#constructorDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructorDeclaration(JamScriptParser.ConstructorDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#variableDeclarators}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclarators(JamScriptParser.VariableDeclaratorsContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#variableDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclarator(JamScriptParser.VariableDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#variableDeclaratorId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclaratorId(JamScriptParser.VariableDeclaratorIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#variableInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableInitializer(JamScriptParser.VariableInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#arrayInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayInitializer(JamScriptParser.ArrayInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#classOrInterfaceType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassOrInterfaceType(JamScriptParser.ClassOrInterfaceTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#typeArgument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeArgument(JamScriptParser.TypeArgumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(JamScriptParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#integerLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntegerLiteral(JamScriptParser.IntegerLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#floatLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloatLiteral(JamScriptParser.FloatLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(JamScriptParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(JamScriptParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#blockStatements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStatements(JamScriptParser.BlockStatementsContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#blockStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStatement(JamScriptParser.BlockStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(JamScriptParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#switchBlockStatementGroup}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchBlockStatementGroup(JamScriptParser.SwitchBlockStatementGroupContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#switchLabel}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchLabel(JamScriptParser.SwitchLabelContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#forControl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForControl(JamScriptParser.ForControlContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#forInit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForInit(JamScriptParser.ForInitContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#enhancedForControl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnhancedForControl(JamScriptParser.EnhancedForControlContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#parExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParExpression(JamScriptParser.ParExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#expressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionList(JamScriptParser.ExpressionListContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#functionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCall(JamScriptParser.FunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(JamScriptParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(JamScriptParser.PrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#typeList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeList(JamScriptParser.TypeListContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#typeType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeType(JamScriptParser.TypeTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#functionType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionType(JamScriptParser.FunctionTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#primitiveType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitiveType(JamScriptParser.PrimitiveTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#creator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreator(JamScriptParser.CreatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#superSuffix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuperSuffix(JamScriptParser.SuperSuffixContext ctx);
	/**
	 * Visit a parse tree produced by {@link JamScriptParser#arguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArguments(JamScriptParser.ArgumentsContext ctx);
}