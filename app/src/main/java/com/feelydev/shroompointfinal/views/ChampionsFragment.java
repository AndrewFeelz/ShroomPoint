package com.feelydev.shroompointfinal.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.feelydev.shroompointfinal.ChampionVerboseActivity;
import com.feelydev.shroompointfinal.R;
import com.feelydev.shroompointfinal.adapters.ChampionListRecyclerView;
import com.feelydev.shroompointfinal.databinding.FragmentChampionsBinding;
import com.feelydev.shroompointfinal.adapters.ChampionsViewModel;
import com.feelydev.shroompointfinal.models.ChampionSimple;
import com.feelydev.shroompointfinal.utils.OnChampionListener;
import com.feelydev.shroompointfinal.utils.ScreenUtility;

import java.util.List;

public class ChampionsFragment extends Fragment implements OnChampionListener {

    //RecyclerView
    private RecyclerView recyclerView;
    private ChampionListRecyclerView championListRecyclerView;

    //ViewModel
    private ChampionsViewModel championsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_champions, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        championsViewModel = new ViewModelProvider(this).get(ChampionsViewModel.class);
        ConfigureRecyclerView();
        getChampionListAPI();
        ObserveChangesToList();
        return view;
    }

    //Call from VIEWMODELS
    private void getChampionListAPI() {
        championsViewModel.getChampionListAPI();
    }

    private void ObserveChangesToList(){
        championsViewModel.getChampionList().observe(getViewLifecycleOwner(), new Observer<List<ChampionSimple>>() {
            @Override
            public void onChanged(List<ChampionSimple> championSimples) {
                if (championSimples != null) {
                    championListRecyclerView.setChampionSimpleList(championSimples);
                }

            };
        });
    }

    private void ConfigureRecyclerView() {
        championListRecyclerView = new ChampionListRecyclerView(this);
        recyclerView.setAdapter(championListRecyclerView);

        int numberOfColumns = ScreenUtility.calculateNumberOfColumns(getContext(), 140);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onChampionClick(int position) {
        Toast.makeText(getContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
        String champID = championListRecyclerView.getSelectedChampionId(position);
        Intent intent = new Intent(getContext(), ChampionVerboseActivity.class);
        intent.putExtra("champID", champID);
        startActivity(intent);


    }
}