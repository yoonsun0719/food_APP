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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

public class Register_locationFragment extends Fragment /*지도구현하기위함*/implements OnMapReadyCallback {
    ProgressBar progressBar;
    private GoogleMap mMap;
    TextView txtAddress;
    Marker moveMarker; //움직이는 마커
    Food vo;
   /* public Register_locationFragment(Food vo) {
        this.vo = vo;
    }*/



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_register_location, container, false);
            //프로그래스바 아이디 가져와 지도가져왔을때 없애기
            progressBar=view.findViewById(R.id.progressBar);
            txtAddress=view.findViewById(R.id.address);



            /*Button next=view.findViewById(R.id.next);
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Food vo=new Food();
                    vo.setAddress(txtAddress.getText().toString());
                    ((RegisterActivity)getContext()).replaceFragment(new Register_inputFragment(vo));
                }
            });*/
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

            //지도 확대 축소버튼있는곳
            UiSettings settings=mMap.getUiSettings();
            settings.setZoomControlsEnabled(true);

            LocationManager locationManager = (LocationManager)getContext().getSystemService(Context.LOCATION_SERVICE);
            String finePermission = Manifest.permission.ACCESS_FINE_LOCATION;
            if (ActivityCompat.checkSelfPermission(getContext(), finePermission) != PackageManager.PERMISSION_GRANTED)
                return;
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 1, gpsLocationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 1, gpsLocationListener);

            //맵을 클릭했을때
            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(@NonNull LatLng latLng) {
                    //위도경도값
                    double latitude=latLng.latitude;
                    double longitude=latLng.longitude;

                    //마커옵션만들기
                    MarkerOptions marker=new MarkerOptions();
                    //마커위치(위도경도값)
                    marker.position(latLng);
                    //마커드러블되도록
                    marker.draggable(true);
                    //마커이미지(아이콘)
                    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

                    //마커가 추가될때 (마커자리에 마커값이 있으면 이전것을 지우고 새로 add하도록 함)
                    if(moveMarker != null) moveMarker.remove();
                    moveMarker = mMap.addMarker(marker);
                    txtAddress.setText(getAddress(getContext(),latitude,longitude));
                }
            });
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
                txtAddress.setText(getAddress(getContext(), latitude, longitude));
                progressBar.setVisibility(View.GONE);
            }
        };

        //위도 경도 값으로 주소 가져오기
        public static String getAddress(Context context, double latitude, double longitude) {
            String strAddress ="현재 위치를 확인 할 수 없습니다.";
            System.out.println("!!!!!!!!!!!!!!"+context);
            Geocoder geocoder = new Geocoder(context, Locale.KOREA);
            List<Address> address;
            try {
                address = geocoder.getFromLocation(latitude, longitude, 1);
                strAddress = address.get(0).getAddressLine(0).toString();
            } catch (Exception e) {
                System.out.println("!!!!위경도주소값 확인!!!!!...."+e.toString());
            }
            return strAddress;
        }

}