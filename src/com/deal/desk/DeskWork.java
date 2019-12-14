package com.deal.desk;

import java.io.Serializable;

public class DeskWork implements Serializable {
    private String deskname;
    private int startadd;
    private int space;
    private String status;
    private String proname="";
    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getDeskname() {
        return deskname;
    }

    public String getProname() {
        return proname;
    }

    public void setProname(String proname) {
        this.proname = proname;
    }

    public void setDeskname(String deskname) {
        this.deskname = deskname;
    }

    public int getStartadd() {
        return startadd;
    }

    public void setStartadd(int startadd) {
        this.startadd = startadd;
    }

    public int getSpace() {
        return space;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DeskWork(String deskname, int startadd, int space, String status) {
        this.deskname = deskname;
        this.startadd = startadd;
        this.space = space;
        this.status = status;
    }
    public DeskWork(String deskname,String proname, int startadd, int space, String status) {
        this.deskname = deskname;
        this.proname=proname;
        this.startadd = startadd;
        this.space = space;
        this.status = status;
    }
    public DeskWork(String deskname,int space,int index) {
        this.deskname = deskname;
        this.space = space;
        this.index=index;
    }
}
