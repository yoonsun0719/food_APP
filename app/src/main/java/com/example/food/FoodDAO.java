package com.example.food;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class FoodDAO {


    //맛집목록출력 메서드
    public static ArrayList<Food> list(FoodDB foodDB, SQLiteDatabase db){
        ArrayList<Food> array=new ArrayList<>();
        String sql="select * from food";
        Cursor cursor=db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            Food vo = new Food();
            vo.setSeq(cursor.getInt(cursor.getColumnIndex("seq")));
            vo.setName(cursor.getString(cursor.getColumnIndex("name")));
            vo.setTel(cursor.getString(cursor.getColumnIndex("tel")));
            vo.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            vo.setLatitude(cursor.getDouble(cursor.getColumnIndex("latitude")));
            vo.setLongitude(cursor.getDouble(cursor.getColumnIndex("longitude")));
            vo.setPhoto(cursor.getString(cursor.getColumnIndex("photo")));
            vo.setKeep(cursor.getInt(cursor.getColumnIndex("keep")));
            array.add(vo);

            System.out.println("이고슨 어레이푸드 리스트"+vo.toString());
        }
        return  array;
    }

}
