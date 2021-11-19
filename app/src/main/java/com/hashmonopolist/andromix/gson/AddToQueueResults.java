package com.hashmonopolist.andromix.gson;

import com.google.gson.annotations.Expose;

public class AddToQueueResults {
    @Expose
    boolean result;
    @Expose
    String errid;

    public String getErrid() {
        return errid;
    }

    public boolean getResult() {
        return result;
    }
}
