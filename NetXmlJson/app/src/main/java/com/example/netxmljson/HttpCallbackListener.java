package com.example.netxmljson;

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
