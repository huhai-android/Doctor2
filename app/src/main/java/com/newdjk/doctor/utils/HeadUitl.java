package com.newdjk.doctor.utils;

import com.newdjk.doctor.MyApplication;

import java.util.HashMap;

public enum HeadUitl {

    instance;

    public HashMap<String, String> getHeads() {
        HashMap<String, String> params = new HashMap<>();
        params.put("Authorization", MyApplication.token);
        return params;
    }
}
