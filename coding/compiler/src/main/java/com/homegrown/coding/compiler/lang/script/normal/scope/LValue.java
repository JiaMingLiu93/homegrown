package com.homegrown.coding.compiler.lang.script.normal.scope;

/**
 * 对栈中的值的引用，表示这个值是属于栈里的，作用域也是当前栈
 * 这就意味着，当拿到了这么一个类型的变量时，就应该明白它是来自栈的
 * @author youyu
 */
public interface LValue {
    Object getValue();

    void setValue(Object value);

    Variable getVariable();

    JamObject getValueContainer();
}
