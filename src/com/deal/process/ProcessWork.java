package com.deal.process;

import java.io.Serializable;

public class ProcessWork implements Serializable {
    private String proname;

    public String getProname() {
        return proname;
    }

    public void setProname(String proname) {
        this.proname = proname;
    }

    public int getProneed() {
        return proneed;
    }

    public void setProneed(int proneed) {
        this.proneed = proneed;
    }

    public ProcessWork(String proname, int proneed) {
        this.proname = proname;
        this.proneed = proneed;
    }

    private int proneed;
}
