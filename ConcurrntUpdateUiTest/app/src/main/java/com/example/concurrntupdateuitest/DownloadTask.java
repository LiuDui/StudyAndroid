package com.example.concurrntupdateuitest;

import android.os.AsyncTask;

public class DownloadTask extends AsyncTask<Void, Integer, Boolean> {

    //这个方法会在后台任务开始执行之前调用，用于进行一些界面上的初始化操作，比如显示一个进度条对话框
    @Override
    protected void onPreExecute() { super.onPreExecute(); }

    //该方法中的所有代码都在子线程中执行，应该在这里处理所有的耗时任务,一旦完成任务，可以通过return返回执行结果，
    @Override// 这个方法不能进行UI操作,如果需要，可以调用publishProgress(Prosess...)方法完成
    protected Boolean doInBackground(Void... voids) {return null;}
    //当后台任务中调用了publishProgress(Progress...)方法后，还方法会被调用，参数就是后台任务传递过来的，可以对UI进行操作。
    @Override
    protected void onProgressUpdate(Integer... values) { super.onProgressUpdate(values); }
    //当后台任务执行完毕并通过return语句进行返回时，这个方法就会很快得到调用，返回的数据作为参数传递到此方法中，可以利用返回的数据来进行
    // 一些UI操作
    @Override
    protected void onPostExecute(Boolean aBoolean) {super.onPostExecute(aBoolean);  }
    @Override
    protected void onCancelled(Boolean aBoolean) { super.onCancelled(aBoolean); }

    @Override
    protected void onCancelled() {super.onCancelled(); }
}
