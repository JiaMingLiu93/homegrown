package com.homegrown.basic.design.pattern.create.singleton.usage.example2;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author youyu
 */
public enum IdGeneratorEnum {
    INSTANCE;
    private AtomicLong id = new AtomicLong(0);
    public Long getId(){
        return id.incrementAndGet();
    }
}
