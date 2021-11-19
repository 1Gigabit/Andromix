package com.hashmonopolist.andromix;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hashmonopolist.andromix.gson.SearchResults;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class API {
    public String server;
    public RequestQueue requestQueue;
    public Cache cache;
    public Network network;
    public String cookie;
    public API(String server, File cache) {
        if (server.endsWith("/")) {
            this.server = server.substring(0, server.length() - 1);
        } else {
            this.server = server;
        }
        this.network = new BasicNetwork(new HurlStack());
        this.cache = new DiskBasedCache(cache, 1024 * 1024);
        requestQueue = new RequestQueue(this.cache, network);
        requestQueue.start();
    }

    public interface LoginARLResponse {
        void onSuccess(NetworkResponse networkResponse);
    }
    public interface AddToQueueResponse {
        void onSuccess(String networkResponse);
    }
    public interface SearchResponse {
        void onSuccess(SearchResults searchResults);
    }
    public void loginARL(String arl, LoginARLResponse loginARLResponse) {
        String url = server + "/api/login-arl?arl=" + arl;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, System.out::println, System.out::println) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                cookie = response.headers.get("Set-Cookie");
                loginARLResponse.onSuccess(response);
                return super.parseNetworkResponse(response);
            }
        };
        requestQueue.add(stringRequest);
    }

    public void addToQueue(String id,String type, AddToQueueResponse addToQueueResponse) {
        String url = this.server+"/api/addToQueue?url=https://www.deezer.com/"+type+"/"+id;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            System.out.println(response);
            addToQueueResponse.onSuccess(response);
        }, System.out::println) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Cookie",cookie);
                return headers;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void mainSearch(String term, SearchResponse searchResponse) {
        String url = this.server + "/api/mainSearch?term="+term;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            Gson gson = new GsonBuilder().create();
            searchResponse.onSuccess(gson.fromJson(response,SearchResults.class));
        }, System.out::println);
        requestQueue.add(stringRequest);
    }
}
/*
public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Cookie","ARL="+arl);
                    System.out.println(headers);
                    return headers;
                }
 */