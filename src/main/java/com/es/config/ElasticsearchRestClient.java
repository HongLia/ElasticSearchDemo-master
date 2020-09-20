package com.es.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchRestClient {

//    @Value("${elasticsearch.ip}")
//    private String[] ips;

    /**
     * Bean name default  函数名字
     * @return
     */
    //@Bean(autowire = Autowire.BY_NAME, name = "restHighLevelClient")

//    @Bean
//    public RestHighLevelClient client() {
//        HttpHost[] httpHosts = (HttpHost[]) Stream.of(ips).map(this::createHttpHost).collect(Collectors.toList()).toArray();
//        RestClientBuilder builder = RestClient.builder(httpHosts);
//        return new RestHighLevelClient(builder);
//    }

    /**
     * 初始化 @Bean(destroyMethod = “close”) 及时关闭资源非常重要
     * @return
     */
//    @Bean(destroyMethod = "close")

    /**
     * 初始化
     * @return
     */
    @Bean
    public RestHighLevelClient client() {

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        // 如果有多个从节点可以持续在内部new多个HttpHost，参数1是ip,参数2是HTTP端口，参数3是通信协议
//                        new HttpHost("192.168.58.103", 9200, "http"),
//                        new HttpHost("192.168.58.104", 9200, "http"),
                        new HttpHost("127.0.0.1", 9200, "http"))

                // .setXXX()... 可以根据需要添加其他配置
        );
        //初始化完成
        return client;
    }

//    private HttpHost createHttpHost(String ip) {
//        return HttpHost.create(ip);
//    }

}
