package com.example.food;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Food_mapFragment extends Fragment  implements OnMapReadyCallback {
    private GoogleMap mMap;
    ArrayList<Food> array;
    ProgressBar progressBar;

    //맵어텝터에서 받아옴(4번)
    MapAdapter adapter;
    RecyclerView list;
    //StaggeredGridLayoutManager layoutManager;

    public Food_mapFragment(ArrayList<Food> array) {
        this.array = array;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view= inflater.inflate(R.layout.fragment_food_map, container, false);
        progressBar=view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        //어댑터 연결과정
        list=view.findViewById(R.id.list);
        final Button listType=view.findViewById(R.id.list_type);
        listType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.getVisibility()==View.VISIBLE){
                    list.setVisibility(view.GONE);
                    listType.setText("목록보기");
                }else {
                    list.setVisibility(view.VISIBLE);
                    listType.setText("목록닫기");
                }
            }
        });

        return view;
    }

    //크리에이트 뷰 한다음에 지도생성하기 (온맵레디)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    //지도그려주는곳()
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        UiSettings settings=mMap.getUiSettings();
        settings.setZoomControlsEnabled(true);

        LocationManager locationManager = (LocationManager)getContext().getSystemService(Context.LOCATION_SERVICE);
        String finePermission = Manifest.permission.ACCESS_FINE_LOCATION;
        if (ActivityCompat.checkSelfPermission(getContext(), finePermission) != PackageManager.PERMISSION_GRANTED)
            return;

        //지도를 새로 검색하여 현위치를 변경하는 시간
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100000, 1, gpsLocationListener);

        //처음 지도 불러올때 시간
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 1, gpsLocationListener);
    }

    //GPS 현재 위치로 이동
    LocationListener gpsLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            String provider = location.getProvider();
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            double altitude = location.getAltitude();
            LatLng latLng = new LatLng(latitude, longitude);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
            mMap.addMarker(new MarkerOptions().position(latLng).title("현재위치")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.current_marker)));

            CircleOptions circleOptions=new CircleOptions().center(latLng)
                    .radius(500) //(반경500m)
                    .fillColor(0x440000FF)
                    .strokeColor(0x110000FF)
                    .strokeWidth(4);
            mMap.addCircle(circleOptions);

            //근방에 있는 음식점 위치 표시하기 (반경500m)
            ArrayList<Food> arrayMarker=new ArrayList<>();
            ArrayList<Float> arrayDistance=new ArrayList<>();
            for(Food food:array) { //어레이에있는 전체데이터를 받아 반복문으로 돌려보기
                float[] result=new float[1]; //결과받기위함
                Location.distanceBetween(latitude,longitude, food.getLatitude(), food.getLongitude(), result); //현위치와 표시된 위치사이에 거리구해주기
                if(result[0] <= 500){
                    arrayMarker.add(food);
                    arrayDistance.add(result[0]);
                    //마커위치구하기
                    LatLng markerLocation=new LatLng(food.getLatitude(),food.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(markerLocation).title(food.getName()));
                }
            }

            //어댑터생성은 거리를 구하 다음에 했음. (먼저 아이디 구해놓기 ==>크리에이트뷰)
            adapter=new MapAdapter(getContext(),array,arrayMarker,arrayDistance); //순서에 맞게 넣기
            list.setAdapter(adapter); //리스트에 넣기
            //버티컬로 쭐 출력하면 되므로 리니어레아웃으로 가져와도됨
            list.setLayoutManager(new LinearLayoutManager(getContext()));

            progressBar.setVisibility(View.GONE);
        }
    };
}