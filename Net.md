## 使用WebView
WebView在后台处理好了发送Http请求、接受服务器相应、解析返回数据，以及最终的页面展示功能。

> 使用方式：
1. 增加一个WebView控件
2. 在Activity中初始化
```java
WebView webView = findViewById(R.id.wedview_test);
webView.getSettings().setJavaScriptEnabled(true);
webView.setWebViewClient(new WebViewClient());
webView.loadUrl("http://www.baidu.com");
```
3. 控制访问权限
```xml
 <uses-permission android:name="android.permission.INTERNET"/>
 <application
      ...
      android:usesCleartextTraffic="true">
```

## 使用Http协议访问网络
### 使用HttpURLConnetion
网络请求比较耗时，因此放到子线程中去处理。
```java
new Thread(new Runnable() {
            @Override
            public void run() {
                java.net.HttpURLConnection connection = null;
                BufferedReader bf = null;

                try {
                    URL url = new URL("https://www.baidu.com");
                    connection = (java.net.HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    bf = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String line = "";

                    while ((line = bf.readLine()) != null){
                        sb.append(line);
                    }
                    showRespsonse(sb.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
```
网络请求放在了子线程中，但是要想显示在界面上，就必须通过更改UI，而更改UI就必须在main线程中执行。

```java
runOnUiThread(new Runnable() {
    @Override
    public void run() {
        tvResponse.setText(respsons);
    }
});
```
通过这个runOnUIThread可以切换到主线程，同样需要申请网络权限。

只要获得到这个HttpURLConnection的实例，即可通过这个链接得到inputstream和outputstream，与socket比较类似，不同的一点是，这个链接工作在http层，所以需要对一些参数进行设置，比如Method等，同时，所传输的数据也要符合http请求的格式。
```java
DataOutputStream out = new DataOutputStream(connection.getOutputStream());
out.writeBytes("username=admin&password=123456");
```
### 使用OkHttp
开源网络通信库，添加依赖：
```xml
    implementation 'com.squareup.okhttp3:okhttp:3.14.0'
```
使用方式：
1. 创建OkHttpClient的实例
2. 如果要发请求，就创建一个Request对象，使用newCall方法把request和cilent结合起来。
```java

  OkHttpClient client = new OkHttpClient();
  Request request = new Request.Builder()
          .url("https://www.baidu.com")
          .build();
  try {
      Response response = client.newCall(request).execute();
      String responseData = response.body().string();
      showRespose(responseData);
```
