package com.homegrown.coding.compiler.lang.script.craft.parser;

import com.homegrown.coding.compiler.lang.script.craft.calculator.ASTNode;
import com.homegrown.coding.compiler.lang.script.craft.calculator.ASTNodeType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 一个简单的AST节点的实现
 * 属性包括：类型、文本值、父节点、子节点
 * @author youyu
 */
public class SimpleASTNode implements ASTNode {
    ASTNode parent = null;
    List<ASTNode> children = new ArrayList<ASTNode>();
    List<ASTNode> readonlyChildren = Collections.unmodifiableList(children);
    ASTNodeType nodeType = null;
    String text = null;


    public SimpleASTNode(ASTNodeType nodeType, String text) {
        this.nodeType = nodeType;
        this.text = text;
    }

    @Override
    public ASTNode getParent() {
        return parent;
    }

    @Override
    public List<ASTNode> getChildren() {
        return readonlyChildren;
    }

    @Override
    public ASTNodeType getType() {
        return nodeType;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void addChild(ASTNode child) {
        children.add(child);
        child.setParent(this);
    }

    @Override
    public void setParent(ASTNode parent) {
        this.parent = parent;
    }
}
