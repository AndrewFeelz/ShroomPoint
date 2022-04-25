package com.feelydev.shroompointfinal.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.feelydev.shroompointfinal.R;
import com.feelydev.shroompointfinal.adapters.ChampionListRecyclerView;
import com.feelydev.shroompointfinal.adapters.ChampionsViewModel;
import com.feelydev.shroompointfinal.models.ChampionSimple;
import com.feelydev.shroompointfinal.utils.OnChampionListener;
import com.feelydev.shroompointfinal.utils.ScreenUtility;

import java.util.ArrayList;
import java.util.List;

public class ChampionsFragment extends Fragment implements OnChampionListener {

    //RecyclerView
    private RecyclerView recyclerView;
    private ChampionListRecyclerView championListRecyclerView;
    //ViewModel
    private ChampionsViewModel championsViewModel;
    //Activity wide championList
    private List<ChampionSimple> championSimpleList;
    //Sort Widgets
    private ImageButton btnAll, btnAssassin, btnFighter, btnMage, btnMarksmen, btnSupport, btnTank;
    private TextView txtCurrentSort;
    private String currentSort;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_champions, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        championsViewModel = new ViewModelProvider(this).get(ChampionsViewModel.class);
        ConfigureRecyclerView();
        getChampionListAPI();
        ObserveChangesToList();

        btnAll = view.findViewById(R.id.btnSortAll);
        btnAssassin = view.findViewById(R.id.btnSortAssassins);
        btnFighter = view.findViewById(R.id.btnSortFighters);
        btnMage = view.findViewById(R.id.btnSortMages);
        btnMarksmen = view.findViewById(R.id.btnSortMarksmen);
        btnSupport = view.findViewById(R.id.btnSortSupport);
        btnTank = view.findViewById(R.id.btnSortTank);
        txtCurrentSort = view.findViewById(R.id.txtSortType);
        setSortListeners();





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
                    championSimpleList = championSimples;
                }

            };
        });
    }

    private void filter(String text) {
        // creating a new array list to filter our data.
        List<ChampionSimple> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (ChampionSimple item : championSimpleList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getRoles().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(getContext(), "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            championListRecyclerView.setChampionSimpleList(filteredlist);
        }
    }

    private void setSortListeners() {
        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter("");
                currentSort = "All";
                txtCurrentSort.setText(currentSort);
            }
        });
        btnAssassin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter("assassin");
                currentSort = "Assassins";
                txtCurrentSort.setText(currentSort);
            }
        });
        btnFighter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter("fighter");
                currentSort = "Fighters";
                txtCurrentSort.setText(currentSort);
            }
        });
        btnMage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter("mage");
                currentSort = "Mages";
                txtCurrentSort.setText(currentSort);
            }
        });
        btnMarksmen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter("marksman");
                currentSort = "Marksmen";
                txtCurrentSort.setText(currentSort);
            }
        });
        btnSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter("support");
                currentSort = "Supports";
                txtCurrentSort.setText(currentSort);
            }
        });
        btnTank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter("tank");
                currentSort = "Tanks";
                txtCurrentSort.setText(currentSort);
            }
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
        String champID = championListRecyclerView.getSelectedChampionId(position);
        Intent intent = new Intent(getContext(), ChampionVerboseActivity.class);
        intent.putExtra("champID", champID);
        startActivity(intent);


    }
}