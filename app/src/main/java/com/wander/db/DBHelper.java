package com.wander.db;

import android.app.Application;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.wander.tt.App;

/**
 * Created by wander on 2016/11/27.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String db_name = "tt.db";
    private static int DATABASE_VERSION = 1;


    private DBHelper() {
        super(App.getInstance(), db_name, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        execSQL(getWritableDatabase(), CREATE_LIST);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //列表
    public static final String LIST_TABLE = "v3_list";
    static final String CREATE_LIST = "CREATE TABLE IF NOT EXISTS [v3_list] (" +
            "[id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            "[cloudid] INTEGER NOT NULL, " +
            "[radioid] INTEGER, " +
            "[name] TEXT NOT NULL, " +
            "[showname] TEXT NOT NULL, " +
            "[uid] INTEGER, " +
            "[username] TEXT NOT NULL, " +
            "[type] TEXT NOT NULL, " +
            "[picture] TEXT NOT NULL, " +
            "[listpath] TEXT NOT NULL, " +
            "[version] INTEGER NOT NULL, " +
            "[syncflag] INTEGER NOT NULL, " +
            "[listsource] TEXT, " +
            "[iswifidownflag] INTEGER NOT NULL DEFAULT (0)," +  //是否支持wifi下自动下载，默认为0，不支持，1：支持，未开启，2：支持，开启动中
            "[extends] TEXT," +
            "[createtime] TEXT)";


    private void execSQL(SQLiteDatabase db, final String sql) {
        try {
            db.execSQL(sql);
        } catch (SQLException sqle) {
        }
    }

    private static DBHelper instance = null;

    static {
        instance = new DBHelper();
    }

    private SQLiteDatabase db;
}
