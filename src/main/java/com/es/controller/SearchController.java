package com.es.controller;

import com.es.EsApi.SearchApi;
import com.es.util.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {
    @Autowired
    SearchApi searchApi;

    @PostMapping("/searchTest")
    @ResponseBody
    public Response search() throws Exception {

        RestClient restClient = searchApi.getRestClient();
        searchApi.queryClusterInfo(restClient);
        boolean big = searchApi.indexIsExist(restClient, "fas_index");
        System.out.println("big = " + big);

        String keyword = "123";

        searchApi.multiQuery(keyword);


        return null;
    }
}
