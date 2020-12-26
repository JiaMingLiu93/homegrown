package com.homegrown.tools.generator.demo3;

import com.homegrown.tools.code.generator.struct.processor.demo.annotation.GenerateTypeEnum;
import com.homegrown.tools.code.generator.struct.processor.demo.annotation.TypeConfig;

import java.util.List;

/**
 * @author youyu
 */
@TypeConfig(type = GenerateTypeEnum.REQUEST,
        packageName = "com.homegrown.tools.code.generator.demo.request",
        className = "TestRequest",
        superClass = "com.homegrown.tools.code.generator.demo.TestSuperRequest",
        annotations = {TestAnnotation.class},
        imports = {List.class})
public class TestModel extends TestRootModel<Long> {

}
