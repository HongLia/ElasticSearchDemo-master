package com.es.service.EsApi;

import java.util.List;
import java.util.Map;

/**
 * 搜索引擎，搜索数据接口，用于搜索搜索引擎中的数据
 */
public interface SearchEngineService {


    /**
     * 创建索引  1成功   0失败
     *
     * @param indexName        //索引名称
     * @param esFileConfigJson //mapping内部字段Json
     */
    int createIndex(String indexName, String esFileConfigJson);

    /**
     * 组装mapping的所有字段Json
     *
     * @param fieldType   //字段类型
     * @param propName    //字段名称
     * @param searchField //是否搜索
     * @return
     */
    String getEsFileJson(String fieldType, String propName, String searchField);

    /**
     * 判断索引是否存在  1是   0否
     *
     * @param indexName
     */
    Boolean exist(String indexName);


    /**
     * 删除索引  1成功   0失败
     *
     * @param indexName
     */
    Boolean deleteIndex(String indexName);

    /**
     * 清空索引数据
     *
     * @param indexName
     */
    Boolean clearDataByIndexName(String indexName);


    /**
     * 插入文档
     *
     * @param indexName
     */
    void putDataToIndex(String indexName, List<Map<String, Object>> esMaps, String fieldPkPropName);

    /**
     * 根据多个索引名称统计es中数据量  逗号分隔
     *
     * @param indexNames
     * @return
     */
    int getCountByIndexes(String indexNames);

    /**
     * 健康检查
     *
     * @return
     */
    String queryHealth();

}
