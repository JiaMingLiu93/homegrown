package com.homegrown.component.search.client.elasticsearch.client;

import com.homegrown.component.search.client.elasticsearch.cfg.HttpConnectionPoolProperties;
import com.homegrown.component.search.client.elasticsearch.cfg.HttpSearchProperties;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author youyu
 */
@Component
public class HttpSearchClientAgent {
    @Autowired
    private HttpSearchProperties properties;

    private OkHttpClient client;

    public OkHttpClient getClient() {
        if (null == client) {
            synchronized (this) {
                if (null == client) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    HttpConnectionPoolProperties pool = properties.getHttpConnectionPool();
                    if (Objects.nonNull(pool.getCallTimeout())) {
                        builder.callTimeout(pool.getCallTimeout(), TimeUnit.MILLISECONDS);
                    }
                    if (Objects.nonNull(pool.getReadTimeout())) {
                        builder.readTimeout(pool.getReadTimeout(), TimeUnit.MILLISECONDS);
                    }
                    if (Objects.nonNull(pool.getWriteTimeout())) {
                        builder.writeTimeout(pool.getWriteTimeout(), TimeUnit.MILLISECONDS);
                    }
                    if (Objects.nonNull(pool.getConnectTimeout())) {
                        builder.connectTimeout(pool.getConnectTimeout(), TimeUnit.MILLISECONDS);
                    }
                    builder.connectionPool(new ConnectionPool(pool.getMaxIdle(), pool.getKeepAlive(), TimeUnit.MILLISECONDS));
                    client = builder.build();
                }
            }
        }
        return client;
    }

    public Response createIndex(Request request) throws IOException {
        return getClient().newCall(request).execute();
    }

    public Response exist(Request request) throws IOException{
        return getClient().newCall(request).execute();
    }
}
