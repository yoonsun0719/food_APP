package com.example.food;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

//SQLiteOpenHelper상속받아 사용
public class FoodDB extends SQLiteOpenHelper {
    public FoodDB(@Nullable Context context) {
        super(context,"food.db",null,1);
    }

    @Override

    public void onCreate(SQLiteDatabase db) {
        String sql = "create table food(seq integer primary key autoincrement,";
        sql += "name text, tel text, address text, latitude double, longitude double,";
        sql += "description text, photo text, keep integer);";
        db.execSQL(sql);

        sql= "insert into food(name,address,tel,latitude,longitude,description,photo,keep)";
        sql+= " values('명태어장','인천광역시 서구 경서동 976-41','0325688665',37.5242415,126.6277588,'밑반찬도 잘나오고 콩나물은 그냥 먹어도 되고 명태조림에 넣어서 비벼먹으면 더 맛있고 구운김에 명태조림을 싸서 먹어요.','/sdcard/DCIM/Camera/image1.jpg',1);";
        db.execSQL(sql);

        sql= "insert into food(name,address,tel,latitude,longitude,description,photo,keep)";
        sql+= " values('선식당','인천광역시 서구 경서동 보석로11번길 13','0325635635',37.5255,126.6308,'청라5단지 선식당은 퓨전요리식당이예요. 메뉴고르기 힘들 때 오면 딱이에요. 중식,양식 한식이 다 있어요.','/sdcard/DCIM/Camera/image2.jpg',0);";
        db.execSQL(sql);

        sql= "insert into food(name,address,tel,latitude,longitude,description,photo,keep)";
        sql+= " values('홍익돈까스','인천광역시 서구 청라동 132-2','0325635635',37.5389135,126.6583786,'홍익 돈까스는 홍익의 정신을 바탕으로 고객 만족을 최우선으로 생각하며 최고의 맛과 서비스를 제공하기위해 노력합니다.','/sdcard/DCIM/Camera/image3.jpg',1);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
