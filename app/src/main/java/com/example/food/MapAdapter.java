package com.example.food;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class MapAdapter extends RecyclerView.Adapter<MapAdapter.ViewHolder> {
    //1.
    Context context;
    ArrayList<Food> array; //전체데이터
    ArrayList<Food> arrayMap; //인근맛집
    ArrayList<Float> arrayDistance; //맛집과의 거리

    private final static int MAP_INFO=500;

    //2.
    public MapAdapter(Context context, ArrayList<Food> array, ArrayList<Food> arrayMap, ArrayList<Float> arrayDistance) {
        this.context = context;
        this.array = array;
        this.arrayMap = arrayMap;
        this.arrayDistance = arrayDistance;
    }

    @NonNull
    @Override
    public MapAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //3.  //4.맵프래그먼트에 저장
        View view= LayoutInflater.from(context).inflate(R.layout.item_map,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MapAdapter.ViewHolder holder, int position) {
        //바인더하기전에 어레이맵에있는 데이터가져오기
        final Food vo=arrayMap.get(position);
        holder.image.setImageResource(vo.getImage());

        //디스턴스값= 플로엇
        float distance=arrayDistance.get(position)/1000; //거리가 미터로 나와서 키로미터로 보기위해 1000으로 나눠주었음.
        //디스크립션 소숫점이 많이 나올것 같으므로 포맷을 줌
        DecimalFormat df=new DecimalFormat("#,###.00km"); //샵은 생략됨 0.51로 하고싶을떄는 "#,##0.00" 으로표기!!
        String strDistance=df.format(distance); //어레이디스턴스에 포지션째 값을 가져온다.
        holder.distance.setText(strDistance);

        holder.name.setText(vo.getName());
        holder.description.setText(vo.getDescription());

        //홀더에 아이템뷰를 받아서 클릭했을때 //맛집정보엑티비티로 이동하기
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //이동하는곳
                Intent intent=new Intent(context, Food_infoActivity.class);
                //넣어줘야하는것
                intent.putExtra("seq",vo.getSeq());
                intent.putExtra("array",array);
                //프래그먼트이므로 엑티비티로 형변환
                ((Activity)context).startActivityForResult(intent, MAP_INFO);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayMap.size(); //실제리턴값 ==> 맵에 출력해줄 사이즈만큼
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //출력해야하는것
        ImageView image;
        TextView name, description, distance;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            name=itemView.findViewById(R.id.name);
            description=itemView.findViewById(R.id.description);
            distance=itemView.findViewById(R.id.distance);
        }
    }
}
