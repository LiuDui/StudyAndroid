package com.example.runtimepermissiontest;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class MyProvider extends ContentProvider {

    public static final int TABLE1_DIR = 0;
    public static final int TABLE1_ITEM = 1;
    public static final int TABLE2_DIR = 2;
    public static final int TABLE2_ITEM = 3;

    private static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.example.runtimepermissiontest.provider",
                "table1", TABLE1_DIR);
        uriMatcher.addURI("com.example.runtimepermissiontest.provider",
                "table1", TABLE1_ITEM);
        uriMatcher.addURI("com.example.runtimepermissiontest.provider",
                "table2", TABLE2_DIR);
        uriMatcher.addURI("com.example.runtimepermissiontest.provider",
                "table2", TABLE2_ITEM);
    }

    /**
     * 当ContentResolver尝试访问当前程序数据时，内容提供器被初始化，此时会调用该函数
     * 通常在这里完成度数据库的创建和升级操作
     * 返回true表示内容提供器初始化成功，返回false表示失败
     */
    @Override
    public boolean onCreate() {
        return false;
    }


    @Override
    public Cursor query( Uri uri,  String[] projection, String selection,  String[] selectionArgs,  String sortOrder) {
        switch (uriMatcher.match(uri)){
            case TABLE1_DIR:
                // 查询table1中的内容
                break;
            case TABLE1_ITEM:
                //查询table1中的单条内容
                break;
            case TABLE2_DIR:
                // 查询table2中的内容
                break;
            case TABLE2_ITEM:
                // 查询table2中的单条内容
                break;
            default:
                break;
        }
        return null;
    }

    /**
     * 根据传入的内容URI来返回相应的MIME类型
     * 是一种固定格式
     */
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case TABLE1_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.runtimepermissiontest.provider.table1";
            case TABLE1_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.runtimepermissiontest.provider.table1";
            case TABLE2_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.runtimepermissiontest.provider.table2";
            case TABLE2_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.runtimepermissiontest.provider.table2";
            default:
                break;
        }
        return null;
    }

    @Override
    public Uri insert( Uri uri,  ContentValues values) {
        return null;
    }

    @Override
    public int delete( Uri uri,  String selection,  String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update( Uri uri, ContentValues values,  String selection, String[] selectionArgs) {
        return 0;
    }
}
