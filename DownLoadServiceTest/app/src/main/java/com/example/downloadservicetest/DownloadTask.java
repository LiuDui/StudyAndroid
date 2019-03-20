package com.example.downloadservicetest;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

// 三个参数分别是 启动时传入后台的参数  进度显示单位  结果反馈
public class DownloadTask extends AsyncTask<String, Integer, Integer> {
    public static final int TYPE_SUCCESS = 0;
    public static final int TYPE_FAILED = 1;
    public static final int TYPE_PAUSED = 2;
    public static final int TYPE_CANCELED = 3;

    private DownloadListenner listenner;
    private boolean isCanceled = false;
    private boolean isPaused = false;
    private int lastProgress;

    public DownloadTask(DownloadListenner listenner){
        this.listenner = listenner;
    }

    @Override
    protected Integer doInBackground(String... strings) {
        InputStream in = null;
        RandomAccessFile savedFile = null;
        File file = null;

        try {
            long downloadLength = 0;// 记录已下载的文件长度
            String downloadUrl = strings[0];
            String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
            String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();

            file = new File(directory + fileName);
            if (file.exists()){
                downloadLength = file.length();
            }
            long contentlength = getContentLength(downloadUrl);

            if(contentlength == 0) {
                return TYPE_FAILED;
            }

            if (contentlength == downloadLength){
                // 相等就完成下载了
                return TYPE_SUCCESS;
            }

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("RANGE", "bytes=" + downloadLength + "-")
                    .url(downloadUrl)
                    .build();
            Response response = client.newCall(request).execute();

            if (response != null) {
                in = response.body().byteStream();
                savedFile = new RandomAccessFile(file, "rw");
                savedFile.seek(downloadLength); // 跳过已下载的字节
                byte[] b = new byte[1024];
                int total = 0;
                int len;

                while((len = in.read(b)) != -1){
                    if (isCanceled){
                        return TYPE_CANCELED;
                    }else if(isPaused){
                        return TYPE_PAUSED;
                    }else {
                        total += len;
                        savedFile.write(b, 0, len);
                        // 计算已经下载完的百分比
                        int progress = (int) ((total + downloadLength) * 100 / contentlength);
                        publishProgress(progress);
                    }
                }
                response.body().close();
                return TYPE_SUCCESS;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if(in != null ){
                    in.close();
                }
                if (savedFile != null){
                    savedFile.close();
                }

                if(isCanceled && file != null){
                    file.delete();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return TYPE_FAILED;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress = values[0];
        if(progress > lastProgress){
            listenner.onProgress(progress);
            lastProgress = progress;
        }
    }

    @Override
    protected void onPostExecute(Integer status) {
        switch (status){
            case TYPE_SUCCESS:
                listenner.onSuccess();
                break;
            case TYPE_FAILED:
                listenner.onFailed();
                break;
            case TYPE_PAUSED:
                listenner.onPaused();
                break;
            case TYPE_CANCELED:
                listenner.onCanceled();
                break;
            default:
                break;
        }
    }

    public void pasuseDownload(){
        isPaused = true;
    }

    public void cancelDownload(){
        isCanceled = true;
    }

    private long getContentLength(String downloadUrl)throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(downloadUrl)
                .build();

        Response response = client.newCall(request).execute();
        if(response != null && response.isSuccessful()){
            long contentLength = response.body().contentLength();
            response.body().close();
            return contentLength;
        }
        return 0;
    }
}
