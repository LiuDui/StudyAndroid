## 数据持久化

>安卓中主要提供了三种持久化技术：
* 文件存储
* SharedPreference存储
* 数据库存储

### 文件存储
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

### SharedPreference存储
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

### 数据库存储
#### SQLite数据库存储
>简介：
* android内置的一个轻量级关系型数据库。
* 运算速度快，占用内存少。
* 支持标准SQL语法
* 遵循数据库ACID事务

> 提供的数据类型：
* integer 整型
* real 浮点型
* text 文本类型
* blob 二进制类型

> 使用方式：通过抽象类：SQLLiteOpenHelp，继承，实现抽象方法。


#### 创建数据库
通过继承的方式
```java
public class MyDatabaseHelper extends SQLiteOpenHelper {

    private String SQLOnCreate = null;

    private Context mContext = null;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    public void setSQLOnCreate(String SQLOnCreate) {
        this.SQLOnCreate = SQLOnCreate;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLOnCreate);
        Log.d("MyDatabaseHelper SQL", "onCreate Success");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
```
其中的onCreate()方法会在程序中查找相关数据库（而不是找表），，若发现没有时被调用，比如方法：
```java
 myDatabaseHelper.getWritableDatabase();
```

#### 升级数据库（onUpgrade）
主要用于数据库的更新，比如之前已经增加过一张Book表
```java
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_layout);

        myDatabaseHelper = new MyDatabaseHelper(this, "BookStore.db", null, 1);

        String sql = "create table book(id integer primary key autoincrement, author text, price real, pages integer, name text)";

        myDatabaseHelper.setSQLOnCreate(new String[]{sql});
        Button btnDatabaseCreate = findViewById(R.id.btn_database_create);

        btnDatabaseCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDatabaseHelper.getWritableDatabase();
            }
        });

    }
```
当第一次点击按钮时，getWritableDatabase()方法被执行，检测没有BookStore.db，则会调用onCreate()方法，但是当第一次点击后，数据库存在，那么再点击，将不会再执行，此时如果想添加表，就需要用到onUpgrade()方法，方法触发条件时**数据库版本的变化**，也就是下面第四个参数。变成其他数字就可以执行onUpgrade()方法了。

```java
myDatabaseHelper = new MyDatabaseHelper(this, "BookStore.db", null, 1);
```

如果有办法得到onCreate方法中的参数
```java
SQLiteDatabase db
```
也能执行，而SQLiteDatabase对象在SQLiteOpenHelp的getReadableDatabase()和getWritableDatabase()方法后可以获得。


#### 增删改查
###### 增加
增删改查需要借助SQLiteDatabase对象，也就是通过那两个方法的返回值得到。

> 增加数据: SQLiteDatabase的insert()方法，三个参数
* table:String 往哪个表里写
* nullColumnHack:String 未指定添加数据的情况下，给某些可为空的列自动赋值NULL，一般用不到，传NULL即可
* values:ContentValues Contentvalue对象提供一些列put()方法重载，用于向ContentValues中添加数据。

```java
public class DatabaseActivity extends AppCompatActivity {

    private MyDatabaseHelper myDatabaseHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_layout);

        myDatabaseHelper = new MyDatabaseHelper(this, "BookStore.db", null, 2);
        String sql = "create table book(id integer primary key autoincrement, author text, price real, pages integer, name text)";
        String sqlCategory = "create table Category(id integer primary key autoincrement, category_name text, category_code integer)";
        myDatabaseHelper.setSQLOnCreate(new String[]{sql, sqlCategory});
        Button btnDatabaseCreate = findViewById(R.id.btn_database_create);
        Button btnDatabaseAddValue = findViewById(R.id.btn_database_addvalue);

        btnDatabaseCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDatabaseHelper.getWritableDatabase();
            }
        });

        btnDatabaseAddValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("name", "The Da Vinci Code");
                contentValues.put("author", "Dan Brown");
                contentValues.put("pages", 454);
                contentValues.put("price", 16.9);
                db.insert("Book", null, contentValues);
                Log.d("DatabaseActivity SQL", "add value"); // 点一次，加一次
                // 插入第二条数据
                //contentValues.clear();
                //...
            }
        });
    }
}
```
###### 更新
与增加类似
```java
btnDatabaseUpdateValue.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("price", 17);
        db.update("Book", contentValues, "name=?", new String[]{"The Da Vinci Code"});
        Log.d("DatabaseActivity SQL", "update value");
    }
});
```
三个参数，那个表，新数据源，约束条件（留占位），占位的数据。

###### 删除数据
```java
btnDatabaseDeleteValue.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
        db.delete("Book", "pages > ?", new String[]{"300"});
    }
});
```

###### 查询
查询最麻烦，方法参数：

```java
public Cursor query(String table, String[] columns, String selection,
            String[] selectionArgs, String groupBy, String having,
            String orderBy) {

        return query(false, table, columns, selection, selectionArgs, groupBy,
                having, orderBy, null /* limit */);
    }
```

| query()方法参数 | 对应SQL部分     | 描述|
| :------------- | :------------- |:-----|
| table       | from tabel_name       | 指定查询的表名|
| columns     | select columns | 指定查询的列名 |
| selectionArgs | - | 为where的占位符提供具体的值|
| groupBy     | group by columns | 指定需要group by的列|
| having      | having column= value | 对group by后的结果进行进一步约束|
| order by |order by column1， column2| 指定查询结果的排序方式|

```java
btnDatabaseQueryValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
                Cursor cursor = db.query("Book", null, null, null, null, null, null);
                if (cursor.moveToFirst()){
                    do {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("DatabaseActivity SQL", "name :" + name);
                        Log.d("DatabaseActivity SQL", "author :" + author);
                        Log.d("DatabaseActivity SQL", "pages :" + pages);
                        Log.d("DatabaseActivity SQL", "price :" + price);

                    }while (cursor.moveToNext());
                }
                cursor.close();
                Log.d("DatabaseActivity SQL", "query value");
            }
        });
```

### 使用LitePal操作数据库
#### 简介
>LitePal是开源的Android数据库框架，采用对象关系映射（ORM）的模式，并将平时开发最常用到的一些数据库功能进行了封装，不用编写SQL即可完成相关工作。
#### 配置
1. 添加依赖：在github主页查
```xml
implementation 'org.litepal.android:java:3.0.0'
```
2. 配置litepal.xml文件
新建main/assets/litepal.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<litepal>
    <dbname value = "BookStore" ></dbname>
    <version value = "1" ></version>
    <list>
    </list>
</litepal>
```
3. 在自愿清单文件中加入：
```XML
 android:name="org.litepal.LitePalApplication"
```
保证该框架的正常工作。

#### 创建和升级数据库
