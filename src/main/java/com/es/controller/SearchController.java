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

    @PostMapping("/queryHealth")
    @ResponseBody
    public Response queryHealth() throws Exception {
        RestClient restClient = searchApi.getRestClient();
        String s = searchApi.queryClusterInfo(restClient);
        return Response.success(s);
    }
}
