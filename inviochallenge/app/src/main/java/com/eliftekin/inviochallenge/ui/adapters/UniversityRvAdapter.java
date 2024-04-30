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
import com.eliftekin.inviochallenge.databinding.ListItemUniBinding;
import com.eliftekin.inviochallenge.models.UniversitiesList;
import com.eliftekin.inviochallenge.repo.FavoritesRepository;
import com.eliftekin.inviochallenge.ui.fragments.UniWebFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UniversityRvAdapter extends RecyclerView.Adapter<UniversityRvAdapter.ViewHolder> {

    Context context;
    ArrayList<UniversitiesList> universitiesListArrayList;
    FavoritesRepository favoritesRepository;

    ExecutorService executor;
    Handler handler;

    public UniversityRvAdapter(Context context, ArrayList<UniversitiesList> universitiesListArrayList) {
        this.context = context;
        this.universitiesListArrayList = universitiesListArrayList;

        favoritesRepository = new FavoritesRepository(context);

        executor = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemUniBinding binding = ListItemUniBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UniversitiesList universitiesList = universitiesListArrayList.get(position);

        holder.binding.universityName.setText(universitiesList.getName());
        holder.binding.phone.setText(universitiesList.getPhone());
        holder.binding.fax.setText(universitiesList.getFax());
        holder.binding.website.setText(universitiesList.getWebsite());
        holder.binding.email.setText(universitiesList.getEmail());
        holder.binding.adress.setText(universitiesList.getAdress());
        holder.binding.rector.setText(universitiesList.getRector());

        checkIsEmpty(universitiesList, holder);
        checkVisibilityStatus(holder, universitiesList);
        checkFavoriteState(holder);

        holder.binding.expandUni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setVisibilityStatus(holder, universitiesList);
            }
        });

        holder.binding.addToFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOrRemoveFromBD(universitiesList, holder);
            }
        });

        holder.binding.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeCall(holder);
            }
        });

        holder.binding.website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToWebPage(holder);
            }
        });

    }

    private void goToWebPage(ViewHolder holder) {
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

    private void makeCall(ViewHolder holder) {
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

    private void checkFavoriteState(ViewHolder holder) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                List<FavoritesEntity> favoritesEntityList = favoritesRepository.getFavorites();
                String name = holder.binding.universityName.getText().toString();

                boolean isAdded = false;

                for (FavoritesEntity favorites : favoritesEntityList){
                    if (favorites.getName().equals(name)){
                        isAdded = true;
                        break;
                    }
                }

                boolean finalIsAdded = isAdded;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(finalIsAdded)
                            holder.binding.addToFavorites.setImageResource(R.drawable.added_to_favs);
                        else
                            holder.binding.addToFavorites.setImageResource(R.drawable.add_to_favs);

                    }
                });
            }
        });
    }

    private void addOrRemoveFromBD(UniversitiesList universitiesList, ViewHolder holder) {
        String name = holder.binding.universityName.getText().toString();
        String phone = holder.binding.phone.getText().toString();
        String fax = holder.binding.fax.getText().toString();
        String website = holder.binding.website.getText().toString();
        String email = holder.binding.email.getText().toString();
        String adress = holder.binding.adress.getText().toString();
        String rector = holder.binding.rector.getText().toString();

        executor.execute(new Runnable() {
            @Override
            public void run() {
                List<FavoritesEntity> favoritesEntityList = favoritesRepository.getFavorites();

                boolean isInDatabase = false;

                for (FavoritesEntity list : favoritesEntityList){
                    if (list.getName().equals(name)){
                        isInDatabase = true;
                        favoritesRepository.delete(name);
                        universitiesList.setAddedToFavs(false);
                        break;
                    }
                }

                if (!isInDatabase){
                    FavoritesEntity newFavorite = new FavoritesEntity(name, phone, fax, website, email, adress, rector);
                    universitiesList.setAddedToFavs(true);
                    favoritesRepository.insert(newFavorite);
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(universitiesList.isAddedToFavs())
                            holder.binding.addToFavorites.setImageResource(R.drawable.added_to_favs);
                        else
                            holder.binding.addToFavorites.setImageResource(R.drawable.add_to_favs);
                    }

                });

            }
        });

    }

    private void setVisibilityStatus(ViewHolder holder, UniversitiesList universitiesList) {
        if (!universitiesList.isExpanded()){
            holder.binding.universityInfoLayout.setVisibility(View.VISIBLE);
            universitiesList.setExpanded(true);
            holder.binding.expandUni.setImageResource(R.drawable.collapse);
        }
        else{
            holder.binding.universityInfoLayout.setVisibility(View.GONE);
            universitiesList.setExpanded(false);
            holder.binding.expandUni.setImageResource(R.drawable.expand);
        }
        notifyItemChanged(holder.getAdapterPosition());
    }

    private void checkVisibilityStatus(ViewHolder holder, UniversitiesList universitiesList) {
        if(universitiesList.isExpanded()){
            holder.binding.universityInfoLayout.setVisibility(View.VISIBLE);
            holder.binding.expandUni.setImageResource(R.drawable.collapse);
        }
        else{
            holder.binding.universityInfoLayout.setVisibility(View.GONE);
            holder.binding.expandUni.setImageResource(R.drawable.expand);
        }
    }

    private void checkIsEmpty(UniversitiesList universitiesList, ViewHolder holder) {
        if (universitiesList.isEmpty())
            holder.binding.expandUni.setVisibility(View.GONE);
        else
            holder.binding.expandUni.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return universitiesListArrayList.size();
    }

    public void collapseAll() {
        for (UniversitiesList university : universitiesListArrayList) {
            university.setExpanded(false);
        }

        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ListItemUniBinding binding;

        public ViewHolder(ListItemUniBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
