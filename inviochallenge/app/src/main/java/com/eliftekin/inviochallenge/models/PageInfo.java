package com.eliftekin.inviochallenge.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PageInfo {
    @SerializedName("currentPage")
    @Expose
    private int currentPage;

    @SerializedName("totalPage")
    @Expose
    private int totalPage;

    @SerializedName("data")
    @Expose
    private ArrayList<DataList> dataList;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public ArrayList<DataList> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<DataList> dataList) {
        this.dataList = dataList;
    }

}
