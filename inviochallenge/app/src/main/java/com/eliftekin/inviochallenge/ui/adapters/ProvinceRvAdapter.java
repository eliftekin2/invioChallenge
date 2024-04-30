package com.eliftekin.inviochallenge.ui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eliftekin.inviochallenge.R;
import com.eliftekin.inviochallenge.databinding.ListItemProvinceBinding;
import com.eliftekin.inviochallenge.models.DataList;

import java.util.ArrayList;

public class ProvinceRvAdapter extends RecyclerView.Adapter<ProvinceRvAdapter.ViewHolder> {

    Context context;
    private ArrayList<DataList> dataListArrayList;

    UniversityRvAdapter universityRvAdapter;

    public ProvinceRvAdapter(Context context, ArrayList<DataList> dataListArrayList) {
        this.context = context;
        this.dataListArrayList = dataListArrayList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemProvinceBinding binding = ListItemProvinceBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataList dataList = dataListArrayList.get(position);
        holder.binding.provinceName.setText(dataList.getProvince());

        checkIfEmpty(dataList, holder);
        checkVisibilityStatus(dataList, holder);

        //nested rv
        universityRvAdapter = new UniversityRvAdapter(context, dataList.getUniversitiesList());
        holder.binding.universityListRv.setAdapter(universityRvAdapter);

        holder.binding.expandProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setVisibility(dataList, holder);

            }
        });

    }

    private void setVisibility(DataList itemState, ViewHolder holder){
        if(!itemState.isExpanded()){
            holder.binding.universityListRv.setVisibility(View.VISIBLE);
            itemState.setExpanded(true);
            holder.binding.provinceCardview.setCardBackgroundColor(Color.parseColor("#A5C9CA"));
            holder.binding.expandProvince.setImageResource(R.drawable.collapse);
        }
        else {
            holder.binding.universityListRv.setVisibility(View.GONE);
            itemState.setExpanded(false);
            holder.binding.provinceCardview.setCardBackgroundColor(Color.parseColor("#EEEEEE"));
            holder.binding.expandProvince.setImageResource(R.drawable.expand);
        }
        notifyItemChanged(holder.getAdapterPosition());
    }

    private void checkVisibilityStatus(DataList itemState, ViewHolder holder) {
        if (itemState.isExpanded()){
            holder.binding.universityListRv.setVisibility(View.VISIBLE);
            holder.binding.provinceCardview.setCardBackgroundColor(Color.parseColor("#A5C9CA"));
            holder.binding.expandProvince.setImageResource(R.drawable.collapse);
        }
        else {
            holder.binding.universityListRv.setVisibility(View.GONE);
            holder.binding.provinceCardview.setCardBackgroundColor(Color.parseColor("#EEEEEE"));
            holder.binding.expandProvince.setImageResource(R.drawable.expand);
        }
    }

    private void checkIfEmpty(DataList dataList, ViewHolder holder) {
        if(dataList.isEmpty())
            holder.binding.expandProvince.setVisibility(View.GONE);
        else
            holder.binding.expandProvince.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return dataListArrayList.size();
    }

    public void collapseAll() {
        universityRvAdapter.collapseAll();

        for (DataList dataList : dataListArrayList) {
            dataList.setExpanded(false);
        }

        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ListItemProvinceBinding binding;

        public ViewHolder(ListItemProvinceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

}
