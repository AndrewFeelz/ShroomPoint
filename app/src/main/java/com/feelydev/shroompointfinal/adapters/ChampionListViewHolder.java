package com.feelydev.shroompointfinal.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.feelydev.shroompointfinal.R;
import com.feelydev.shroompointfinal.utils.OnChampionListener;

public class ChampionListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    //Widgets
    TextView name;
    TextView roles;
    ImageView championThumbnail;

    //ClickListener
    OnChampionListener ChampionListener;

    public ChampionListViewHolder(@NonNull View itemView, OnChampionListener onChampionListener) {
        super(itemView);
        ChampionListener = onChampionListener;

        name = itemView.findViewById(R.id.txtChampionName);
        roles = itemView.findViewById(R.id.txtChampRoles);
        championThumbnail = itemView.findViewById(R.id.imgChampionThumbnail);
        //may
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        ChampionListener.onChampionClick(getAdapterPosition());
    }
}
