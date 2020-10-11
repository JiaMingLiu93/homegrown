// Generated from com/homegrown/coding/compiler/lang/script/normal/JamScript.g4 by ANTLR 4.7.2

package com.homegrown.coding.compiler.lang.script.normal;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link JamScriptParser}.
 */
public interface JamScriptListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassDeclaration(JamScriptParser.ClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassDeclaration(JamScriptParser.ClassDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#classBody}.
	 * @param ctx the parse tree
	 */
	void enterClassBody(JamScriptParser.ClassBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#classBody}.
	 * @param ctx the parse tree
	 */
	void exitClassBody(JamScriptParser.ClassBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#classBodyDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassBodyDeclaration(JamScriptParser.ClassBodyDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#classBodyDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassBodyDeclaration(JamScriptParser.ClassBodyDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#memberDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterMemberDeclaration(JamScriptParser.MemberDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#memberDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitMemberDeclaration(JamScriptParser.MemberDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDeclaration(JamScriptParser.FunctionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDeclaration(JamScriptParser.FunctionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#functionBody}.
	 * @param ctx the parse tree
	 */
	void enterFunctionBody(JamScriptParser.FunctionBodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#functionBody}.
	 * @param ctx the parse tree
	 */
	void exitFunctionBody(JamScriptParser.FunctionBodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#typeTypeOrVoid}.
	 * @param ctx the parse tree
	 */
	void enterTypeTypeOrVoid(JamScriptParser.TypeTypeOrVoidContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#typeTypeOrVoid}.
	 * @param ctx the parse tree
	 */
	void exitTypeTypeOrVoid(JamScriptParser.TypeTypeOrVoidContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#qualifiedNameList}.
	 * @param ctx the parse tree
	 */
	void enterQualifiedNameList(JamScriptParser.QualifiedNameListContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#qualifiedNameList}.
	 * @param ctx the parse tree
	 */
	void exitQualifiedNameList(JamScriptParser.QualifiedNameListContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#formalParameters}.
	 * @param ctx the parse tree
	 */
	void enterFormalParameters(JamScriptParser.FormalParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#formalParameters}.
	 * @param ctx the parse tree
	 */
	void exitFormalParameters(JamScriptParser.FormalParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#formalParameterList}.
	 * @param ctx the parse tree
	 */
	void enterFormalParameterList(JamScriptParser.FormalParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#formalParameterList}.
	 * @param ctx the parse tree
	 */
	void exitFormalParameterList(JamScriptParser.FormalParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#formalParameter}.
	 * @param ctx the parse tree
	 */
	void enterFormalParameter(JamScriptParser.FormalParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#formalParameter}.
	 * @param ctx the parse tree
	 */
	void exitFormalParameter(JamScriptParser.FormalParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#lastFormalParameter}.
	 * @param ctx the parse tree
	 */
	void enterLastFormalParameter(JamScriptParser.LastFormalParameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#lastFormalParameter}.
	 * @param ctx the parse tree
	 */
	void exitLastFormalParameter(JamScriptParser.LastFormalParameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#variableModifier}.
	 * @param ctx the parse tree
	 */
	void enterVariableModifier(JamScriptParser.VariableModifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#variableModifier}.
	 * @param ctx the parse tree
	 */
	void exitVariableModifier(JamScriptParser.VariableModifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#qualifiedName}.
	 * @param ctx the parse tree
	 */
	void enterQualifiedName(JamScriptParser.QualifiedNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#qualifiedName}.
	 * @param ctx the parse tree
	 */
	void exitQualifiedName(JamScriptParser.QualifiedNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#fieldDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFieldDeclaration(JamScriptParser.FieldDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#fieldDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFieldDeclaration(JamScriptParser.FieldDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#constructorDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterConstructorDeclaration(JamScriptParser.ConstructorDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#constructorDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitConstructorDeclaration(JamScriptParser.ConstructorDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#variableDeclarators}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclarators(JamScriptParser.VariableDeclaratorsContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#variableDeclarators}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclarators(JamScriptParser.VariableDeclaratorsContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#variableDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclarator(JamScriptParser.VariableDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#variableDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclarator(JamScriptParser.VariableDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#variableDeclaratorId}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaratorId(JamScriptParser.VariableDeclaratorIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#variableDeclaratorId}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaratorId(JamScriptParser.VariableDeclaratorIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#variableInitializer}.
	 * @param ctx the parse tree
	 */
	void enterVariableInitializer(JamScriptParser.VariableInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#variableInitializer}.
	 * @param ctx the parse tree
	 */
	void exitVariableInitializer(JamScriptParser.VariableInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#arrayInitializer}.
	 * @param ctx the parse tree
	 */
	void enterArrayInitializer(JamScriptParser.ArrayInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#arrayInitializer}.
	 * @param ctx the parse tree
	 */
	void exitArrayInitializer(JamScriptParser.ArrayInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#classOrInterfaceType}.
	 * @param ctx the parse tree
	 */
	void enterClassOrInterfaceType(JamScriptParser.ClassOrInterfaceTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#classOrInterfaceType}.
	 * @param ctx the parse tree
	 */
	void exitClassOrInterfaceType(JamScriptParser.ClassOrInterfaceTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#typeArgument}.
	 * @param ctx the parse tree
	 */
	void enterTypeArgument(JamScriptParser.TypeArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#typeArgument}.
	 * @param ctx the parse tree
	 */
	void exitTypeArgument(JamScriptParser.TypeArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(JamScriptParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(JamScriptParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#integerLiteral}.
	 * @param ctx the parse tree
	 */
	void enterIntegerLiteral(JamScriptParser.IntegerLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#integerLiteral}.
	 * @param ctx the parse tree
	 */
	void exitIntegerLiteral(JamScriptParser.IntegerLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#floatLiteral}.
	 * @param ctx the parse tree
	 */
	void enterFloatLiteral(JamScriptParser.FloatLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#floatLiteral}.
	 * @param ctx the parse tree
	 */
	void exitFloatLiteral(JamScriptParser.FloatLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(JamScriptParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(JamScriptParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(JamScriptParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(JamScriptParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#blockStatements}.
	 * @param ctx the parse tree
	 */
	void enterBlockStatements(JamScriptParser.BlockStatementsContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#blockStatements}.
	 * @param ctx the parse tree
	 */
	void exitBlockStatements(JamScriptParser.BlockStatementsContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void enterBlockStatement(JamScriptParser.BlockStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void exitBlockStatement(JamScriptParser.BlockStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(JamScriptParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(JamScriptParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#switchBlockStatementGroup}.
	 * @param ctx the parse tree
	 */
	void enterSwitchBlockStatementGroup(JamScriptParser.SwitchBlockStatementGroupContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#switchBlockStatementGroup}.
	 * @param ctx the parse tree
	 */
	void exitSwitchBlockStatementGroup(JamScriptParser.SwitchBlockStatementGroupContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#switchLabel}.
	 * @param ctx the parse tree
	 */
	void enterSwitchLabel(JamScriptParser.SwitchLabelContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#switchLabel}.
	 * @param ctx the parse tree
	 */
	void exitSwitchLabel(JamScriptParser.SwitchLabelContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#forControl}.
	 * @param ctx the parse tree
	 */
	void enterForControl(JamScriptParser.ForControlContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#forControl}.
	 * @param ctx the parse tree
	 */
	void exitForControl(JamScriptParser.ForControlContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#forInit}.
	 * @param ctx the parse tree
	 */
	void enterForInit(JamScriptParser.ForInitContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#forInit}.
	 * @param ctx the parse tree
	 */
	void exitForInit(JamScriptParser.ForInitContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#enhancedForControl}.
	 * @param ctx the parse tree
	 */
	void enterEnhancedForControl(JamScriptParser.EnhancedForControlContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#enhancedForControl}.
	 * @param ctx the parse tree
	 */
	void exitEnhancedForControl(JamScriptParser.EnhancedForControlContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#parExpression}.
	 * @param ctx the parse tree
	 */
	void enterParExpression(JamScriptParser.ParExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#parExpression}.
	 * @param ctx the parse tree
	 */
	void exitParExpression(JamScriptParser.ParExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void enterExpressionList(JamScriptParser.ExpressionListContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#expressionList}.
	 * @param ctx the parse tree
	 */
	void exitExpressionList(JamScriptParser.ExpressionListContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCall(JamScriptParser.FunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCall(JamScriptParser.FunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(JamScriptParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(JamScriptParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(JamScriptParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(JamScriptParser.PrimaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#typeList}.
	 * @param ctx the parse tree
	 */
	void enterTypeList(JamScriptParser.TypeListContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#typeList}.
	 * @param ctx the parse tree
	 */
	void exitTypeList(JamScriptParser.TypeListContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#typeType}.
	 * @param ctx the parse tree
	 */
	void enterTypeType(JamScriptParser.TypeTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#typeType}.
	 * @param ctx the parse tree
	 */
	void exitTypeType(JamScriptParser.TypeTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#functionType}.
	 * @param ctx the parse tree
	 */
	void enterFunctionType(JamScriptParser.FunctionTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#functionType}.
	 * @param ctx the parse tree
	 */
	void exitFunctionType(JamScriptParser.FunctionTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void enterPrimitiveType(JamScriptParser.PrimitiveTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#primitiveType}.
	 * @param ctx the parse tree
	 */
	void exitPrimitiveType(JamScriptParser.PrimitiveTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#creator}.
	 * @param ctx the parse tree
	 */
	void enterCreator(JamScriptParser.CreatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#creator}.
	 * @param ctx the parse tree
	 */
	void exitCreator(JamScriptParser.CreatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#superSuffix}.
	 * @param ctx the parse tree
	 */
	void enterSuperSuffix(JamScriptParser.SuperSuffixContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#superSuffix}.
	 * @param ctx the parse tree
	 */
	void exitSuperSuffix(JamScriptParser.SuperSuffixContext ctx);
	/**
	 * Enter a parse tree produced by {@link JamScriptParser#arguments}.
	 * @param ctx the parse tree
	 */
	void enterArguments(JamScriptParser.ArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link JamScriptParser#arguments}.
	 * @param ctx the parse tree
	 */
	void exitArguments(JamScriptParser.ArgumentsContext ctx);
}