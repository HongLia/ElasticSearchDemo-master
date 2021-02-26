package com.es.service.EsApi.impl;

import com.es.enums.WhetherEnum;
import com.es.service.EsApi.SearchEngineService;
import com.es.util.EsUtils;
import com.es.util.PropertiesUtil;
import org.apache.commons.collections.CollectionUtils;
import org.elasticsearch.client.RestClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @auther zhl
 * @description 搜索引擎查询接口，elasticsearch实现类
 * @date 2020/9/11
 */
@Service
public class SearchEngineElasticsearchServiceImpl implements SearchEngineService {


    String pluginsIK = PropertiesUtil.getpropetyByfile("pluginsIK", "es-example.properties");
    String pluginsPinyin = PropertiesUtil.getpropetyByfile("pluginsPinyin", "es-example.properties");

    private static RestClient restClient = null;


    /**
     * 创建索引  1成功   0失败
     *
     * @param indexName
     * @param esFileConfigJson
     */
    @Override
    public int createIndex(String indexName, String esFileConfigJson) {
        int result = 0;
        try {
            String mappingJsonString =
                    "    \"mappings\": {\n" +
                            "            \"properties\": {\n" +
                            esFileConfigJson +
                            "            }\n" +
                            "    }\n";

            restClient = EsUtils.getRestClient();
            result = EsUtils.createIndex(restClient, indexName, mappingJsonString);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            EsUtils.CloseRestClient(restClient);
        }
        return result;
    }


    /**
     * 组装mapping的所有字段Json
     *
     * @param fieldType   //字段类型
     * @param propName    //字段名称
     * @param searchField //是否搜索
     * @return
     */
    @Override
    public String getEsFileJson(String fieldType, String propName, String searchField) {

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
            analyzer = "ik_max_word";
        } else if (fieldType.contains("int") ||
                fieldType.equals("text") ||
                fieldType.equals("numeric")) {
            type = "keyword";
        } else if (fieldType.equals("timestamp")) {
            // TODO: 2021/2/23 处理日期格式的数据
            type = "keyword";
//            format = "yyyy-MM-dd HH:mm:ss";
        }

        //    "name": {
        //        "type": "keyword",
        //        "analyzer": "ik_max_word",
        //        "index": true
        //    }
        return this.handleMappingAnalyzer(propName, type, analyzer, index, format);
    }

    /**
     * 根据分词器安装情况设置mapping  properties  analyzer
     *
     * @param propName
     * @param type
     * @param analyzer
     * @param index
     * @param format
     * @return
     */
    private String handleMappingAnalyzer(String propName, String type, String analyzer, String index, String format) {
        String esFileConfig = "\"" + propName + "\": {\n" +
                "        \"type\": \"" + type + "\", \n";

        if (analyzer != "") {
            if (pluginsIK.equals("true") && pluginsPinyin.equals("true")) {
                //IK与pingyin分词器都已安装
                esFileConfig += "\"analyzer\": \"" + analyzer + "\", \n" +
                        "        \"search_analyzer\": \"" + analyzer + "\", \n" +
                        "        \"fields\": {\n" +
                        "          \"my_pinyin\":{\n" +
                        "            \"type\":\"text\",\n" +
                        "            \"analyzer\": \"ik_pinyin_analyzer\",\n" +
                        "            \"search_analyzer\": \"ik_pinyin_analyzer\"\n" +
                        "          }\n" +
                        "        },";
            } else if (pluginsIK.equals("true") && pluginsPinyin.equals("false")) {
                //只有IK
                esFileConfig += "\"analyzer\": \"ik_max_word\", \n";

            } else if (pluginsIK.equals("false") && pluginsPinyin.equals("true")) {
                //只有pinyin
                esFileConfig += "\"analyzer\": \"pinyin\", \n";

            } else {
                //未知
            }
        }

        if (format != "") {
            esFileConfig += "\"format\": \"" + format + "\", \n";
        }
        esFileConfig += "\"index\": " + index + "\n" + "}";

        return esFileConfig;
    }

    /**
     * 判断索引是否存在  1是   0否
     *
     * @param indexName
     */
    @Override
    public Boolean exist(String indexName) {
        Boolean exist = false;
        try {
            restClient = EsUtils.getRestClient();
            exist = EsUtils.exist(restClient, indexName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            EsUtils.CloseRestClient(restClient);
        }
        return exist;
    }

    /**
     * 删除索引  1成功   0失败
     *
     * @param indexName
     */
    @Override
    public Boolean deleteIndex(String indexName) {
        Boolean result = false;
        try {
            restClient = EsUtils.getRestClient();
            Boolean exist = EsUtils.exist(restClient, indexName);
            if (exist) {
                result = EsUtils.deleteIndex(restClient, indexName);
            } else {
                System.out.println("索引: " + indexName + " 不存在！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            EsUtils.CloseRestClient(restClient);
        }
        return result;
    }

    /**
     * 清空索引数据
     *
     * @param indexName
     */
    @Override
    public Boolean clearDataByIndexName(String indexName) {
        Boolean result = false;
        try {
            restClient = EsUtils.getRestClient();
            Boolean success = EsUtils.clearDataByIndexName(restClient, indexName);
            System.out.println("清空索引: " + indexName + ":" + success);
            if (success) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            EsUtils.CloseRestClient(restClient);
        }
        return result;
    }

    /**
     * 批量插入文档
     *
     * @param indexName
     */
    @Override
    public void putDataToIndex(String indexName, List<Map<String, Object>> esMaps, String fieldPkPropName) {
        if (CollectionUtils.isNotEmpty(esMaps)) {
            try {
                restClient = EsUtils.getRestClient();
                //es批量新增文档
                EsUtils.bulk(restClient, indexName, "_doc", esMaps, fieldPkPropName);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                EsUtils.CloseRestClient(restClient);
            }
        }
    }


    /**
     * 根据多个索引名称统计es中数据量
     *
     * @param indexNames
     * @return
     */
    @Override
    public int getCountByIndexes(String indexNames) {
        int result = 0;
        try {
            restClient = EsUtils.getRestClient();
            result = EsUtils.getCountByIndexName(restClient, indexNames);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            EsUtils.CloseRestClient(restClient);
        }
        return result;

    }

    @Override
    public String queryHealth() {
        String result = null;
        try {
            restClient = EsUtils.getRestClient();
            result = EsUtils.queryHealth(restClient);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            EsUtils.CloseRestClient(restClient);
        }
        return result;
    }


}
