package com.example.food;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class KeepAdapter extends RecyclerView.Adapter<KeepAdapter.ViewHolder> {
    private final static int KEEP_INFO = 400;
    ArrayList<Food> array; //전체데이터가 들어가있음.
    ArrayList<Food> arrayKeep; //즐겨찾기한 데이터가 들어가있음.
    Context context;

    public KeepAdapter(ArrayList<Food> array, ArrayList<Food> arrayKeep, Context context) {
        this.array = array;
        this.arrayKeep = arrayKeep;
        this.context = context;
    }

    @NonNull
    @Override
    public KeepAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_food,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KeepAdapter.ViewHolder holder, int position) {
        final Food vo=arrayKeep.get(position);
        holder.image.setImageResource(vo.getImage());
        holder.name.setText(vo.getName());
        holder.description.setText(vo.getDescription());
        holder.keep.setImageResource(R.drawable.ic_star1); //즐겨찾기 추가된것만 나오므로 즐착 됐을때와 안됐을때 if로 하지 않아도됨
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Food_infoActivity.class);
                intent.putExtra("seq",vo.getSeq());
                intent.putExtra("array",array);
                ((Activity)context).startActivityForResult(intent,KEEP_INFO);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayKeep.size();
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
