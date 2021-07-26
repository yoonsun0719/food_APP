package com.example.food;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    private final static int LIST_INFO = 300; //리스트에서 인포로 이동하기

    Context context;
    ArrayList<Food> array;

    //생성자
    public FoodAdapter(Context context, ArrayList<Food> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_food,parent,false);
        return new ViewHolder(view); //리턴할떄 뷰홀더를 생성해야하기때문에 뷰홀더(뷰)로 리턴해준다.
    }

    @Override
    public void onBindViewHolder(@NonNull final FoodAdapter.ViewHolder holder, int position) {
        final Food vo=array.get(position);
        Bitmap photo= BitmapFactory.decodeFile(vo.getPhoto());
        if(photo != null) holder.image.setImageBitmap(photo);


        //holder.image.setImageResource(vo.getImage());
        holder.name.setText(vo.getName());
        holder.description.setText(vo.getDescription());

        //홀더에있는 아이템뷰를 클릭했을때  => 푸드인포로 이동한다.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context, Food_infoActivity.class);
                intent.putExtra("seq", vo.getSeq());
                intent.putExtra("array",array);
                ((Activity)context).startActivityForResult(intent,LIST_INFO); //리스트에서 인포로가기
            }
        });


        if(vo.getKeep()==1){ //즐겨찾기추가한경우 1 , 아니면 0
            holder.keep.setImageResource(R.drawable.ic_star1);
        }else {
            holder.keep.setImageResource(R.drawable.ic_star0);
        }

        //즐겨찾기 버튼 클릭했을때
        holder.keep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vo.getKeep()==1) { //즐겨찾기 추가된경우
                    AlertDialog.Builder box=new AlertDialog.Builder(context);
                    box.setTitle("즐겨찾기취소");
                    box.setMessage("즐겨찾기 취소하실건가요?");
                    box.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            holder.keep.setImageResource(R.drawable.ic_star0);
                            vo.setKeep(0);
                        }
                    });
                    box.setNegativeButton("아니오",null);
                    box.show();
                }else {
                    AlertDialog.Builder box=new AlertDialog.Builder(context);
                    box.setTitle("즐겨찾기추가");
                    box.setMessage("즐겨찾기 추가하실건가요?");
                    box.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            holder.keep.setImageResource(R.drawable.ic_star1);
                            vo.setKeep(1);
                        }
                    });
                    box.setNegativeButton("아니오",null);
                    box.show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image, keep;
        TextView name, description;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            keep=itemView.findViewById(R.id.keep);
            name=itemView.findViewById(R.id.name);
            description=itemView.findViewById(R.id.description);
        }
    }
}
