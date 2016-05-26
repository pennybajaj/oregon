package com.quadship.skoolin;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class JsonDataAdapter extends RecyclerView.Adapter<JsonDataAdapter.ViewHolder> {
   // private ArrayList<AndroidVersion> android_versions;
   ArrayList<UserData> userArray;
    private Context context;

    public JsonDataAdapter(Context context, ArrayList<UserData> userArray) {
        this.context = context;
        this.userArray = userArray;

    }



    @Override
    public JsonDataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.tv_android.setText(userArray.get(i).getUserBbmPin());
        Picasso.with(context).load(userArray.get(i).getUserImg()).resize(160, 160).into(viewHolder.img_android);
    }

    @Override
    public int getItemCount() {
        return userArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_android;
        ImageView img_android;
        public ViewHolder(View view) {
            super(view);

            tv_android = (TextView)view.findViewById(R.id.tv_android);
            img_android = (ImageView)view.findViewById(R.id.img_android);
        }
    }
}
