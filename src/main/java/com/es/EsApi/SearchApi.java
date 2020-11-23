package com.es.EsApi;

import org.elasticsearch.client.RestClient;

public interface SearchApi {


    void createIndexWithShardNum(RestClient restClientTest, String index);

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
