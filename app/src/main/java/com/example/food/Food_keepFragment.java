package com.example.food;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class Food_keepFragment extends Fragment {
    ArrayList<Food> array;
    KeepAdapter adapter;
    StaggeredGridLayoutManager layoutManager;
    RecyclerView list;

    public Food_keepFragment(ArrayList<Food> array) {
        this.array = array;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_food_keep, container, false);

        ArrayList<Food> arrayKeep=new ArrayList<>();
        for (Food food:array) {
            if(food.getKeep()==1) arrayKeep.add(food);
        }

        list=view.findViewById(R.id.list);
        layoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        adapter=new KeepAdapter(array,arrayKeep,getContext());

        list.setAdapter(adapter);
        list.setLayoutManager(layoutManager);
        return view;
    }
}