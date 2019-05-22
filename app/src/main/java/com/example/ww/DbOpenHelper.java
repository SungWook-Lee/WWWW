package com.example.ww;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper {

    private static final String DATABASE_NAME = "PlaceInfo(SQLite).db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;


    private class DatabaseHelper extends SQLiteOpenHelper{

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(Place_DB.CreateDB._CREATE0);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+Place_DB.CreateDB._TABLENAME0);
            onCreate(db);
        }
    }

    public DbOpenHelper(Context context){
        this.mCtx =context;
    }

    public DbOpenHelper open() throws SQLException{
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void create(){
        mDBHelper.onCreate(mDB);
    }

    public void close(){
        mDB.close();
    }

    //insert DB
    public long insertColumn(String place, String latitude, String longitude){
        ContentValues values = new ContentValues();
        values.put(Place_DB.CreateDB.PLACE, place);
        values.put(Place_DB.CreateDB.LATITUDE, latitude);
        values.put(Place_DB.CreateDB.LONGITUDE, longitude);

        return mDB.insert(Place_DB.CreateDB._TABLENAME0,null,values);
    }

    //update DB
    public boolean updateColumn(long id, String place, String latitude, String longitude){
        ContentValues values = new ContentValues();
        values.put(Place_DB.CreateDB.PLACE, place);
        values.put(Place_DB.CreateDB.LATITUDE, latitude);
        values.put(Place_DB.CreateDB.LONGITUDE, longitude);
        return mDB.update(Place_DB.CreateDB._TABLENAME0, values, "_id="+id, null) > 0;
    }

    //delete all
    public void deletAllColumns(){
        mDB.delete(Place_DB.CreateDB._TABLENAME0,null,null);
    }

    //delete DB
    public boolean deleteColumn(long id){
        return mDB.delete(Place_DB.CreateDB._TABLENAME0,"_id="+id,null) > 0;
    }

    //select DB
    public Cursor selectColums(){
        return mDB.query(Place_DB.CreateDB._TABLENAME0,null,null,null,null,null,null);
    }

    //sort by column
    public Cursor sortColumn(String sort){
        Cursor c = mDB.rawQuery( "SELECT * FROM place_table ORDER BY " + sort + ";", null);
        return c;
    }
}
