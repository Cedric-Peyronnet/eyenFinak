package com.example.maxi.eyen;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> {

    ArrayList<event> liste;

      public MyAdapter(ArrayList<event> liste) {
        this.liste = liste;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
          /** linkage avec le layout **/
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_layout, parent , false);
        MyHolder holder = new MyHolder(v, this);

        return holder;
    }

    @Override
    /**Binding sur le layout**/
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.tvNom.setText(this.liste.get(position).getLocation());
        holder.tvAddress.setText(this.liste.get(position).getAddress());
        holder.tvCity.setText(this.liste.get(position).getCity());
        holder.tvNom.setText(this.liste.get(position).getName());

        holder.tvStartDate.setText(this.liste.get(position).getStartDate().toString());
        holder.tvEndDate.setText(this.liste.get(position).getEndDate().toString());
    }

    @Override
    public int getItemCount() {
        return this.liste.size();
    }
}
