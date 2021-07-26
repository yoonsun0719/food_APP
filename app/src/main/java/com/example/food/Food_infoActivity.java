package com.example.food;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class Food_infoActivity extends AppCompatActivity implements OnMapReadyCallback {
    ArrayList<Food> array;
    int seq;
    Food vo;

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_info);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getSupportActionBar().setTitle("맛집정보");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent=getIntent(); //인텐트가져오기
        seq =intent.getIntExtra("seq",0); //seq 값이 없으면 0

        //★★Serializable 사용하기위해 VO에서 implements해주어야함★★

        //array=intent.getSerializableExtra("array"); //오브젝트일때는 시리얼라이저엑스트라로 가져옴 => 오류나는 이유 (형변환해주어야함)
        array=(ArrayList<Food>)intent.getSerializableExtra("array"); //형변환 완료


        vo=array.get(seq);
        ImageView image=findViewById(R.id.image);
        image.setImageResource(vo.getImage());

        final ImageView keep =findViewById(R.id.keep);
        if(vo.getKeep()==1){
            keep.setImageResource(R.drawable.ic_star1);
        }else {
            keep.setImageResource(R.drawable.ic_star0);
        }
        //keep버튼을 눌렀을때
        keep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vo.getKeep()==1) {
                    android.app.AlertDialog.Builder box = new android.app.AlertDialog.Builder(Food_infoActivity.this);
                    box.setTitle("즐겨찾기에 삭제");
                    box.setMessage("즐겨찾기에서 삭제하시겠습니까?");
                    box.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            keep.setImageResource(R.drawable.ic_star0);
                            vo.setKeep(0);
                        }
                    });
                    box.setNegativeButton("아니오", null);
                    box.show();
                }else{
                    android.app.AlertDialog.Builder box = new AlertDialog.Builder(Food_infoActivity.this);
                    box.setTitle("즐겨찾기에 추가");
                    box.setMessage("즐겨찾기에 추가하시겠습니까?");
                    box.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            keep.setImageResource(R.drawable.ic_star1);
                            vo.setKeep(1);
                        }
                    });
                    box.setNegativeButton("아니오", null);
                    box.show();
                }
            }
        });


        //갑가져와 넣어주기
        TextView name=findViewById(R.id.name);
        name.setText(vo.getName());

        TextView tel=findViewById(R.id.tel);
        tel.setText(vo.getTel());

        //전화걸수 있도록 하기
        tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //전화번호 형태를 URI 형태로 바꿔서 넣기
                Uri phone= Uri.parse("tel:"+vo.getTel());
                //다이얼 패드먼저 보이게하기
                Intent telIntent=new Intent(Intent.ACTION_DIAL, phone); //전화를직접걸고싶으면 ACTION_CALL로 변경
                startActivity(telIntent);
            }
        });


        TextView description=findViewById(R.id.description);
        description.setText(vo.getDescription());

        TextView address=findViewById(R.id.address);
        address.setText(vo.getAddress());

        //위치보기버튼 클릭했을때
        TextView location=findViewById(R.id.location);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng latLng=new LatLng(vo.getLatitude(),vo.getLongitude());
                mMap.addMarker(new MarkerOptions().position(latLng).title(vo.getName()));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,16));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case android.R.id.home :
                //뒤로가기버튼 눌렀을 때 즐겨찾기상태가 변경되면 array값을 가지고 가야한다.(이동할땐 인텐드에 담아서 가야한다.)
                Intent intent=new Intent();
                intent.putExtra("array",array);
                setResult(RESULT_OK,intent);
                finish(); //이후 메인엑티비티로 이동함
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    //실제맵을 이때 그려줌
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        //지도 확대 축소버튼있는곳
        UiSettings settings=mMap.getUiSettings();
        settings.setZoomControlsEnabled(true);

        LatLng sydney = new LatLng(vo.getLatitude(), vo.getLongitude());
        mMap.addMarker(new MarkerOptions().position(sydney).title(vo.getName()+" / "+vo.getTel()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,16));
    }
}