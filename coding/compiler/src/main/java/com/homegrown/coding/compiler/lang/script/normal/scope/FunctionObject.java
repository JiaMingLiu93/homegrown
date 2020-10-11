package com.homegrown.coding.compiler.lang.script.normal.scope;

/**
 * 存放一个函数运行时的本地变量的值，包括参数的值
 * @author youyu
 */
public class FunctionObject extends JamObject{
    /**
     * 接收者所在的scope。缺省是function的enclosingScope，也就是词法的Scope。
     * 当赋值给一个函数型变量的时候，要修改receiverEnclosingScope等于这个变量的enclosingScope。
     */
    private Variable receiver;
    //类型
    private Function function;

    public Variable getReceiver() {
        return receiver;
    }

    public void setReceiver(Variable receiver) {
        this.receiver = receiver;
    }

    public FunctionObject (Function function){
        this.function = function;
    }

    protected void setFunction(Function function){
        this.function = function;
    }

    public Function getFunction() {
        return function;
    }
}
