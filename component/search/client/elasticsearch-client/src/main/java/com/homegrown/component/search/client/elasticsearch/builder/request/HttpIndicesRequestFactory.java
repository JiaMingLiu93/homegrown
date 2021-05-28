package com.homegrown.component.search.client.elasticsearch.builder.request;

import com.homegrown.component.search.client.elasticsearch.cfg.HttpSearchProperties;
import lombok.AllArgsConstructor;
import okhttp3.Request;
import org.springframework.stereotype.Component;

/**
 * @author youyu
 */
@Component
@AllArgsConstructor
public class HttpIndicesRequestFactory {
    private final HttpSearchProperties properties;

    public Request buildIndexCreationRequest(String index, String mappingJson) {
        return OkHttpRequestBuilder.put(wrapPathWithEnv(index),null,mappingJson);
    }

    public Request buildIndexExistRequest(String index){
        return OkHttpRequestBuilder.get(wrapPathWithEnv(index),null);
    }

    private String wrapPathWithEnv(String index) {
        return index + properties.getEnvironment();
    }
}
