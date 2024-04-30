package com.eliftekin.inviochallenge.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eliftekin.inviochallenge.R;
import com.eliftekin.inviochallenge.database.FavoritesEntity;
import com.eliftekin.inviochallenge.databinding.FragmentFavoritesBinding;
import com.eliftekin.inviochallenge.ui.adapters.FavoritesRvAdapter;
import com.eliftekin.inviochallenge.ui.viewmodels.FavoritesViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {

    private FragmentFavoritesBinding binding;

    RecyclerView recyclerView;
    FavoritesRvAdapter rvAdapter;
    List<FavoritesEntity> favoritesList = new ArrayList<>();

    FavoritesViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        initRV(favoritesList);
        setViewModel();

        binding.backBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_favoritesFragment2_to_homeFragment2);
            }
        });

        return view;
    }

    private void setViewModel() {
        viewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);
        viewModel.getFavoritesList().observe(this, new Observer<List<FavoritesEntity>>() {
            @Override
            public void onChanged(List<FavoritesEntity> favorites) {
                if(favoritesList.isEmpty()){
                    favoritesList = favorites;
                    initRV(favoritesList);
                }
                else {
                    favoritesList.addAll(favorites);
                    rvAdapter.notifyDataSetChanged();
                }
            }
        });

        viewModel.getIsFavoritesEmpty().observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean)
                    binding.messageTV.setVisibility(View.VISIBLE);
                else
                    binding.messageTV.setVisibility(View.GONE);
            }
        });
    }

    private void initRV(List<FavoritesEntity> favoritesList) {
        recyclerView = binding.favoriteListRv;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        rvAdapter = new FavoritesRvAdapter(favoritesList, getContext());
        recyclerView.setAdapter(rvAdapter);
    }
}