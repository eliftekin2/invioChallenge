package com.eliftekin.inviochallenge.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DataList {
    @SerializedName("province")
    @Expose
    private String province;

    @SerializedName("universities")
    @Expose
    private ArrayList<UniversitiesList> universitiesList;

    private boolean isExpanded= false;

    public boolean isEmpty(){
        return universitiesList.isEmpty();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public ArrayList<UniversitiesList> getUniversitiesList() {
        return universitiesList;
    }

    public void setUniversitiesList(ArrayList<UniversitiesList> universitiesList) {
        this.universitiesList = universitiesList;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}
