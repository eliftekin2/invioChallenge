package com.eliftekin.inviochallenge.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FavoritesEntity {
    @PrimaryKey(autoGenerate = true)
    int id;

    String name;
    String phone;
    String fax;
    String website;
    String email;
    String adress;
    String rector;

    Boolean isExpanded;

    public FavoritesEntity(String name, String phone, String fax, String website, String email, String adress, String rector) {
        this.name = name;
        this.phone = phone;
        this.fax = fax;
        this.website = website;
        this.email = email;
        this.adress = adress;
        this.rector = rector;

        isExpanded = false;

    }

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

    public Boolean getExpanded() {
        return isExpanded;
    }

    public void setExpanded(Boolean expanded) {
        isExpanded = expanded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
}
