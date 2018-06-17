package com.example.maxi.eyen;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


public class MyHolder extends RecyclerView.ViewHolder {
        TextView tvAddress;
        TextView tvLocation;
        TextView tvNom;
        TextView tvCity;
        TextView tvStartDate;
        TextView tvEndDate;

        /**Holder pour l'affichage sur notre item_layout**/
        public MyHolder(View itemView, final MyAdapter adapter) {
            super(itemView);
            tvNom = (TextView) itemView.findViewById(R.id.textViewNom);
            tvAddress = (TextView) itemView.findViewById(R.id.textViewAdress);
            tvCity = (TextView) itemView.findViewById(R.id.textViewCity);
            tvLocation = (TextView) itemView.findViewById(R.id.textViewLocation);
            tvStartDate = (TextView) itemView.findViewById(R.id.textViewStartDate);
            tvEndDate = (TextView) itemView.findViewById(R.id.textViewEndDate);
        }
}
