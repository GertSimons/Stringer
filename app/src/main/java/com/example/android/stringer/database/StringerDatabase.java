package com.example.android.stringer.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;


import static android.content.ContentValues.TAG;

@Database(entities = {Client.class , Strings.class}, version = 1 , exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class StringerDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "stringers";
    private static final Object LOCK = new Object();
    private static StringerDatabase sInstance;


    public static StringerDatabase getInstance(Context context){
        if(sInstance == null){
            synchronized (LOCK){
                Log.d(TAG, "Creating new Stringerdatabase");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        StringerDatabase.class, StringerDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(TAG, "getting the database instance");
        return sInstance;
    }

    public abstract StringerDao stringerDao();
}
