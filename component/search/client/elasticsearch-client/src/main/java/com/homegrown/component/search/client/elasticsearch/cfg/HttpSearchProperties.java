package com.homegrown.component.search.client.elasticsearch.cfg;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author youyu
 */
@Data
@ConfigurationProperties("search.datasource")
public class HttpSearchProperties {
    /**
     * Http address
     */
    private String address = "http://127.0.0.1:9200";
    /**
     * Xpack username
     */
    private String username;
    /**
     * Xpack password
     */
    private String password;

    /**
     * Http connection pool
     */
    private HttpConnectionPoolProperties pool;

    /**
     * Index mapping file
     */
    private String[] mappingLocations;

    /**
     * Index mapping disable, default false
     */
    private Boolean mappingDisable;

    public HttpConnectionPoolProperties getHttpConnectionPool() {
        return Objects.isNull(pool)?new HttpConnectionPoolProperties():pool;
    }

    public String getAuthorization() {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return null;
        }
        return "Basic " + Base64Utils.encodeToString((username + ":" + password).getBytes());
    }

    public HttpTarget getHttpTarget() {
        HttpTarget target = new HttpTarget();
        int i = address.indexOf("://");
        if (i == -1) {
            target.scheme = "http";
            i = 0;
        } else {
            target.scheme = address.substring(0, i);
            i = i + 3;
        }
        int j = address.lastIndexOf(":");
        target.host = address.substring(i, j);
        target.port = Integer.parseInt(address.substring(j + 1));
        return target;
    }

    public Resource[] resolveMappingLocations() {
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        List<Resource> resources = new ArrayList<>();
        if (this.mappingLocations != null) {
            for (String mapperLocation : this.mappingLocations) {
                try {
                    Resource[] mappers = resourceResolver.getResources(mapperLocation);
                    resources.addAll(Arrays.asList(mappers));
                } catch (IOException e) {
                    // ignore
                }
            }
        }
        return resources.toArray(new Resource[resources.size()]);
    }

    public boolean isMappingDisable() {
        if (null == mappingDisable) {
            return false;
        }
        return mappingDisable;
    }

    @Getter
    @ToString
    public static class HttpTarget {
        private String scheme;

        private String host;

        private int port;
    }

    /**
     * Index environment
     */
    private String environment;

    public String getEnvironment() {
        if (StringUtils.isEmpty(environment)) {
            return "";
        }
        return "_" + environment;
    }
}
