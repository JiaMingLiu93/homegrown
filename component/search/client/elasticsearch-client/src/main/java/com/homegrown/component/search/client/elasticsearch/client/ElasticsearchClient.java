package com.homegrown.component.search.client.elasticsearch.client;

import com.homegrown.component.search.client.SearchClient;
import com.homegrown.component.search.client.elasticsearch.builder.reponse.HttpIndicesResultBuilder;
import com.homegrown.component.search.client.elasticsearch.builder.request.HttpIndicesRequestFactory;
import lombok.AllArgsConstructor;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

/**
 * @author youyu
 */
@Component
@AllArgsConstructor
public class ElasticsearchClient implements SearchClient {

    private final HttpSearchClientAgent agent;
    private final HttpIndicesRequestFactory indicesRequestFactory;
    private final HttpIndicesResultBuilder indicesResultBuilder;

    @Override
    public boolean createWithSchema(String index, String mappings) {
        Request request = indicesRequestFactory.buildIndexCreationRequest(index,mappings);
        try(Response response = agent.createIndex(request)){
            return indicesResultBuilder.isAcknowledged(response);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean createWithSchema(String index, String mappings, String ext1) {
        return false;
    }

    @Override
    public boolean exist(String index) {
        Request request = indicesRequestFactory.buildIndexExistRequest(index);
        try(Response response = agent.exist(request)){
            return indicesResultBuilder.isSuccessful(response);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
