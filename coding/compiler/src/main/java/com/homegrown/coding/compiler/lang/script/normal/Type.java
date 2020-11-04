package com.homegrown.coding.compiler.lang.script.normal;

import com.homegrown.coding.compiler.lang.script.normal.scope.Scope;

/**
 * 类型接口，定义了一个类型的动作，也就是拿到这个类型后可以做什么
 * 换句话说就是类型的功能是什么，就好像拿到了一个药瓶，说明书就是这个药的功能介绍
 *
 * @see com.homegrown.coding.compiler.lang.script.normal.scope.PrimitiveType
 * @see com.homegrown.coding.compiler.lang.script.normal.scope.FunctionType
 * @author youyu
 */
public interface Type {
    String getName();

    Scope getEnclosingScope();

    /**
     * 判断本类型能否用来替换目标类型。
     * 以面向对象为例，子类 is 父类。子类可以出现在任何需要父类的地方。
     * @param type 目标类型
     * @return
     */
    boolean isType(Type type);
}
