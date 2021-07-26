package com.example.food;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register_inputFragment extends Fragment {
    Food vo;
    TextView txtcount;
    /*public Register_inputFragment(Food vo) {
        this.vo = vo;
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_register_input, container, false);

        TextView txtAddress=view.findViewById(R.id.address);
       // txtAddress.setText(vo.getAddress());

        //이전버튼만들기
        /*Button prev=view.findViewById(R.id.prev);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RegisterActivity)getContext()).replaceFragment(new Register_locationFragment(vo));
            }
        });*/

        //글자수 변경될때, 글자수 변경하기 (n글자 이상 글을 쓸때 Toast 띄우기)
        txtcount =view.findViewById(R.id.txtcount);
        final EditText description=view.findViewById(R.id.description);
        description.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                int length=description.getText().toString().length();
                txtcount.setText(String.valueOf(length));

                if(length > 10)
                    Toast.makeText(getContext(),"10글자를 넘을 수없습니다.",Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        return view;
    }
}