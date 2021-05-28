package com.homegrown.component.search.client.elasticsearch.index;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.google.common.io.Resources;
import com.homegrown.component.search.client.SearchClient;
import com.homegrown.component.search.client.elasticsearch.cfg.HttpSearchProperties;
import com.homegrown.component.search.client.elasticsearch.collection.GenericMap;
import com.homegrown.component.search.client.elasticsearch.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.homegrown.component.search.client.elasticsearch.constant.Constants.INDEX_PREFIX;

/**
 * to initialize index if enable
 * @author youyu
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ElasticsearchIndexInitializer implements SmartInitializingSingleton {
    private static final Map<String, String> ALIAS_JSON = new HashMap<>();
    private static final Map<String, String> ALIAS_INDEX = new HashMap<>();

    private final HttpSearchProperties properties;
    private final SearchClient searchClient;

    private String wrapIndexName(Resource resource) {
        return INDEX_PREFIX + resource.getFilename().substring(0, resource.getFilename().lastIndexOf(".json"));
    }

    @Override
    public void afterSingletonsInstantiated() {
        if (properties.isMappingDisable()){
            log.warn("Elasticsearch index mapping auto creation is disabled");
            return;
        }
        Resource[] resources = properties.resolveMappingLocations();
        if (resources.length <= 0) {
            log.info("Elasticsearch index mapping json files are not found");
            return;
        }

        Set<String> indexNameSet = Sets.newHashSet();
        for (Resource resource : resources) {
            if (resource.getFilename() == null || !resource.getFilename().endsWith(".json")) {
                continue;
            }
            String indexWrapped = wrapIndexName(resource);
            if (indexNameSet.contains(indexWrapped)) {
//                throw new IOException("Elasticsearch index mapping json file is not available due to conflict name, filename = " + resource.getFilename());
            }
            indexNameSet.add(indexWrapped);
            String json = null;
            try {
                json = Resources.toString(resource.getURL(), Charsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
            GenericMap map = JsonUtils.fromJson(json, GenericMap.class);
            if (null == map) {
//                throw new IOException("Elasticsearch index mapping json file is not available due to miss content , filename = " + resource.getFilename());
            }
            Map aliases = (Map) map.get("aliases");
            if (CollectionUtils.isEmpty(aliases)) {
//                throw new IOException("Elasticsearch index mapping json file is not available due to miss aliases, filename = " + resource.getFilename());
            }
            String alias = Iterables.getFirst(aliases.keySet(), (String) null);
            if (StringUtils.isEmpty(alias)) {
//                throw new IOException("Elasticsearch index mapping json file is not available due to miss alias, filename = " + resource.getFilename());
            }

            map.remove("aliases");
            // ALIAS_INDEX_JSON 中放一份，创建新索引时会再次使用
            ALIAS_JSON.put(alias, JsonUtils.toJson(map));
            ALIAS_INDEX.put(alias, indexWrapped);
            // 根据环境切换别名
            map.put("aliases", ImmutableMap.of(alias + properties.getEnvironment(), new HashMap<>()));
            json = JsonUtils.toJson(map);

            // 判断索引初始化
            if (searchClient.exist(alias)) {
                // 索引已经存在则不继续创建
                log.info("Elasticsearch index already exists, filename = {}", resource.getFilename());
                continue;
            }
            if (searchClient.createWithSchema(indexWrapped, json)) {
                log.info("Elasticsearch index is not exists, auto create successful, filename = {}", resource.getFilename());
            } else {
                log.error("Elasticsearch index is not exists, auto create failed, filename = {}", resource.getFilename());
            }
        }
    }
}
