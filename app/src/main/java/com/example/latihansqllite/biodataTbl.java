package com.example.latihansqllite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Random;

public class biodataTbl extends SQLiteOpenHelper {

    Context context;
    Cursor cursor;
    SQLiteDatabase database;

    public static String nama_database="data";
    public  static  String nama_table ="biodata";


    public biodataTbl(@Nullable Context context) {
        super(context, nama_database, null, 3);
        this.context=context;
        database=getReadableDatabase();
        database=getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE IF NOT EXISTS " +nama_table+" (id varchar(50), nama varchar(50), alamat varchar(50))";
        db.execSQL(query);

    }

    String random(){
        //100-999
        int acak = new Random().nextInt(888+1)+100;
        return String.valueOf(acak);
    }

    void simpan_data(String nama, String alamat){
        database.execSQL(
                "INSERT INTO " + nama_table+" values" + "('"+random()+"','"+nama+"','"+alamat+"')"
        );

        Toast.makeText(context, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
    }

    void update_data(String id, String nama, String alamat){
        database.execSQL("UPDATE "+nama_table+" SET nama='"+nama+"', alamat='"+alamat+"'" + "WHERE id='"+id+"'" + "");
        Toast.makeText(context, "Berhasil Update data", Toast.LENGTH_SHORT).show();
    }

    void delete(String id){
        database.execSQL("DELETE FROM "+nama_table+" WHERE id='"+id+"'");
        Toast.makeText(context, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
    }

    Cursor tampil_data(){
        Cursor cursor = database.rawQuery(
                "SELECT * FROM "+nama_table, null
        );
        return cursor;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
