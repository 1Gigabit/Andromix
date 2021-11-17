package com.hashmonopolist.andromix;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Header;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

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
        void onSuccess(NetworkResponse networkResponse);
    }
    public void loginARL(String arl, LoginARLResponse loginARLResponse) {
        String url = server + "/api/login-arl?arl=" + arl;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> System.out.println(response), System.out::println) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                cookie = response.headers.get("Set-Cookie");
                return super.parseNetworkResponse(response);
            }
        };
        requestQueue.add(stringRequest);
    }

    public void addToQueue(String id, AddToQueueResponse addToQueueResponse) {
        String url = this.server+"/api/addToQueue";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> System.out.println(response), System.out::println) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Cookie",cookie);
                return super.getHeaders();
            }
        };
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