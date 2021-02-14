package com.es.EsApi;

import org.elasticsearch.client.RestClient;

public interface SearchApi {


    void createIndexWithShardNum(RestClient restClientTest, String index);

    /**
     * 获取es的地段JsonStr
     *
     * @param fieldType     字段类型
     * @param propName      字段名称
     * @param searchFieldIs 是否搜索
     * @return
     */
    String getEsFile(String fieldType, String propName, String searchFieldIs);

    /**
     * 获取es搜索字段JsonStr
     *
     * @param fieldType
     * @param propName
     * @return
     */
    String getEsSearchFile(String fieldType, String propName);

    /**
     * 创建实体
     *
     * @param entityName    实体名称
     * @param esFileConfigs es的字段JsonStr
     * @return
     */
    int createIndex(String entityName, String esFileConfigs);


    void putData(RestClient restClientTest, String index, String type, String id);

    void queryData(RestClient restClientTest, String index, String type, String id);

    void deleteIndex(RestClient restClientTest, String index);

    void deleteSomeDocumentsInIndex(RestClient restClientTest, String index, String field, String value);

    void flushOneIndex(RestClient restClientTest, String index);

    void bulk(RestClient restClientTest, String index, String type);

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
     * @param restClientTest
     * @param index
     * @return
     */
    boolean indexIsExist(RestClient restClientTest, String index);




    /**
     * Query the cluster's information
     * 查看es健康状态
     *
     * @param restClientTest
     * @return
     * @throws Exception
     */
    void queryClusterInfo(RestClient restClientTest);

    void multiQuery(String keyword);

}
