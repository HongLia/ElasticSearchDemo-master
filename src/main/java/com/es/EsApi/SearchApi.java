package com.es.EsApi;

import org.elasticsearch.client.RestClient;

import java.util.List;
import java.util.Map;

public interface SearchApi {



    /**
     * 获取客户端
     *
     * @return
     */
    RestClient getRestClient();

    /**
     * Check the existence of the index or not.
     * 判断索引是否存在
     *
     * @param restClient
     * @param indexName
     * @return
     */
    boolean indexIsExist(RestClient restClient, String indexName);

    /**
     * Query the cluster's information
     * 查看es健康状态
     *
     * @param restClient
     * @return
     */
    String queryClusterInfo(RestClient restClient);

    /**
     * 获取es的地段JsonStr
     *
     * @param fieldType     字段类型
     * @param propName      字段名称
     * @param searchFieldIs 是否搜索
     * @return
     */
    String getEsFileJson(String fieldType, String propName, String searchFieldIs);

    /**
     * 获取es搜索字段JsonStr
     *
     * @param fieldType
     * @param propName
     * @return
     */
    String getEsSearchFileJson(String fieldType, String propName);

    /**
     * 创建es索引
     *
     * @param entityName        实体名称
     * @param esFileConfigsJson es的字段JsonStr
     * @return
     */
    int createIndex(String entityName, String esFileConfigsJson);

    /**
     * 删除索引及所有数据
     *
     * @param restClient
     * @param indexName
     * @return
     */
    Boolean deleteIndex(RestClient restClient, String indexName);


    /**
     * 清空索引中的所有数据
     *
     * @param restClient
     * @param indexName
     * @return
     */
    Boolean clearDataByIndexName(RestClient restClient, String indexName);

    /**
     * 根据多个索引名称统计es中数据量
     *
     * @param restClient
     * @param indexNames 多个索引名称逗号分割   index1,index2
     * @return
     */
    int getCountByIndexNames(RestClient restClient, String indexNames);

    /**
     * 插入一条文档
     *
     * @param restClient
     * @param indexName
     * @param id
     * @param esMap
     */
    void putData(RestClient restClient, String indexName, String id, Map<String, Object> esMap);

    /**
     * 批量插入文档
     *
     * @param restClient
     * @param indexName
     * @param esMaps
     * @param fieldPkPropName
     */
    void bulk(RestClient restClient, String indexName, List<Map<String, Object>> esMaps, String fieldPkPropName);




    void queryData(RestClient restClientTest, String index, String type, String id);

    void deleteSomeDocumentsInIndex(RestClient restClientTest, String index, String field, String value);

    void flushOneIndex(RestClient restClientTest, String index);


    void multiQuery(String keyword);

}
