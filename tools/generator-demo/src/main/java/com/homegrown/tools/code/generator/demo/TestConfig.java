package com.homegrown.tools.code.generator.demo;

import com.homegrown.tools.code.generator.struct.processor.demo.annotation.GeneratorConfig;
import com.homegrown.tools.code.generator.struct.processor.demo.annotation.RepoTypeConfig;
import com.homegrown.tools.code.generator.struct.processor.demo.annotation.RequestTypeConfig;
import com.homegrown.tools.code.generator.struct.processor.demo.annotation.ServiceTypeConfig;

import java.util.List;

/**
 * @author youyu
 */
@GeneratorConfig(facadePath = "Users/jam/",
        model = "com.homegrown.tools.code.generator.demo.TestModel",
        methodName = "findById")
@RepoTypeConfig(className = "TestModelDao",
        packageName = "com.homegrown.tools.code.generator.demo.repo",
        superClass = TestSuperDao.class)
@ServiceTypeConfig(packageName = "com.homegrown.tools.code.generator.demo.service")
@RequestTypeConfig(packageName = "com.homegrown.tools.code.generator.demo.request",
        className = "TestRequest",
        superClass = TestSuperRequest.class,
        annotations = {TestAnnotation.class},
        imports = {List.class})
public class TestConfig {
}
