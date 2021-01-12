package com.example.demo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.springframework.http.HttpEntity;

public class HttpReq {
	public static void main(String args[]) throws ClientProtocolException, IOException
	{
		/*CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPut httpPut = new HttpPut("http://localhost:9200/company");
		httpPut.setHeader("Accept", "application/json");
		httpPut.setHeader("Content-type", "application/json");
		String json = "{\"settings\": {\"index\": {\"number_of_shards\": 1,\"number_of_replicas\": 1},\"analysis\": {\"analyzer\": {\"analyzer-name\": {\"type\": \"custom\",\"tokenizer\":\"keyword\",\"filter\": \"lowercase\"}}},\"mappings\": {\"properties\": {\"age\": {\"type\": \"long\"},\"experienceInYears\": {\"type\": \"long\"},\"name\": {\"type\": \"string\",\"analyzer\": \"analyzer-name\"}}}}}";
				StringEntity stringEntity = new StringEntity(json);
				System.out.println(stringEntity);
				httpPut.setEntity(stringEntity);
				
				ResponseHandler < String > responseHandler = response -> {
				    int status = response.getStatusLine().getStatusCode();
				    if (status >= 200 && status < 300) {
				        HttpEntity entity = (HttpEntity) response.getEntity();
				        return entity != null ? EntityUtils.toString((org.apache.http.HttpEntity) entity) : null;
				    } else {
				        throw new ClientProtocolException("Unexpected response status: " + status);
				    }
				};
				CloseableHttpResponse responseBody = httpclient.execute(httpPut);
				System.out.println(responseBody);
				httpclient.close();*/
		/*CreateIndexRequest request = new CreateIndexRequest("indexname"); 
		request.settings(Settings.builder() 
			    .put("index.number_of_shards", 1)
			    .put("index.number_of_replicas", 1)
			);
		RestHighLevelClient client = new RestHighLevelClient(
		        RestClient.builder(
		                new HttpHost("localhost", 9200, "http")));
		CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
		System.out.println(createIndexResponse.toString());*/
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("1", "One");
		map.put("2", Integer.valueOf("2"));
		map.put("3", Long.valueOf("1717"));
		
		System.out.println("Map(1)="+map.get("1").getClass());
		System.out.println("Map(2)="+map.get("2").getClass());
		System.out.println("Map(3)="+map.get("3").getClass());
	}

}
