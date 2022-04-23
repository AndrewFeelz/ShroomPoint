package com.feelydev.shroompointfinal.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.feelydev.shroompointfinal.R;
import com.feelydev.shroompointfinal.models.ChampionSimple;
import com.feelydev.shroompointfinal.utils.Credentials;
import com.feelydev.shroompointfinal.utils.OnChampionListener;

import java.util.List;

public class ChampionListRecyclerView extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ChampionSimple> championSimpleList;
    private OnChampionListener onChampionListener;

    public ChampionListRecyclerView(OnChampionListener onChampionListener) {
        this.onChampionListener = onChampionListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.champion_list_item, parent, false);
        return new ChampionListViewHolder(view, onChampionListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        ((ChampionListViewHolder)holder).name.setText(championSimpleList.get(i).getName());

        //Use Glide for image
        Glide.with(holder.itemView.getContext())
                .load(Credentials.BASE_URL + "champion-icons/" + championSimpleList.get(i).getId() + ".png")
                .into((((ChampionListViewHolder)holder).championThumbnail));
    }

    @Override
    public int getItemCount() {
        if(championSimpleList != null){
            return championSimpleList.size();
        } else { return 0; }
    }

    public void setChampionSimpleList(List<ChampionSimple> championSimples){
        this.championSimpleList = championSimples;
        notifyDataSetChanged();
    }

    //Get ID of Champ
    public String getSelectedChampionId(int position){
        if (championSimpleList != null){
            if (championSimpleList.size() > 0){
                ChampionSimple championSimple = championSimpleList.get(position);
                return String.valueOf(championSimple.getId());
            }
        }
        return null;
    }
}
