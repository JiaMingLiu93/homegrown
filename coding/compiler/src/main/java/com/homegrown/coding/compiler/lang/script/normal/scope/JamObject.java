package com.homegrown.coding.compiler.lang.script.normal.scope;

import java.util.HashMap;
import java.util.Map;

/**
 * @author youyu
 */
public class JamObject {
    //成员变量
    private Map<Variable, Object> fields = new HashMap<>();

    public Object getValue(Variable variable){
        Object rtn = fields.get(variable);
        //TODO 父类的属性如何返回？还是说都在这里了？

        //替换成自己的NullObject
        if (rtn == null){
            rtn = NullObject.instance();
        }
        return rtn;
    }

    public void setValue(Variable variable, Object value){
        fields.put(variable, value);
    }

    public Map<Variable, Object> getFields() {
        return fields;
    }
}
