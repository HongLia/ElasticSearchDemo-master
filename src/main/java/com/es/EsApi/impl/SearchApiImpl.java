package com.es.EsApi.impl;

import com.es.EsApi.SearchApi;
import com.google.gson.Gson;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class SearchApiImpl implements SearchApi {

    private static final Logger LOG = LoggerFactory.getLogger(SearchApiImpl.class);
    private static HttpHost[] hostArray;
    private String esServerHost = "127.0.0.1:9200";
    private String isSecureMode = "false";


    /**
     * Check the existence of the index or not.
     */
    @Override
    public boolean indexIsExist(RestClient restClientTest, String index) {
        Response response;
        try {
            Request request = new Request("HEAD", "/" + index);
            request.addParameter("pretty", "true");
            response = restClientTest.performRequest(request);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                LOG.info("Check index successful,index is exist : " + index);
                return true;
            }
            if (HttpStatus.SC_NOT_FOUND == response.getStatusLine().getStatusCode()) {
                LOG.info("Index is not exist : " + index);
                return false;
            }

        } catch (Exception e) {
            LOG.error("Check index failed, exception occurred.", e);
        }
        return false;
    }


    /**
     * Query the cluster's information
     */
    @Override
    public void queryClusterInfo(RestClient restClientTest) {
        Response response;
        try {
            Request request = new Request("GET", "/_cluster/health");
            request.addParameter("pretty", "true");
            response = restClientTest.performRequest(request);

            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                LOG.info("QueryClusterInfo successful.");
            } else {
                LOG.error("QueryClusterInfo failed.");
            }
            LOG.info("QueryClusterInfo response entity is : " + EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            LOG.error("QueryClusterInfo failed, exception occurred.", e);
        }
    }

    @Override
    public void multiQuery(String keyword) {

        Response response;
        try {
            Request request = new Request("GET", "/" + "big" + "/" + "goods");
            request.addParameter("pretty", "true");

            RestClient restClient = this.getRestClient();
            response = restClient.performRequest(request);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                LOG.info("QueryData successful.");
            } else {
                LOG.error("QueryData failed.");
            }
            LOG.info("QueryData response entity is : " + EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            LOG.error("QueryData failed, exception occurred.", e);
        }

    }

    /**
     * Create one index with customized shard number and replica number.
     */
    @Override
    public void createIndexWithShardNum(RestClient restClientTest, String index) {
        Response response;
        int shardNum = 3;
        int replicaNum = 1;
        String jsonString =
                "{" + "\"settings\":{" + "\"number_of_shards\":\"" + shardNum + "\"," + "\"number_of_replicas\":\"" + replicaNum + "\"" + "}}";

        HttpEntity entity = new NStringEntity(jsonString, ContentType.APPLICATION_JSON);
        try {
            Request request = new Request("PUT", "/" + index);
            request.addParameter("pretty", "true");
            request.setEntity(entity);
            response = restClientTest.performRequest(request);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                LOG.info("CreateIndexWithShardNum successful.");
            } else {
                LOG.error("CreateIndexWithShardNum failed.");
            }
            LOG.info("CreateIndexWithShardNum response entity is : " + EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            LOG.error("CreateIndexWithShardNum failed, exception occurred.", e);
        }

    }

    /**
     * Write one document into the index
     */
    @Override
    public void putData(RestClient restClientTest, String index, String type, String id) {

        Gson gson = new Gson();
        Map<String, Object> esMap = new HashMap<>();
        esMap.put("name", "Happy");
        esMap.put("author", "Alex Yang");
        esMap.put("pubinfo", "Beijing,China");
        esMap.put("pubtime", "2020-01-01");
        esMap.put("description", "Elasticsearch is a highly scalable open-source full-text search and analytics engine.");
        String jsonString = gson.toJson(esMap);
        Map<String, String> params = Collections.singletonMap("pretty", "true");
        HttpEntity entity = new NStringEntity(jsonString, ContentType.APPLICATION_JSON);
        Response response;

        try {
            Request request = new Request("POST", "/" + index + "/" + type + "/" + id);
            request.addParameter("pretty", "true");
            request.setEntity(entity);
            response = restClientTest.performRequest(request);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode() ||
                    HttpStatus.SC_CREATED == response.getStatusLine().getStatusCode()) {
                LOG.info("PutData successful.");
            } else {
                LOG.error("PutData failed.");
            }
            LOG.info("PutData response entity is : " + EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            LOG.error("PutData failed, exception occurred.", e);
        }

    }

    /**
     * Query all data of one index.
     */
    @Override
    public void queryData(RestClient restClientTest, String index, String type, String id) {
        Response response;
        try {
            Request request = new Request("GET", "/" + index + "/" + type + "/" + id);
            request.addParameter("pretty", "true");
            response = restClientTest.performRequest(request);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                LOG.info("QueryData successful.");
            } else {
                LOG.error("QueryData failed.");
            }
            LOG.info("QueryData response entity is : " + EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            LOG.error("QueryData failed, exception occurred.", e);
        }
    }

    /**
     * Delete one index
     */
    @Override
    public void deleteIndex(RestClient restClientTest, String index) {
        Response response;
        try {
            Request request = new Request("DELETE", "/" + index + "?&pretty=true");
            response = restClientTest.performRequest(request);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                LOG.info("Delete index successful.");
            } else {
                LOG.error("Delete index failed.");
            }
            LOG.info("Delete index response entity is : " + EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            LOG.error("Delete index failed, exception occurred.", e);
        }
    }

    /**
     * Delete some documents by query in one index
     */
    @Override
    public void deleteSomeDocumentsInIndex(RestClient restClientTest, String index, String field, String value) {
        String jsonString = "{\n" + "  \"query\": {\n" + "    \"match\": { \"" + field + "\":\"" + value + "\"}\n" + "  }\n" + "}";
        HttpEntity entity = new NStringEntity(jsonString, ContentType.APPLICATION_JSON);
        Response response;
        try {
            Request request = new Request("POST", "/" + index + "/_delete_by_query");
            request.addParameter("pretty", "true");
            request.setEntity(entity);
            response = restClientTest.performRequest(request);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                LOG.info("DeleteSomeDocumentsInIndex successful.");
            } else {
                LOG.error("DeleteSomeDocumentsInIndex failed.");
            }
            LOG.info("DeleteSomeDocumentsInIndex response entity is : " + EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            LOG.error("DeleteSomeDocumentsInIndex failed, exception occurred.", e);
        }
    }

    /**
     * Flush one index data to storage and clearing the internal transaction log
     */
    @Override
    public void flushOneIndex(RestClient restClientTest, String index) {
        Response flushresponse;
        try {
            Request request = new Request("POST", "/" + index + "/_flush");
            request.addParameter("pretty", "true");
            flushresponse = restClientTest.performRequest(request);
            LOG.info(EntityUtils.toString(flushresponse.getEntity()));

            if (HttpStatus.SC_OK == flushresponse.getStatusLine().getStatusCode()) {
                LOG.info("Flush successful.");
            } else {
                LOG.error("Flush failed.");
            }
            LOG.info("Flush response entity is : " + EntityUtils.toString(flushresponse.getEntity()));
        } catch (Exception e) {
            LOG.error("Flush failed, exception occurred.", e);
        }
    }

    /**
     * Send a bulk request
     */
    @Override
    public void bulk(RestClient restClientTest, String index, String type) {

        //需要写入的总文档数
        long totalRecordNum = 10;
        //一次bulk写入的文档数
        long oneCommit = 5;
        long circleNumber = totalRecordNum / oneCommit;
        StringEntity entity;
        Gson gson = new Gson();
        Map<String, Object> esMap = new HashMap<>();
        String str = "{ \"index\" : { \"_index\" : \"" + index + "\", \"_type\" :  \"" + type + "\"} }";

        for (int i = 1; i <= circleNumber; i++) {
            StringBuffer buffer = new StringBuffer();
            for (int j = 1; j <= oneCommit; j++) {
                esMap.clear();
                esMap.put("name", "Linda");
                esMap.put("age", ThreadLocalRandom.current().nextInt(18, 100));
                esMap.put("height", (float)ThreadLocalRandom.current().nextInt(140, 220));
                esMap.put("weight", (float)ThreadLocalRandom.current().nextInt(70, 200));
                esMap.put("cur_time", System.currentTimeMillis());

                String strJson = gson.toJson(esMap);
                buffer.append(str).append("\n");
                buffer.append(strJson).append("\n");
            }
            entity = new StringEntity(buffer.toString(), ContentType.APPLICATION_JSON);
            entity.setContentEncoding("UTF-8");
            Response response;
            try {
                Request request = new Request("PUT", "/_bulk");
                request.setEntity(entity);
                request.addParameter("pretty", "true");
                response = restClientTest.performRequest(request);
                if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                    LOG.info("Already input documents : " + oneCommit * i);
                } else {
                    LOG.error("Bulk failed.");
                }
                LOG.info("Bulk response entity is : " + EntityUtils.toString(response.getEntity()));
            } catch (Exception e) {
                LOG.error("Bulk failed, exception occurred.", e);
            }
        }
    }


    private HttpHost[] getHostArray() {
        String schema;
        if ("false".equals(isSecureMode)) {
            schema = "http";
        } else {
            schema = "https";
        }

        List<HttpHost> hosts = new ArrayList();
        String[] hostArray1 = esServerHost.split(",");
        String[] var4 = hostArray1;
        int var5 = hostArray1.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            String host = var4[var6];
            String[] ipPort = host.split(":");
            HttpHost hostNew = new HttpHost(ipPort[0], Integer.parseInt(ipPort[1]), schema);
            hosts.add(hostNew);
        }

        return (HttpHost[])hosts.toArray(new HttpHost[0]);
    }

    @Override
    public RestClient getRestClient() {
        hostArray = this.getHostArray();
        RestClientBuilder builder = RestClient.builder(hostArray);
        Header[] defaultHeaders = new Header[]{new BasicHeader("Accept", "application/json"), new BasicHeader("Content-type", "application/json")};
        builder.setDefaultHeaders(defaultHeaders);
        RestClient restClient = builder.build();
        return restClient;
    }

}
