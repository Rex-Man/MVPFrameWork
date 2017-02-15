package com.oocl.manre.mvpframework.searchViewStudy;

import java.io.Serializable;

/**
 * Created by manre on 2/13/17.
 */

public class TruckerModel implements Serializable {
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
}
