package org.aplas.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class SqliteHelper extends SQLiteOpenHelper{
    private Context context;
    private static SqliteHelper instance;
    //Database name and version
    public static final String DATABASE_NAME = "myappfintech.db";
    public static final int DATABASE_VERSION = 1;

    //Nama Tabel
    public static final String TABLE_NAME = "user";

    //Nama Kolom
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";


    public SqliteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SqliteHelper getInstance(@Nullable Context context) {
        if(instance == null){
            instance = new SqliteHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user(id INTEGER primary key, username TEXT, password TEXT)");
        String sql = "create table tb_trans(id_trans INTEGER PRIMARY KEY AUTOINCREMENT, jumlah INTEGER, keterangan TEXT, tanggal TEXT, flow TEXT)";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists user");
        onCreate(db);

    }
    public Boolean insertData(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username", username);
        values.put("password", password);

        long result = db.insert("user", null, values);
        if (result == -1) return false;
        else
            return true;

    }

//    public Cursor Get(String table){
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.rawQuery("SELECT * FROM " + table, null);
//    }

    public Cursor where(String table, String where) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM" + table + " WHERE " + where, null);
    }

    public Boolean updatePass(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("password", password);

        long result = db.update("user",values,"username=?",new String[]{username});
        return  result != -1;

    }

    public Boolean checkUsername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user where username=?", new String[] {username});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }
    public Boolean checkUsernamePassword(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user where username=? and password=?", new String[] {username,password});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public List<DetailModel> getDataTrans(){
        List<DetailModel> l = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String alldata = "select * from tb_trans";

        Cursor c = db.rawQuery(alldata,null);

        if (c.moveToFirst()) {
            do {
                DetailModel data = new DetailModel(c.getInt(0),c.getInt(1),c.getString(2),c.getString(3), c.getString(4));
                l.add(data);
            } while (c.moveToNext());
        }
        return l;
    }

    public boolean insertTrans(String t, Integer j, String f, String k){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tanggal", t);
        values.put("jumlah", j);
        values.put("flow", f);
        values.put("keterangan", k);

        long result = db.insert("tb_trans", null, values);
        return result != -1;
    }

    public Cursor total(String f, String t, String w){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT SUM(" + f + ") AS result FROM " + t + " WHERE " + w, null);
    }

}
