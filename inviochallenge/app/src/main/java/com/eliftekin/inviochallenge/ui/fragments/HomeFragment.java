package com.eliftekin.inviochallenge.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eliftekin.inviochallenge.R;
import com.eliftekin.inviochallenge.databinding.FragmentHomeBinding;
import com.eliftekin.inviochallenge.models.DataList;
import com.eliftekin.inviochallenge.models.PageInfo;
import com.eliftekin.inviochallenge.ui.adapters.ProvinceRvAdapter;
import com.eliftekin.inviochallenge.ui.viewmodels.HomeViewModel;
import com.eliftekin.inviochallenge.utils.Singleton;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    PageInfo pageInfo;

    RecyclerView recyclerView;
    ProvinceRvAdapter rvAdapter;
    ArrayList<DataList> dataList = new ArrayList<>();

    HomeViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        pageInfo = Singleton.getInstance().getPageInfo();
        setViewModel(pageInfo);
        initRV(dataList);
        setRvScroll();

        binding.favoritesPageIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_homeFragment2_to_favoritesFragment2);
            }
        });

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collapseAll();
            }
        });

        return view;
    }

    private void collapseAll() {
        if(rvAdapter != null)
            rvAdapter.collapseAll();
    }

    private void setRvScroll() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (manager != null && manager.findLastVisibleItemPosition() == rvAdapter.getItemCount()-1){
                    viewModel.fetchNextPage();
                }
            }
        });
    }

    private void setViewModel(PageInfo pageInfo) {
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        if (viewModel.getPageInfoMutable().getValue() == null)
            viewModel.setPageInfoMutable(pageInfo);

        viewModel.getPageInfoMutable().observe(getViewLifecycleOwner(), new Observer<PageInfo>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(PageInfo pageInfo) {
                if (dataList.isEmpty()){
                    dataList = pageInfo.getDataList();
                    initRV(dataList);
                }
                else{
                    dataList.addAll(pageInfo.getDataList());
                    rvAdapter.notifyDataSetChanged();
                }
            }
        });

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initRV(ArrayList<DataList> dataList) {
        recyclerView = binding.provinceListRv;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        rvAdapter = new ProvinceRvAdapter(getContext(), dataList);
        recyclerView.setAdapter(rvAdapter);
    }
}