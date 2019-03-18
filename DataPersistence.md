## 数据持久化

>安卓中主要提供了三种持久化技术：
* 文件存储
* SharedPreference存储
* 数据库存储

#### 文件存储
* 不对数据做任何的格式化处理，原封不动保存
* 适合存储一些简单的文本数据或二进制数据
* 默认的文件放在/data/data/<package name\>/下面

具体操作：
```java
// 保存 ， 两个模式：Context.MODE_PRIVATE 覆盖
//                  Context.MODE_APPEND 追加
private void save(String data){
        FileOutputStream out = null;
        BufferedWriter bf = null;
        try {
            out = openFileOutput("data", Context.MODE_PRIVATE);
            bf = new BufferedWriter(new OutputStreamWriter(out));
            bf.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bf != null){
                    bf.close();
                    bf = null;
                }
                if (out != null){
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
```

```java
// 不需要指定模式
private String loadFromFile(){
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();

        try {
            in = openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null){
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }
```

#### SharedPreference存储
* 使用键值对的方式存储数据，支持多种数据存储类型，但是不适合保存大量数据

> 使用方式：
1. 获取到SharedPreferences对象，有三种方式
    * Contex类中的getSharedPreferences()方法，接收两个参数，一个用于指定文件的名称，一个用于指定操作模式。文件默认放在/data/data/ [package name]/shared_prefs/目录下。
    操作模式只有一种：MODE_PROVATE，表示只有当前应用程序才可以对该文件进行读写。
    * Activity类中的getPreferences()方法，只接受一个指定模式的参数，因为自动将当前活动的类名作为SharedPreference的文件名。
    * PreferenceManager类中的getDegasultSharedPregerences()方法。静态方法，接收一个Contex参数，并自动使用当前应用程序的包名作为前缀开命名SharedPreferences文件。
2. 存储数据
    - 1.调用SharedPreference对象的edit()方法获取一个SharedPreferences.Editor对象。
    - 2.像ShardPreferences.Editor对象中添加数据。
    - 3.调用apply方法提交数据。

存储一部分数据：
```java
SharedPreferences.Editor editor = getSharedPreferences("data",
        MODE_PRIVATE).edit();
editor.putBoolean("gender", true);
editor.putString("name", "tony");
editor.apply();
```
打开文件看一下，原来用的时XML存储的，所以存储的一些特点与XML类似。
```XML
<map>
    <boolean name="gender" value="true" />
    <string name="name">tony</string>
</map>
```

#### 数据库存储
##### SQLite数据库存储
> * android内置的一个轻量级关系型数据库。
* 运算速度快，占用内存少。
* 支持标准SQL语法
* 遵循数据库ACID事务

###### 创建数据库
