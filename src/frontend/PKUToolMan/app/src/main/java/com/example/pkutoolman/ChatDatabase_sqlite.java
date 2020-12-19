package com.example.pkutoolman;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ChatDatabase_sqlite extends SQLiteOpenHelper {
    public ChatDatabase_sqlite(Context context) {
        super(context,"day7",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //学生表数据
        db.execSQL("create table chat(order_id integer, sender_id integer,receiver_id integer,message_content varchar(100),message_time varchar(100))");
        //成绩表数据
        //学生表添加数据
        db.execSQL("insert into chat values(1,1,2,'我到了',1,'2020.12.19')");
        db.execSQL("insert into chat values(1,2,1,'好的',1,'2020.12.19')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}