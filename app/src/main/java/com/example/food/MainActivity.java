package com.example.food;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //상수지정
    private final static int MAIN_PROFILE = 100;
    private final static int MAIN_REGISTER = 200;
    private final static int LIST_INFO = 300;
    private final static int KEEP_INFO = 400;
    private final static int MAP_INFO=500;

    DrawerLayout drawerLayout; //서랍안에 열리고 닫히는것을 조정하기
    RelativeLayout drawerMain;

    FragmentTransaction tr;
    ArrayList<Food> array; //=====어레이데이터를 넣어줌

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FoodDB foodDB=new FoodDB(this);
        SQLiteDatabase db=foodDB.getWritableDatabase();
        array=FoodDAO.list(foodDB, db);

        drawerLayout=findViewById(R.id.drawer_layout);
        drawerMain=findViewById(R.id.drawer_main);

        getSupportActionBar().setTitle("Main 맛집리스트");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);


        tr=getSupportFragmentManager().beginTransaction();
        // 가장먼저 메인엑티비티에 프레그먼트 출력되는것 확인할수있음
        tr.add(R.id.content_main, new Food_listFragment(array)).commit(); //어레이데이터값을 가지고 옴=====

        //4.네비게이션부에 메뉴를 선택한경우
        NavigationView nav_menu=findViewById(R.id.nav_menu);
        nav_menu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                System.out.println("이곳은메인엑티비=>온네이게이션셀렉티드");
                switch (menuItem.getItemId()){
                    case R.id.nav_map :
                        getSupportActionBar().setTitle("지도리스트");
                        tr=getSupportFragmentManager().beginTransaction();
                        tr.replace(R.id.content_main, new Food_mapFragment(array)).commit();
                        break;
                    case R.id.nav_keep :
                        getSupportActionBar().setTitle("즐겨찾기");
                        tr=getSupportFragmentManager().beginTransaction();
                        tr.replace(R.id.content_main, new Food_keepFragment(array)).commit();
                        break;
                    //맛집리스트 만들어서 목록에 저장하기
                    case R.id.nav_list :
                        getSupportActionBar().setTitle("맞집목록");  //Intent이므로 이곳에서 바꿀수있음. activity는 액티비티 가서바꿔야함.
                        //리플레이하기위해 트레직션 먼저해야함
                        tr=getSupportFragmentManager().beginTransaction();
                        tr.replace(R.id.content_main, new Food_listFragment(array)).commit();
                        break;

                    case R.id.nav_profile :
                        //상수선언후
                        Intent intent=new Intent(MainActivity.this, ProfileActivity.class);
                        startActivityForResult(intent,MAIN_PROFILE);
                        break;
                    case R.id.nav_register :
                        intent =new Intent(MainActivity.this,RegisterActivity.class);
                        startActivityForResult(intent,MAIN_PROFILE);
                        break;

                }
                drawerLayout.closeDrawer(drawerMain);
                return false;
            }
        });
    }
//6.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //이게먼저 있어야 getSerializableExtra 적용될수있다.
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode !=RESULT_OK) return;

        if((ArrayList<Food>)data.getSerializableExtra("array") != null) { //null이아닐경우 값이 존재한다.
            array=(ArrayList<Food>)data.getSerializableExtra("array");


            switch (requestCode) {
            case MAIN_PROFILE :
                System.out.println("PROFILE등록완료 등록완료");
                break;
            case MAIN_REGISTER :
                System.out.println("REGISTER등록완료 등록완료");
                break;
            case LIST_INFO :
                System.out.println("FOODINFO수정완료 수정완료 메인엑티비티-온엑티비티리설트");

                    tr=getSupportFragmentManager().beginTransaction();
                    tr.replace(R.id.content_main, new Food_listFragment(array)).commit(); //변경할게 있을때만 넣어주면됨
                    break;

            case KEEP_INFO :
                System.out.println("INFO 즐겨찾기 정보");
                tr=getSupportFragmentManager().beginTransaction();
                tr.replace(R.id.content_main, new Food_keepFragment(array)).commit(); //변경할게 있을때만 넣어주면됨
                break;
            //맵프래그먼트에 어레이 값 넣어주기 (하지 않으면 지도리스트->목록선택->즐겨찾기해재/선택->뒤로가기->값이 변경되지않음.)
            case MAP_INFO :
                System.out.println("MAPLIST 정보");
                tr=getSupportFragmentManager().beginTransaction();
                tr.replace(R.id.content_main, new Food_mapFragment(array)).commit(); //변경할게 있을때만 넣어주면됨
                break;
            }
        }
        //super.onActivityResult(requestCode, resultCode, data);
    }

    //1.아이템 선택했을때 발생
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //2.버튼눌렀을때
        switch (item.getItemId()){
            case android.R.id.home : //3.드로우레이아웃 지정하기 --> 아이디 가져오기
                //드로우 레이아웃이 열렸을때 닫고
                if(drawerLayout.isDrawerOpen(drawerMain)){
                    drawerLayout.closeDrawer(drawerMain);
                //드로우 레이아웃이 닫혀있으면 열기
                }else {
                    drawerLayout.openDrawer(drawerMain);
                }
                System.out.println("이곳은메인엑티비=>온옵션아이템셀렉티드");
        }
        return super.onOptionsItemSelected(item);
    }
}