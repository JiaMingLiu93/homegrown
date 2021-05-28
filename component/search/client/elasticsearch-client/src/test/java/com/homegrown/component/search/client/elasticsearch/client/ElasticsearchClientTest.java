package com.homegrown.component.search.client.elasticsearch.client;

import com.homegrown.component.search.client.SearchClient;
import com.homegrown.component.search.client.elasticsearch.cfg.ElasticsearchClientAutoConfiguration;
import com.homegrown.component.search.client.elasticsearch.index.ElasticsearchIndexInitializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

/**
 * @author youyu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ElasticsearchClientAutoConfiguration.class)
@EnableAutoConfiguration
@ActiveProfiles("search")
public class ElasticsearchClientTest {
    @Autowired
    private SearchClient searchClient;

    /**
     * @see ElasticsearchIndexInitializer
     * elasticsearch的版本是：7.0.0
     */
    @Test
    public void testInit(){

    }

    @Test
    public void testCreateWithSchema(){
        boolean b = searchClient.createWithSchema("index_test", "");
        System.out.println("test result="+b);
        Assert.isTrue(searchClient.exist("index_test"));
    }
}
