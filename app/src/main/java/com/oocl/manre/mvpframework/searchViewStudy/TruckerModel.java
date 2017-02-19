package com.oocl.manre.mvpframework.searchViewStudy;

import java.io.Serializable;

/**
 * Created by manre on 2/13/17.
 */

public class TruckerModel implements Serializable , Comparable<TruckerModel>{
    private String name;
    private String sortLetters;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }



    @Override
    public int compareTo(TruckerModel o) {

        return this.getName().compareTo(o.getName());
    }
}
