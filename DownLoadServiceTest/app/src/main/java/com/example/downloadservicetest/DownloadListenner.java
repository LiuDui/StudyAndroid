package com.example.downloadservicetest;

/*
 * 下载过程中的回调
 */
public interface DownloadListenner {
    void onProgress(int progress);
    void onSuccess();
    void onFailed();
    void onPaused();
    void onCanceled();
}
