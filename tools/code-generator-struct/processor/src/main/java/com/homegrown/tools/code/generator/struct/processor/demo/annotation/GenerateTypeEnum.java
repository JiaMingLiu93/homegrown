package com.homegrown.tools.code.generator.struct.processor.demo.annotation;

/**
 * todo manage priority
 * @author youyu
 */
public enum GenerateTypeEnum {
    /**
     * general type is not used to generate class,but just for general config
     */
    GENERAL,
    /**
     * request of facade api
     */
    REQUEST,
    /**
     * response of facade api
     */
    INFO,
    /**
     * repo class,such as dao
     */
    REPO,
    /**
     * service interface
     */
    SERVICE,
    /**
     * service implement
     */
    SERVICEIMPL,
    /**
     * manager class
     */
    MANAGER,
    /**
     * facade interface
     */
    FACADE,
    /**
     * facade implement
     */
    FACADEIMPL;
}
