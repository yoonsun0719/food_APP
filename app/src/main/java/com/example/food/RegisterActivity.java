package com.example.food;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    ViewPager fragment;
    TabLayout tab;
    PagerAdapter adapter;
    ArrayList<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setTitle("맛집등록");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //프래그먼트에 해당하는 각페이지 넣기
        fragments.add(new Register_locationFragment());
        fragments.add(new Register_inputFragment());
        fragments.add(new Register_imageFragment());

        //1.전역변수 만든 후, 아이디주기
        fragment=findViewById(R.id.fragment);
        tab=findViewById(R.id.tab);

        //2.탭생성하기
        tab.addTab(tab.newTab().setText("STEP1."));
        tab.addTab(tab.newTab().setText("STEP2."));
        tab.addTab(tab.newTab().setText("STEP3."));
        //아이콘넣어주기
        tab.getTabAt(0).setIcon(R.drawable.ic_location); //위치
        tab.getTabAt(1).setIcon(R.drawable.ic_call); //정보
        tab.getTabAt(2).setIcon(R.drawable.ic_baseline_linked_camera_24); //사진

        fragment=findViewById(R.id.fragment);
        adapter=new PagerAdapter(getSupportFragmentManager(), fragments);
        fragment.setAdapter(adapter);

        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fragment.setCurrentItem(tab.getPosition()); //탭에 겟 포지션번째 프래그먼트로 바꾼다.
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //FragmentTransaction tr=getSupportFragmentManager().beginTransaction();
        //레지스터로케이션프래그먼트가 fragment로들어감
        //tr.add(R.id.fragment, new Register_locationFragment(new Food())).commit();

    }

    //pagerAdapter생성(탭을 누를 때 마다 페이지내용이 바껴야 하므로 페이지 내용에 프래그먼트를 넣어줘야 함.
    //여러개의 내용을 변경할 경우 어댑터가 필요

    //페이져어댑터
    private class PagerAdapter extends FragmentPagerAdapter{
        ArrayList<Fragment> fragments;

        public PagerAdapter(@NonNull FragmentManager fm, ArrayList<Fragment> fragments) {
            super(fm);
            this.fragments =fragments;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    //프래그먼트 교체메서드    // replaceFragment -->다음버튼(id=next)을 눌렀을때
    public void replaceFragment(Fragment fragment){
        FragmentTransaction tr=getSupportFragmentManager().beginTransaction();
        tr.replace(R.id.fragment,fragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            case R.id.submit :
                AlertDialog.Builder box=new AlertDialog.Builder(this);
                box.setTitle("질의");
                box.setMessage("저장하시겠습니까?");
                //인서트작업시 요기서 해주기
                box.setPositiveButton("예",null);
                box.setNegativeButton("아니오",null);
                box.show();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_submit,menu);
        return super.onCreateOptionsMenu(menu);
    }
}