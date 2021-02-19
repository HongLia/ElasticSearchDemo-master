package com.es.EsApi.impl;

import com.es.EsApi.SearchApi;
import com.es.enums.WhetherEnum;
import com.es.util.EsUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SearchApiImpl implements SearchApi {

    private static final Logger LOG = LoggerFactory.getLogger(SearchApiImpl.class);

    @Override
    public void multiQuery(String keyword) {

        Response response;

        try {

            MultiMatchQueryBuilder queryBuilder = new MultiMatchQueryBuilder(keyword).field("title").field("summary");


//            Settings settings = Settings.builder().put("cluster.name", "my-application").build();
//            //获取es主机中节点的ip地址及端口号(以下是单个节点案例)
//            TransportClient client = new PreBuiltTransportClient(settings).addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.1.94"), 9300));
//
//            SearchRequest searchRequest = new SearchRequest("xc_course");
//            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//            //搜索方式
//            //MultiMatchQuery
//            searchSourceBuilder.query(QueryBuilders.multiMatchQuery("spring css","name","description")
//                    .minimumShouldMatch("50%")
//                    .field("name",10));
//            //设置源字段过虑,第一个参数结果集包括哪些字段，第二个参数表示结果集不包括哪些字段
//            searchSourceBuilder.fetchSource(new String[]{"name","studymodel","price","timestamp"},new String[]{});
//            //向搜索请求对象中设置搜索源
//            searchRequest.source(searchSourceBuilder);
//            //执行搜索,向ES发起Http请求,获得结果对象
//            SearchResponse searchResponse = client.search(searchRequest);
//            //搜索结果
//            SearchHits hits = searchResponse.getHits();
//            //获得匹配总记录
//            long totalHits = hits.getTotalHits();


//            Gson gson = new Gson();
//            Map<String, Object> esMap = new HashMap<>();
//            esMap.put("from", "0");
//            esMap.put("size", "20");
//            esMap.put("query", "Beijing,China");
//            esMap.put("pubtime", "2020-01-01");
//            esMap.put("description", "Elasticsearch is a highly scalable open-source full-text search and analytics engine.");
//            String jsonString = gson.toJson(esMap);

//            String jsonString =
//                    "{" +
//                        "\"query\":{" + "\"number_of_shards\":\"" + shardNum + "\"," +
//                        "\"highlight\":\"" + replicaNum + "\"" + "}" +
//                        "\"from\":\"" + 0 + "\"" + "}" +
//                        "\"size\":\"" + 20 + "\"" + "}" +
//                    "}";
//
//            HttpEntity entity = new NStringEntity(jsonString, ContentType.APPLICATION_JSON);
//            Request request = new Request("GET", "/" + "big" + "/" + "goods");
//            request.addParameter("pretty", "true");
//            request.setEntity(entity);
//            RestClient restClient = this.getRestClient();
//            response = restClient.performRequest(request);
//            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
//                LOG.info("QueryData successful.");
//            } else {
//                LOG.error("QueryData failed.");
//            }
//            LOG.info("QueryData response entity is : " + EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            LOG.error("QueryData failed, exception occurred.", e);
        }

    }


    /**
     * Check the existence of the index or not.
     */
    @Override
    public boolean indexIsExist(RestClient restClient, String indexName) {
        try {
            return EsUtils.exist(restClient, indexName);
        } catch (Exception e) {
            LOG.error("Check index failed, exception occurred.", e);
        }
        return false;
    }

    @Override
    public int createIndex(String entityName, String esFileConfigsJson) {
        int result = 0;
        RestClient restClient = null;
        try {
            //查询实体信息
//            ZsEntity entityIById = entityMapper.getEntityIById(entity_id);
//
//            //查询所有字段信息
//            List<Map> entityFields = entityFieldMapper.getEntityFields(entity_id, EnableEnum.EFFECTIVE.getCode());
//            String esFileConfigs = "";
//            if (CollectionUtils.isNotEmpty(entityFields)) {
//                for (Map entityField : entityFields) {
//                    if (esFileConfigs.equals("")) {
//                        esFileConfigs += this.getEsFile(entityField);
//                    } else {
//                        esFileConfigs += "," + this.getEsFile(entityField);
//                    }
//                }
//            }
//
//
//            //查询所有搜索字段信息
//            List<Map> searchFields = entityFieldMapper.getSearchFieldsByEntityId(entity_id);
//            if (CollectionUtils.isNotEmpty(searchFields)) {
//                for (Map entitySearchField : searchFields) {
//                    esFileConfigs += this.getEsSearchFile(entitySearchField);
//                }
//            }

            //获取es客户端
            restClient = EsUtils.getRestClient();

            String mappingJsonString =
                    "    \"mappings\": {\n" +
                            "    \"properties\": {\n" +
                            esFileConfigsJson +
                            "       }\n" +
                            "    }\n";

            result = EsUtils.createIndex(restClient, entityName, mappingJsonString);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            EsUtils.CloseRestClient(restClient);
        }
        return result;
    }


    @Override
    public String getEsFileJson(String fieldType, String propName, String searchField) {
//        //字段类型
//        String fieldType = entityField.get("field_type") + "";
//        //字段名称
//        String propName = entityField.get("prop_name") + "";
//        //是否搜索
//        String searchField = entityField.get("search_field") + "";

        //mapping  类型
        String type = "";
        //mapping  分词器  ik_max_word  pinyin
        String analyzer = "";
        //mapping  是否作为搜索项
        String index = "";
        //mapping  指定格式（日期）
        String format = "";

        //根据字段是否为搜索项进行判断
        if (searchField.equals(WhetherEnum.YES.getCode())) {
            index = "true";


        } else {
            index = "false";
        }

        //根据字段类型进行判断
        if (fieldType.equals("varchar") ||
                fieldType.equals("nvarchar2")) {
            type = "text";
        } else if (fieldType.contains("int") ||
                fieldType.equals("text") ||
                fieldType.equals("numeric")) {
            type = "keyword";
        } else if (fieldType.equals("timestamp")) {
            type = "keyword";
//            format = "yyyy-MM-dd HH:mm:ss";
        }

        //    "name": {
        //        "type": "keyword",
        //        "analyzer": "ik_max_word",
        //        "index": true
        //    }
        String esFileConfig = "\"" + propName + "\": {\n"
                + "        \"type\": \"" + type + "\", \n";
        if (analyzer != "") {
            esFileConfig += "\"analyzer\": \"" + analyzer + "\", \n";
        }
        if (format != "") {
            esFileConfig += "\"format\": \"" + format + "\", \n";
        }
        esFileConfig += "\"index\": " + index + "\n" + "}";
//                +"        \"analyzer\": \""+analyzer+"\", \n"
//        +"        \"format\": \"" + format + "\", \n"
//                + "        \"index\": " + index + "\n"
//                + "    }";
        return esFileConfig;
    }

    @Override
    public String getEsSearchFileJson(String fieldType, String propName) {
//        //字段类型
//        String fieldType = entityField.get("field_type") + "";
//        //字段名称
//        String propName = entityField.get("prop_name") + "";

        //mapping  类型
        String type = "";
        //mapping  分词器  ik_max_word  pinyin
        String analyzer = "";
        //mapping  是否作为搜索项
        String index = "true";
        //mapping  指定格式（日期）
        String format = "";


        //根据字段类型进行判断
        if (fieldType.equals("varchar") ||
                fieldType.equals("nvarchar2")) {
            type = "text";
            analyzer = "ik_max_word";
        } else if (fieldType.contains("int") ||
                fieldType.equals("text") ||
                fieldType.equals("numeric")) {
            type = "keyword";
        } else if (fieldType.equals("timestamp")) {
            type = "keyword";
//            format = "yyyy-MM-dd HH:mm:ss";
        }

        //    "name": {
        //        "type": "keyword",
        //        "analyzer": "ik_max_word",
        //        "index": true
        //    }
        String esSearchConfig = ", \n" + "\"" + "fasik_" + propName + "\": {\n"
                + "        \"type\": \"" + type + "\", \n";
        if (analyzer != "") {
            esSearchConfig += "\"analyzer\": \"" + analyzer + "\", \n"
                    + "        \"search_analyzer\": \"" + analyzer + "\", \n"
                    + "        \"fields\": {\n" +
                    "          \"my_pinyin\":{\n" +
                    "            \"type\":\"text\",\n" +
                    "            \"analyzer\": \"ik_pinyin_analyzer\",\n" +
                    "            \"search_analyzer\": \"ik_pinyin_analyzer\"\n" +
                    "          }\n" +
                    "        },";
        }
        if (format != "") {
            esSearchConfig += "\"format\": \"" + format + "\", \n";
        }
        esSearchConfig += "\"index\": " + index + "\n" + "}";
//                +"        \"analyzer\": \""+analyzer+"\", \n"
//        +"        \"format\": \"" + format + "\", \n"
//                + "        \"index\": " + index + "\n"
//                + "    }";
        return esSearchConfig;
    }


    /**
     * Query the cluster's information
     */
    @Override
    public String queryClusterInfo(RestClient restClient) {
        try {
            return EsUtils.queryClusterInfo(restClient);
        } catch (Exception e) {
            LOG.error("QueryClusterInfo failed", e);
        }
        return "check!";
    }





    /**
     * Write one document into the index
     */
    @Override
    public void putData(RestClient restClient, String indexName, String id, Map<String, Object> esMap) {
        try {
            EsUtils.putData(restClient, indexName, "/_doc", id, esMap);
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
    public Boolean deleteIndex(RestClient restClient, String indexName) {
        try {
            return EsUtils.deleteIndex(restClient, indexName);
        } catch (Exception e) {
            LOG.error("Delete index failed, exception occurred.", e);
        }
        return false;
    }

    /**
     * 清空索引数据
     * Delete all documents by query in one index
     */
    @Override
    public Boolean clearDataByIndexName(RestClient restClient, String indexName) {
        try {
            return EsUtils.clearDataByIndexName(restClient, indexName);
        } catch (Exception e) {
            LOG.error("clearDataByIndexName failed, exception occurred.", e);
        }
        return false;
    }

    /**
     * 根据多个索引名称统计es中数据量
     */
    @Override
    public int getCountByIndexNames(RestClient restClient, String indexNames) {
        int result = 0;
        try {
            result = EsUtils.getCountByIndexName(restClient, indexNames);
            return result;
        } catch (Exception e) {
            LOG.error("getCountByIndexNames failed ", e);
        }
        return result;
    }


    /**
     * Send a bulk request
     */
    @Override
    public void bulk(RestClient restClient, String indexName, List<Map<String, Object>> esMaps, String fieldPkPropName) {
        try {
            EsUtils.bulk(restClient,indexName,"_doc",esMaps,fieldPkPropName);
        } catch (Exception e) {
            LOG.error("Bulk failed, exception occurred.", e);
        }

    }

    @Override
    public RestClient getRestClient() {
        RestClient restClient = null;
        try {
            restClient = EsUtils.getRestClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restClient;
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



}
