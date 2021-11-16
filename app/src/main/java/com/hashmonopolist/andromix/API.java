package com.hashmonopolist.andromix;

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

public class API {
    public String server;
    public RequestQueue requestQueue;
    public Cache cache;
    public Network network;

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

    public void loginARL(String arl, LoginARLResponse loginARLResponse) {
        String url = server + "/api/login-arl?arl=" + arl;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);
            }
        }, System.out::println) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                assert response.allHeaders != null;
                for (Header header : response.allHeaders) {
                    if (header.getName().equals("Set-Cookie")) {
                        loginARLResponse.onSuccess(response);
                    }
                }
                return super.parseNetworkResponse(response);
            }
        };
        requestQueue.add(stringRequest);
    }

    public void addToQueue(String id) {
        String url = this.server+"/api/addToQueue";
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