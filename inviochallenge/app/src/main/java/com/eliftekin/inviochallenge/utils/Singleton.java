package com.eliftekin.inviochallenge.utils;

import com.eliftekin.inviochallenge.models.PageInfo;

public class Singleton {
    private PageInfo pageInfo;
    private static Singleton instance;

    private Singleton(){

    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public static synchronized Singleton getInstance(){
        if(instance == null)
            instance = new Singleton();

        return  instance;
    }

}
