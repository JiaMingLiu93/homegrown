package com.homegrown.component.search.client.elasticsearch.builder.request;

import com.homegrown.component.search.client.elasticsearch.cfg.HttpSearchProperties;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Objects;

/**
 * @author youyu
 */
@Component
public class OkHttpRequestBuilder implements SmartInitializingSingleton, ApplicationContextAware {
    private static final String AUTHORIZATION = "Authorization";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    @Autowired
    private HttpSearchProperties properties;

    private static ApplicationContext applicationContext;
    private static OkHttpRequestBuilder okHttpRequestBuilder;

    public static Request get(String path, Map<String, String> parameter) {
        return okHttpRequestBuilder._get(path,parameter);
    }
    private Request _get(String path, Map<String, String> parameter) {
        Request.Builder builder = new Request.Builder();
        builder.url(url(path, parameter));
        if (!StringUtils.isEmpty(properties.getAuthorization())) {
            builder.addHeader(AUTHORIZATION, properties.getAuthorization());
        }
        return builder.get().build();
    }

    public static Request post(String path, Map<String, String> parameter, String json) {
        return okHttpRequestBuilder._post(path,parameter,json);
    }
    private Request _post(String path, Map<String, String> parameter, String json) {
        Request.Builder builder = new Request.Builder();
        builder.url(url(path, parameter));
        if (!StringUtils.isEmpty(properties.getAuthorization())) {
            builder.addHeader(AUTHORIZATION, properties.getAuthorization());
        }
        builder.post(Objects.nonNull(json) ? RequestBody.create(JSON, json) : RequestBody.create(JSON, new byte[]{}));
        return builder.build();
    }

    public static Request put(String path, Map<String, String> parameter, String json) {
        return okHttpRequestBuilder._put(path,parameter,json);
    }
    private Request _put(String path, Map<String, String> parameter, String json) {
        Request.Builder builder = new Request.Builder();
        builder.url(url(path, parameter));
        if (!StringUtils.isEmpty(properties.getAuthorization())) {
            builder.addHeader(AUTHORIZATION, properties.getAuthorization());
        }
        builder.put(Objects.nonNull(json) ? RequestBody.create(JSON, json) : RequestBody.create(JSON, new byte[]{}));
        return builder.build();
    }

    private HttpUrl url(String path, Map<String, String> parameter) {
        HttpSearchProperties.HttpTarget target = properties.getHttpTarget();
        HttpUrl.Builder builder = new HttpUrl.Builder();
        builder.scheme(target.getScheme());
        builder.host(target.getHost());
        builder.port(target.getPort());
        builder.addPathSegments(path);
        if (null != parameter) {
            parameter.forEach(builder::addQueryParameter);
        }
        return builder.build();
    }

    @Override
    public void afterSingletonsInstantiated() {
        OkHttpRequestBuilder.okHttpRequestBuilder = applicationContext.getBean(OkHttpRequestBuilder.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        OkHttpRequestBuilder.applicationContext = applicationContext;
    }
}
