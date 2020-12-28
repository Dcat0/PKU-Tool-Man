package com.example.pkutoolman;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.pkutoolman.baseclass.Data;

public class ChatDatabase_sqlite extends SQLiteOpenHelper {
    public ChatDatabase_sqlite(Context context) {
        super(context,"chat" + Integer.toString(Data.getUserID()),null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String databaseName = "chat" + Integer.toString(Data.getUserID());
        db.execSQL("create table if not exists " + databaseName + "(order_id integer,sender_id integer,receiver_id integer,message_time varchar(100),message_content text)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}