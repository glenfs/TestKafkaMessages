package com.example.demo.elasticsearch;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
public class ElasticSearchService {

    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.port}")
    private Integer port;

    @Value("${elasticsearch.scheme}")
    private String scheme;

    @Value("${elasticsearch.username}")
    private String username;

    @Value("${elasticsearch.password}")
    private String password;

    @Getter
    private RestHighLevelClient client;

    @PostConstruct
    public void init() {
        if (this.client != null) {
            return;
        }

        this.client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(this.host, this.port, this.scheme)
                )
        );
    }

    public IndexResponse indexRequest(IndexRequest request, RequestOptions options) throws IOException {
        return this.client.index(request, options);
    }

    public IndexResponse send(String indexName, String id, Map<String, Object> source) throws IOException {
        IndexRequest request = new IndexRequest(indexName)
                .id(id)
                .source(source)
                .timeout("10s");

        return this.client.index(request, RequestOptions.DEFAULT);
    }
}
