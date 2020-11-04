package com.homegrown.coding.compiler.lang.script.normal.scope;

/**
 * 栈帧
 * @author youyu
 */
public class StackFrame {
    //该frame所对应的scope
    private Scope scope;

    /**
     * 放parent scope所对应的frame的指针，就叫parentFrame吧，便于提高查找效率。
     * 规则：如果是同一级函数调用，跟上一级的parentFrame相同；
     * 如果是下一级的函数调用或For、If等block，parentFrame是自己；
     * 如果是一个闭包（如何判断？），那么要带一个存放在堆里的环境。
     */
    private StackFrame parentFrame;

    //实际存放变量的地方
    private JamObject object;

    /**
     * 本栈桢里有没有包含某个变量的数据
     * @param variable
     * @return
     */
    public boolean contains(Variable variable) {
        if(object != null && object.getFields() != null){
            return object.getFields().containsKey(variable);
        }
        return false;
    }

    public StackFrame(Scope scope) {
        this.scope = scope;
        this.object = new JamObject();
    }

    public StackFrame(ClassObject object){
        this.scope = object.type;
        this.object = object;
    }

    public Scope getScope() {
        return scope;
    }

    public void setParentFrame(StackFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    public StackFrame getParentFrame() {
        return parentFrame;
    }

    public JamObject getObject() {
        return object;
    }

    @Override
    public String toString(){
        String rtn = ""+scope;
        if (parentFrame != null){
            rtn += " -> " + parentFrame;
        }
        return rtn;
    }

}
