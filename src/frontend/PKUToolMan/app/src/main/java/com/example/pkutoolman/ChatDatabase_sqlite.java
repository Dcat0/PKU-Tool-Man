package com.example.pkutoolman;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ChatDatabase_sqlite extends SQLiteOpenHelper {
    public ChatDatabase_sqlite(Context context) {
        super(context,"chat",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists chat(order_id integer,sender_id integer,receiver_id integer,message_content text,message_time varchar(100))");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}