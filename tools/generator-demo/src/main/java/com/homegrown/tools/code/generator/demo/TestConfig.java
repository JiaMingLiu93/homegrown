package com.homegrown.tools.code.generator.demo;

import com.homegrown.tools.code.generator.struct.processor.demo.annotation.GeneratorConfig;
import com.homegrown.tools.code.generator.struct.processor.demo.annotation.RepoTypeConfig;
import com.homegrown.tools.code.generator.struct.processor.demo.annotation.RequestTypeConfig;
import com.homegrown.tools.code.generator.struct.processor.demo.annotation.ServiceTypeConfig;

/**
 * @author youyu
 */
@GeneratorConfig(facadePath = "Users/jam/",
        model = "com.homegrown.tools.code.generator.demo.TestModel",
        methodName = "findById")
@RepoTypeConfig(className = "TestModelDao",
        packageName = "com.homegrown.tools.code.generator.demo.repo",
        superClassName = "com.homegrown.tools.code.generator.demo.TestSuperDao")
@ServiceTypeConfig(packageName = "com.homegrown.tools.code.generator.demo.service")
@RequestTypeConfig(packageName = "com.homegrown.tools.code.generator.demo.request",
        className = "TestRequest",
        superClassName = "com.homegrown.tools.code.generator.demo.TestSuperRequest",
        annotations = {"com.homegrown.tools.code.generator.demo.TestAnnotation"},
        imports = {"java.util.List"})
public class TestConfig {
}
