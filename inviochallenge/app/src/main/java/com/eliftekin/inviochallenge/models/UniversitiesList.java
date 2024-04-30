package com.eliftekin.inviochallenge.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UniversitiesList {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("fax")
    @Expose
    private String fax;

    @SerializedName("website")
    @Expose
    private String website;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("adress")
    @Expose
    private String adress;

    @SerializedName("rector")
    @Expose
    private String rector;

    public Boolean isEmpty(){

        boolean isEmpty = false;

        if(phone.equals("-") &&
                fax.equals("-") &&
                website.equals("-") &&
                email.equals("-") &&
                adress.equals("-") &&
                rector.equals("-") ){

            isEmpty = true;
        }
        return isEmpty;
    }

    private boolean isExpanded = false;

    private boolean isAddedToFavs = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getRector() {
        return rector;
    }

    public void setRector(String rector) {
        this.rector = rector;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public boolean isAddedToFavs() {
        return isAddedToFavs;
    }

    public void setAddedToFavs(boolean addedToFavs) {
        isAddedToFavs = addedToFavs;
    }
}
