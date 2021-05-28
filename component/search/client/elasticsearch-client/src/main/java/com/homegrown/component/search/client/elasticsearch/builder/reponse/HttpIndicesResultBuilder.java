package com.homegrown.component.search.client.elasticsearch.builder.reponse;

import com.homegrown.component.search.client.elasticsearch.cfg.HttpSearchProperties;
import com.homegrown.component.search.client.elasticsearch.utils.JsonUtils;
import com.homegrown.component.search.client.elasticsearch.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author youyu
 */
@Slf4j
@Component
@AllArgsConstructor
public class HttpIndicesResultBuilder {
    private final HttpSearchProperties properties;

    public boolean isSuccessful(Response response) {
        return response.isSuccessful();
    }

    public boolean isAcknowledged(Response response) throws Exception {
        if (response.isSuccessful()) {
            String body = null == response.body() ? null : response.body().string();
            if (StringUtils.isEmpty(body)) {
                return false;
            }
            Map<String, Object> doc = JsonUtils.fromJson(body, JsonUtils.MAP_TYPE_REFERENCE);
            return ObjectUtils.getBoolean(doc, "acknowledged", false);
        }
        return false;
    }
}
