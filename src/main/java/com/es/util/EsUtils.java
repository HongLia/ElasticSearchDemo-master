package com.es.util;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
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
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @auther zhl
 * @description
 * @date 2020/11/16
 */
public class EsUtils {

    private static final Logger LOG = LoggerFactory.getLogger(RestClientTest.class);

    public static final String CONFIGURATION_FILE_NAME = "es-example.properties";
    //是否需要文件验证
    private static String isSecureMode;
    private static String esServerHost;
    private static String pluginsIK;
    private static String pluginsPinyin;
    private static int MaxRetryTimeoutMillis;
    private static int shardNum;
    private static int replicaNum;
    private static int ConnectTimeout;
    private static int SocketTimeout;
    private static String schema = "https";   //当是否需要验证isSecureMode为false，schema为http

    /**
     * 初始化
     *
     * @throws Exception
     */
    private static void initProperties() {
        esServerHost = PropertiesUtil.getpropetyByfile("EsServerHost", CONFIGURATION_FILE_NAME);
        MaxRetryTimeoutMillis = Integer.valueOf(PropertiesUtil.getpropetyByfile("MaxRetryTimeoutMillis", CONFIGURATION_FILE_NAME));
        ConnectTimeout = Integer.valueOf(PropertiesUtil.getpropetyByfile("ConnectTimeout", CONFIGURATION_FILE_NAME));
        SocketTimeout = Integer.valueOf(PropertiesUtil.getpropetyByfile("SocketTimeout", CONFIGURATION_FILE_NAME));
        isSecureMode = PropertiesUtil.getpropetyByfile("isSecureMode", CONFIGURATION_FILE_NAME);
        shardNum = Integer.valueOf(PropertiesUtil.getpropetyByfile("shardNum", CONFIGURATION_FILE_NAME));
        replicaNum = Integer.valueOf(PropertiesUtil.getpropetyByfile("replicaNum", CONFIGURATION_FILE_NAME));
        replicaNum = Integer.valueOf(PropertiesUtil.getpropetyByfile("replicaNum", CONFIGURATION_FILE_NAME));
        pluginsIK = PropertiesUtil.getpropetyByfile("pluginsIK", CONFIGURATION_FILE_NAME);
        pluginsPinyin = PropertiesUtil.getpropetyByfile("pluginsPinyin", CONFIGURATION_FILE_NAME);

        LOG.info("EsServerHost:" + esServerHost);
        LOG.info("MaxRetryTimeoutMillis:" + MaxRetryTimeoutMillis);
        LOG.info("ConnectTimeout:" + ConnectTimeout);
        LOG.info("SocketTimeout:" + SocketTimeout);
        LOG.info("shardNum:" + shardNum);
        LOG.info("replicaNum:" + replicaNum);
        LOG.info("isSecureMode:" + isSecureMode);
        LOG.info("pluginsIK:" + pluginsIK);
        LOG.info("pluginsPinyin:" + pluginsPinyin);

    }

    /**
     * 获取验证文件
     *
     * @throws Exception
     */
    private static void setSecConfig() {

        String krb5ConfFile = System.getProperty("user.dir") + File.separator + "src\\main\\java\\com\\fas\\search\\config\\es" + File.separator + "krb5.conf";

        LOG.info("krb5ConfFile: " + krb5ConfFile);
        System.setProperty("java.security.krb5.conf", krb5ConfFile);
        System.out.println("krb5ConfFile: " + krb5ConfFile);

        String jaasPath = System.getProperty("user.dir") + File.separator + "src\\main\\java\\com\\fas\\search\\config\\es" + File.separator + "jaas.conf";

        LOG.info("jaasPath: " + jaasPath);
        System.out.println("jaasPath: " + jaasPath);
        System.setProperty("java.security.auth.login.config", jaasPath);
        System.setProperty("javax.security.auth.useSubjectCredsOnly", "false");

        //add for ES security indication
        System.setProperty("es.security.indication", "true");
        LOG.info("es.security.indication is  " + System.getProperty("es.security.indication"));

    }

    /**
     * 获取es客户端
     *
     * @throws Exception
     */
    public static RestClient getRestClient() throws Exception {
        initProperties();
        RestClientBuilder builder = null;
        RestClient restClient = null;
        HttpHost[] HostArray = getHostArray(esServerHost);
        if (isSecureMode.equals("true")) {
            setSecConfig();
            builder = RestClient.builder(HostArray);
        } else {
            System.setProperty("es.security.indication", "false");
            builder = RestClient.builder(HostArray);
        }
        Header[] defaultHeaders = new Header[]{new BasicHeader("Accept", "application/json"),
                new BasicHeader("Content-type", "application/json")};

        builder = builder.setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
            @Override
            public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder requestConfigBuilder) {
                return requestConfigBuilder.setConnectTimeout(ConnectTimeout).setSocketTimeout(SocketTimeout);
            }
        }).setMaxRetryTimeoutMillis(MaxRetryTimeoutMillis);

        builder.setDefaultHeaders(defaultHeaders);
        restClient = builder.build();
        LOG.info("The RestClient has been created !");
        restClient.setHosts(HostArray);
        return restClient;
    }

    /**
     * Get hostArray by esServerHost in property file
     *
     * @param esServerHost
     * @return
     */
    public static HttpHost[] getHostArray(String esServerHost) {

        //如果不需要进行登录验证，请求协议改为http
        if (("false").equals(isSecureMode)) {
            schema = "http";
        }
        List<HttpHost> hosts = new ArrayList<HttpHost>();
        String[] hostArray1 = esServerHost.split(",");

        for (String host : hostArray1) {
            String[] ipPort = host.split(":");
            HttpHost hostNew = new HttpHost(ipPort[0], Integer.valueOf(ipPort[1]), schema);
            hosts.add(hostNew);
        }
        return hosts.toArray(new HttpHost[]{});
    }


    /**
     * 创建索引
     *
     * @param restClient
     * @param indexName
     * @throws Exception
     */
    public static int createIndex(RestClient restClient, String indexName, String mappingJsonString) throws Exception {
//        initProperties();
        // TODO: 2021/2/23 判断分词器是否安装的代码逻辑在上一层编写
        String indexJsonString = handleSettingIndex();

        int result = 0;
        Response rsp = null;
//        Map<String, String> params = Collections.singletonMap("pretty", "true");
        String jsonString = "{" + "\"settings\":{" + "\"number_of_shards\":\"" + shardNum + "\","
                + "\"number_of_replicas\":\"" + replicaNum + "\"," + indexJsonString + "}," + mappingJsonString + "}";
//        LOG.info("Create index  settings and mappings\n" + jsonString);
        System.out.println("Create index  settings and mappings---------->>>>>>>>>>>\n" + jsonString);
        HttpEntity entity = new NStringEntity(jsonString, ContentType.APPLICATION_JSON);
        try {
            Request request = new Request("PUT", "/" + indexName + "?include_type_name=false");
            request.addParameter("pretty", "true");
            request.setEntity(entity);
            rsp = restClient.performRequest(request);
            if (HttpStatus.SC_OK == rsp.getStatusLine().getStatusCode()) {
                LOG.info("CreateIndex successful.");
                System.out.println("CreateIndex successful.");
                result = 1;
            } else {
                LOG.error("CreateIndex failed.");
                System.out.println("CreateIndex failed.");
            }
            LOG.info("CreateIndex response entity is : " + EntityUtils.toString(rsp.getEntity()));
            System.out.println("CreateIndex response entity is : " + EntityUtils.toString(rsp.getEntity()));
        } catch (Exception e) {
            LOG.error("CreateIndex failed, exception occurred.", e);
        }
        return result;
    }

    /**
     * 根据分词器安装情况设置setting  index
     *
     * @return
     */
    private static String handleSettingIndex() {
        String indexJsonString = null;
        if (pluginsIK.equals("true") && pluginsPinyin.equals("true")) {
            //自定义分词器相关配置，支持ik+pinyin同时
            indexJsonString = "\"index\":{\n" +
                    "      \"analysis\":{\n" +
                    "        \"analyzer\":{\n" +
                    "           \"ik_pinyin_analyzer\":{\n" +
                    "            \"type\":\"custom\",\n" +
                    "            \"tokenizer\":\"ik_max_word\",\n" +
                    "            \"filter\":\"pinyin_filter\"\n" +
                    "          }\n" +
                    "        },\n" +
                    "        \"filter\":{\n" +
                    "          \"pinyin_filter\":{\n" +
                    "            \"type\":\"pinyin\",\n" +
                    "            \"keep_first_letter\": false\n" +
                    "          }\n" +
                    "        }\n" +
                    "      }\n" +
                    "    }";
        } else if (pluginsIK.equals("true") && pluginsPinyin.equals("false")) {
            //只有IK

        } else if (pluginsIK.equals("false") && pluginsPinyin.equals("true")) {
            //只有pingyin

        } else {
            //未知
            indexJsonString = "";
        }
        return indexJsonString;
    }


    /**
     * 关闭客户端
     *
     * @param restClient
     */
    public static void CloseRestClient(RestClient restClient) {
        if (restClient != null) {
            try {
                restClient.close();
                LOG.info("Close the client successful.");
            } catch (Exception e1) {
                LOG.error("Close the client failed.", e1);
            }
        }
    }

    /**
     * 判断索引是否存在
     * Check the existence of the index or not.
     *
     * @param restClient
     * @param indexName
     */
    public static boolean exist(RestClient restClient, String indexName) {
        Response rsp = null;
        try {
            Request request = new Request("HEAD", "/" + indexName);
            request.addParameter("pretty", "true");
            rsp = restClient.performRequest(request);
            if (HttpStatus.SC_OK == rsp.getStatusLine().getStatusCode()) {
                LOG.info("Check index successful,index is exist : " + indexName);
                return true;
            }
            if (HttpStatus.SC_NOT_FOUND == rsp.getStatusLine().getStatusCode()) {
                LOG.info("index is not exist : " + indexName);
                return false;
            }

        } catch (Exception e) {
            LOG.error("Check index failed, exception occurred.", e);
        }
        return false;
    }


    /**
     * 删除索引
     * Delete one index
     *
     * @param restClient
     * @param indexName
     */
    public static Boolean deleteIndex(RestClient restClient, String indexName) throws Exception {
        Boolean result = false;
        Response rsp = null;
        try {
            Request request = new Request("DELETE", "/" + indexName + "?&pretty=true");
            rsp = restClient.performRequest(request);
            if (HttpStatus.SC_OK == rsp.getStatusLine().getStatusCode()) {
                LOG.info("deleteIndex successful.");
                result = true;
            } else {
                LOG.error("deleteIndex failed.");
            }
            LOG.info("deleteIndex response entity is : " + EntityUtils.toString(rsp.getEntity()));
        } catch (Exception e) {
            LOG.error("deleteIndex failed, exception occurred.", e);
        }
        return result;
    }

    /**
     * 清空索引数据
     * Delete all documents by query in one index
     *
     * @param restClient
     * @param indexName
     */
    public static Boolean clearDataByIndexName(RestClient restClient, String indexName) throws Exception {
        Boolean result = false;
        String jsonString = "{\n" + "  \"query\": {\n" + "    \"match_all\": {}\n" + "  }\n" + "}";
        HttpEntity entity = new NStringEntity(jsonString, ContentType.APPLICATION_JSON);
        Response response;
        try {
            Request request = new Request("POST", "/" + indexName + "/_delete_by_query");
            request.addParameter("pretty", "true");
            request.setEntity(entity);
            response = restClient.performRequest(request);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                LOG.info("clearDataByIndexName successful.");
                result = true;
            } else {
                LOG.error("clearDataByIndexName failed.");
            }
            LOG.info("clearDataByIndexName response entity is : " + EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            LOG.error("clearDataByIndexName failed, exception occurred.", e);
        }
        return result;
    }


    /**
     * 清空索引数据
     * Write one document into the index
     *
     * @param restClient
     * @param index
     * @param type
     * @param id
     * @param esMap
     */
    public static void putData(RestClient restClient, String index, String type, String id, Map<String, Object> esMap) throws Exception {
        Gson gson = new Gson();

//        Map<String, Object> esMap = new HashMap<>();
//        esMap.put("name", "Happy");
//        esMap.put("author", "Alex Yang");
//        esMap.put("pubinfo", "Beijing,China");
//        esMap.put("pubtime", "2020-01-01");
//        esMap.put("description", "Elasticsearch is a highly scalable open-source full-text search and analytics engine.");

        String jsonString = gson.toJson(esMap);
        LOG.info("PutData docString：" + jsonString);
        HttpEntity entity = new NStringEntity(jsonString, ContentType.APPLICATION_JSON);
        Response rsp = null;
        try {
            Request request = new Request("POST", "/" + index + "/" + type + "/" + id);
            request.addParameter("pretty", "true");
            request.setEntity(entity);
            rsp = restClient.performRequest(request);
            if (HttpStatus.SC_OK == rsp.getStatusLine().getStatusCode() || HttpStatus.SC_CREATED == rsp.getStatusLine().getStatusCode()) {
                LOG.info("PutData successful.");
            } else {
                LOG.error("PutData failed.");
            }
            LOG.info("PutData response entity is : " + EntityUtils.toString(rsp.getEntity()));
        } catch (Exception e) {
            LOG.error("PutData failed, exception occurred.", e);
        }
    }


    /**
     * 批量插入文档。    如果指定的文档id重复，会将原数据覆盖
     * Send a bulk request
     *
     * @param restClient
     * @param index
     * @param type
     * @param esMaps
     * @param fieldPkPropName
     */
    public static void bulk(RestClient restClient, String index, String type, List<Map<String, Object>> esMaps, String fieldPkPropName) {
//        [
//            {"xm":"qqq","zjhm":"123123"},
//            {"xm":"www","zjhm":"23134"},
//            {"xm":"eee","zjhm":"345564"}
//        ]
        try {
            StringEntity entity;
            Gson gson = new Gson();
            String str = "{ \"index\" : { \"_index\" : \"" + index + "\", \"_type\" :  \"" + type + "\"} }";

            StringBuilder builder = new StringBuilder();
            for (Map<String, Object> esMap : esMaps) {
                String strJson = gson.toJson(esMap);
                //处理文档的id，如果配置了主键字段，将制定字段值作为文档的id
                if (fieldPkPropName != null) {
                    String fieldPk = esMap.get(fieldPkPropName) + "";
                    str = "{ \"index\" : { \"_index\" : \"" + index + "\", \"_type\" :  \"" + type + "\",\"_id\": \"" + fieldPk + "\"} }";
                }
                LOG.info("input documents indexMsg：" + str);
//                LOG.info("input documents docMsg：" + strJson);
                builder.append(str).append("\n");
                builder.append(strJson).append("\n");
            }

            entity = new StringEntity(builder.toString(), ContentType.APPLICATION_JSON);
            entity.setContentEncoding("UTF-8");
            Response response;

            Request request = new Request("PUT", "/_bulk");
            request.addParameter("pretty", "true");
            request.setEntity(entity);
            response = restClient.performRequest(request);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                LOG.info("Already input documents success！ ");
            } else {
                LOG.error("Bulk failed.");
            }
//            LOG.info("Bulk response entity is : " + EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            LOG.error("Bulk failed, exception occurred.", e);
        }
    }


    /**
     * 根据多个索引名称统计es中数据量
     *
     * @param restClient
     * @param indexNames
     * @return
     */
    public static int getCountByIndexName(RestClient restClient, String indexNames) throws Exception {
        int result = 0;
        String jsonString = "{\n" + "  \"query\": {\n" + "    \"match_all\": {}\n" + "  }\n" + "}";
        HttpEntity entity = new NStringEntity(jsonString, ContentType.APPLICATION_JSON);
        Response response;
        try {
            Request request = new Request("GET", "/" + indexNames + "/_count");
            request.addParameter("pretty", "true");
            request.setEntity(entity);
            response = restClient.performRequest(request);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                LOG.info("clearDataByIndexName successful.");
                JSONObject object = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
                Object conut = object.get("count");
                if (conut != null) {
                    result = Integer.valueOf((Integer) conut);
                }
            } else {
                LOG.error("clearDataByIndexName failed.");
            }
            LOG.info("clearDataByIndexName response entity is : " + EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            LOG.error("clearDataByIndexName failed, exception occurred.", e);
        }
        return result;
    }


    /**
     * 健康检查
     *
     * @param restClient
     * @return
     */
    public static String queryHealth(RestClient restClient) {

        Response response = null;
        String result = "msg";
        try {
            Request request = new Request("GET", "/_cluster/health");
            request.addParameter("pretty", "true");
            response = restClient.performRequest(request);
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                LOG.info("QueryClusterInfo successful.");
            } else {
                LOG.error("QueryClusterInfo failed.");
            }
            result = EntityUtils.toString(response.getEntity());
            LOG.info("QueryClusterInfo response entity is : " + EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            LOG.error("QueryClusterInfo failed, exception occurred.", e);
        }
        return result;
    }

}
