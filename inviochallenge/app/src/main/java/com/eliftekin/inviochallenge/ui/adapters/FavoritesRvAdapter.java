package com.eliftekin.inviochallenge.ui.adapters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.eliftekin.inviochallenge.R;
import com.eliftekin.inviochallenge.database.FavoritesEntity;
import com.eliftekin.inviochallenge.databinding.ListItemFavoritesBinding;
import com.eliftekin.inviochallenge.repo.FavoritesRepository;
import com.eliftekin.inviochallenge.ui.fragments.UniWebFragment;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoritesRvAdapter extends RecyclerView.Adapter<FavoritesRvAdapter.ViewHolder> {

    List<FavoritesEntity> favoritesList;
    Context context;

    FavoritesRepository favoritesRepository;

    ExecutorService executor;
    Handler handler;

    public FavoritesRvAdapter(List<FavoritesEntity> favoritesList, Context context) {
        this.favoritesList = favoritesList;
        this.context = context;

        favoritesRepository = new FavoritesRepository(context);

        executor = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemFavoritesBinding binding = ListItemFavoritesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FavoritesEntity favorites = favoritesList.get(position);

        holder.binding.universityName.setText(favorites.getName());
        holder.binding.phone.setText(favorites.getPhone());
        holder.binding.fax.setText(favorites.getFax());
        holder.binding.website.setText(favorites.getWebsite());
        holder.binding.email.setText(favorites.getEmail());
        holder.binding.adress.setText(favorites.getAdress());
        holder.binding.rector.setText(favorites.getRector());

        checkIsEmpty(favorites, holder);
        checkVisibilityStatus(favorites, holder);

        holder.binding.expandUni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setVisibilityStatus(favorites, holder);
            }
        });

        holder.binding.addToFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RemoveFromDB(favorites, holder);
            }
        });

        holder.binding.website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToWebsite(holder);
            }
        });

        holder.binding.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callTheUni(holder);
            }
        });

    }

    private void callTheUni(ViewHolder holder) {
        String phoneNumber = holder.binding.phone.getText().toString();

        if(!phoneNumber.equals("-")){
            Intent call = new Intent(Intent.ACTION_CALL);
            call.setData(Uri.parse("tel:"+ phoneNumber));

            if(ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, 1);
                Toast.makeText(context, "Tekrar deneyiniz.", Toast.LENGTH_SHORT).show();
            }
            else
                context.startActivity(call);
        }
    }

    private void goToWebsite(ViewHolder holder) {
        String uniName = holder.binding.universityName.getText().toString();
        String uniUrl = holder.binding.website.getText().toString();

        FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        UniWebFragment fragment = new UniWebFragment();

        Bundle bundle = new Bundle();
        bundle.putString("uniName", uniName);
        bundle.putString("url", uniUrl);
        fragment.setArguments(bundle);

        transaction.addToBackStack(null).replace(R.id.fragmentContainerView, fragment).commit();
    }

    private void RemoveFromDB(FavoritesEntity favorites, ViewHolder holder) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                String name = holder.binding.universityName.getText().toString();
                List<FavoritesEntity> list = favoritesRepository.getFavorites();

                boolean isRemoved = false;

                for (FavoritesEntity favorites : list){
                    if (favorites.getName().equals(name)){
                        favoritesRepository.delete(name);
                        isRemoved = true;
                        break;
                    }
                }

                boolean finalIsRemoved = isRemoved;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(finalIsRemoved){
                            favoritesList.remove(holder.getAdapterPosition());
                            notifyItemRemoved(holder.getAdapterPosition());
                        }
                    }
                });

            }
        });
    }

    private void setVisibilityStatus(FavoritesEntity favorites, ViewHolder holder) {
        if(!favorites.getExpanded()){
            holder.binding.universityInfoLayout.setVisibility(View.VISIBLE);
            favorites.setExpanded(true);
            holder.binding.expandUni.setImageResource(R.drawable.collapse);
        }
        else{
            holder.binding.universityInfoLayout.setVisibility(View.GONE);
            favorites.setExpanded(false);
            holder.binding.expandUni.setImageResource(R.drawable.expand);
        }
        notifyItemChanged(holder.getAdapterPosition());
    }

    private void checkVisibilityStatus(FavoritesEntity favorites, ViewHolder holder) {
        if(favorites.getExpanded()){
            holder.binding.universityInfoLayout.setVisibility(View.VISIBLE);
            holder.binding.expandUni.setImageResource(R.drawable.collapse);
        }
        else {
            holder.binding.universityInfoLayout.setVisibility(View.GONE);
            holder.binding.expandUni.setImageResource(R.drawable.expand);
        }
    }

    private void checkIsEmpty(FavoritesEntity favorites, ViewHolder holder) {
        if(favorites.isEmpty())
            holder.binding.expandUni.setVisibility(View.GONE);
        else
            holder.binding.expandUni.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return favoritesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ListItemFavoritesBinding binding;

        public ViewHolder(ListItemFavoritesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
