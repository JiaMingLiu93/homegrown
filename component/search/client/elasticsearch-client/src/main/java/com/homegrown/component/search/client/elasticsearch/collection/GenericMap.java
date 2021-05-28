package com.homegrown.component.search.client.elasticsearch.collection;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author youyu
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GenericMap extends HashMap<String, Object> {
    public GenericMap(int initialCapacity) {
        super(initialCapacity);
    }
    public GenericMap(Map<? extends String, ?> m) {
        super(m);
    }

}
