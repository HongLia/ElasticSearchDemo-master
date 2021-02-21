package com.es.controller;

import com.es.EsApi.SearchApi;
import com.es.util.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/createIndex")
    public Response createIndex(@RequestParam String indexName) throws Exception {
        // TODO: 2021/2/19 写死的JSON
        String ss = "\"author\":{\n" +
                "        \"type\": \"keyword\"\n" +
                "      }";
        return Response.success(searchApi.createIndex(indexName, ss));
    }





}
