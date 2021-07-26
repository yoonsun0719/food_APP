package com.example.food;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;


public class Food_listFragment extends Fragment {
    RecyclerView list;
    FoodAdapter adapter;
    StaggeredGridLayoutManager layoutManager;
    ArrayList<Food> array;

    int listType= 1; //한개씩보이게하기

    //생성할때마다 어레이값을 가지고 다녀야하기때문에 array만 생성자를 만들어줌
    public Food_listFragment(ArrayList<Food> array) {
        this.array = array;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_food_list, container, false);
        list=view.findViewById(R.id.list_food);

        //푸드 어댑터만들기
        adapter=new FoodAdapter(getContext(),array); //콘텍스트에서 어레이를 넘겨주겠다.
        layoutManager=new StaggeredGridLayoutManager(listType,StaggeredGridLayoutManager.VERTICAL);
        list.setAdapter(adapter);
        list.setLayoutManager(layoutManager);
        //바인딩 전이므로 똑같은 데이터 세개가 나오게됨


        //이미지 클릭했을때, 두개 or 하나로 정렬하기
        final ImageView imgType=view.findViewById(R.id.list_type);
        imgType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listType==1) {
                    listType=2;
                    imgType.setImageResource(R.drawable.ic_list2);
                }else {
                    listType=1;
                    imgType.setImageResource(R.drawable.ic_list1);
                }
                layoutManager=new StaggeredGridLayoutManager(listType, StaggeredGridLayoutManager.VERTICAL);
                list.setLayoutManager(layoutManager);
            }
        });

        return view;
    }
}