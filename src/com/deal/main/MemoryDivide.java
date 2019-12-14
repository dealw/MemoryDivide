package com.deal.main;

import com.deal.desk.DeskWork;
import com.deal.process.ProcessWork;

import java.io.*;
import java.util.*;

/**
 *  操作系统实验二 动态分配与回收内存
 */
public class MemoryDivide {
    private static List<ProcessWork> parr;
    private static List<DeskWork> darr;
    private static List<DeskWork> darrchange;
    private static List<ProcessWork> parrchange;
    public static void main(String[] args) throws IOException, ClassNotFoundException {
            welcomme();
    }
//首页选择功能
    private static void welcomme() throws IOException, ClassNotFoundException {
        Scanner sc=new Scanner(System.in);
       out: while(true) {
            System.out.println("-----------------------------------");
            System.out.println("----------1.查看/录入信息---------");
            System.out.println("----------2.首次适应算法-----");
            System.out.println("----------3.循环首次适应算法--");
            System.out.println("----------4.最佳适应算法------");
            System.out.println("----------5.最坏适应算法------");
            System.out.println("----------6.回收内存----------");
            System.out.println("----------7.退出--------------");
            System.out.println("-----------------------------------");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    add();
                    break;
                case 7:
                    break out;
                case 2:
                    firstfit();
                    break ;
                case 3:
                    cyclefirst();
                    break ;
                case 4:
                    mostadapt();
                    break ;
                case 5:
                    mostbad();
                    break ;
                case 6:
                    recycle();
                    break ;
            }
        }
    }
//最坏适应算法
    private static void mostbad() throws IOException, ClassNotFoundException {
        darrchange=deepCopy(darr);
        parrchange=deepCopy(parr);
        boolean isfind=false;
        List<DeskWork> sortlist=new ArrayList<>();
        int count=0;
        List<ProcessWork> pnum=deepCopy(parr);
        List<ProcessWork> pnum1=new ArrayList<>();
        for(int i=0;i<parrchange.size();i++){
            isfind=false;
            sortlist.removeAll(sortlist);
            for(int j=0;j<darrchange.size();j++){
                if(parrchange.get(i).getProneed()<=darrchange.get(j).getSpace()&&darrchange.get(j).getStatus().equals("free")){
                    sortlist.add(new DeskWork(darrchange.get(j).getDeskname(),darrchange.get(j).getSpace(),j));
//                    pnum.add(new ProcessWork(parrchange.get(i).getProname(),parrchange.get(i).getProneed()));
                    isfind=true;
                }
            }
            if(isfind){
                pnum1.add(new ProcessWork(parrchange.get(i).getProname(),parrchange.get(i).getProneed()));
            }
//            自定义排序
            if(sortlist.size()>1) {
                Collections.sort(sortlist, new Comparator<DeskWork>() {
                    @Override
                    public int compare(DeskWork o1, DeskWork o2) {
                        return -(o1.getSpace() - o2.getSpace());
                    }
                });}
            if(isfind&&sortlist.size()>0){
                if(!(sortlist.get(0).getSpace()-parrchange.get(i).getProneed()==0)) {
                    darrchange.add(sortlist.get(0).getIndex(),new DeskWork(darrchange.get(sortlist.get(0).getIndex()).getDeskname(),parrchange.get(i).getProname(),darrchange.get(sortlist.get(0).getIndex()).getStartadd(),parrchange.get(i).getProneed(),"busy"));
                    darrchange.get(sortlist.get(0).getIndex() + 1).setStartadd(darrchange.get(sortlist.get(0).getIndex() + 1).getStartadd() + parrchange.get(i).getProneed());
                    darrchange.get(sortlist.get(0).getIndex() + 1).setSpace(darrchange.get(sortlist.get(0).getIndex() + 1).getSpace() - parrchange.get(i).getProneed());
                    count++;
                }else {
                    darrchange.add(sortlist.get(0).getIndex(),new DeskWork(darrchange.get(sortlist.get(0).getIndex()).getDeskname(),parrchange.get(i).getProname(),darrchange.get(sortlist.get(0).getIndex()).getStartadd(),parrchange.get(i).getProneed(),"busy"));
//                    darrchange.get(sortlist.get(0).getIndex() + 1).setStartadd(darrchange.get(sortlist.get(0).getIndex() + 1).getStartadd() + parrchange.get(i).getProneed());
//                    darrchange.get(sortlist.get(0).getIndex() + 1).setSpace(darrchange.get(sortlist.get(0).getIndex() + 1).getSpace() - parrchange.get(i).getProneed());
                    count++;
                    darrchange.remove(sortlist.get(0).getIndex()+1);
                }
            }
//            for(DeskWork d:sortlist){
//                System.out.println(d.getSpace());
//            }
        }
        for(int k=0;k<pnum1.size();k++){
            for(int y=0;y<pnum.size();y++){
                if(pnum.get(y).getProname().equals(pnum1.get(k).getProname())){
                    pnum.remove(y);
                }
            }
        }
        if(pnum1.size()==0){
            System.out.println("全部未找到合适大小的分区！");
            return ;
        }

        if(count<parr.size()){
            System.out.print("未找到合适大小的进程：");
            for(ProcessWork p:pnum){
                System.out.print(p.getProname()+"  ");
            }
            System.out.println();
        }
        showdesk(darrchange);
    }

    //最佳适应算法
    private static void mostadapt() throws IOException, ClassNotFoundException {
        darrchange=deepCopy(darr);
        parrchange=deepCopy(parr);
        boolean isfind=false;
        List<DeskWork> sortlist=new ArrayList<>();
        int count=0;
        List<ProcessWork> pnum=deepCopy(parr);
        List<ProcessWork> pnum1=new ArrayList<>();
        for(int i=0;i<parrchange.size();i++){
            sortlist.removeAll(sortlist);
            for(int j=0;j<darrchange.size();j++){
                if(parrchange.get(i).getProneed()<=darrchange.get(j).getSpace()&&darrchange.get(j).getStatus().equals("free")){
                    sortlist.add(new DeskWork(darrchange.get(j).getDeskname(),darrchange.get(j).getSpace(),j));
                    isfind=true;
                }
            }
            if(isfind){
                pnum1.add(new ProcessWork(parrchange.get(i).getProname(),parrchange.get(i).getProneed()));
            }
//            自定义排序
            if(sortlist.size()>1) {
                Collections.sort(sortlist, new Comparator<DeskWork>() {
                    @Override
                    public int compare(DeskWork o1, DeskWork o2) {
                        return o1.getSpace() - o2.getSpace();
                    }
                });
            }
            if(isfind&&sortlist.size()>0){
                if(!(sortlist.get(0).getSpace()-parrchange.get(i).getProneed()==0)) {
                    darrchange.add(sortlist.get(0).getIndex(),new DeskWork(darrchange.get(sortlist.get(0).getIndex()).getDeskname(),parrchange.get(i).getProname(),darrchange.get(sortlist.get(0).getIndex()).getStartadd(),parrchange.get(i).getProneed(),"busy"));
                    darrchange.get(sortlist.get(0).getIndex() + 1).setStartadd(darrchange.get(sortlist.get(0).getIndex() + 1).getStartadd() + parrchange.get(i).getProneed());
                    darrchange.get(sortlist.get(0).getIndex() + 1).setSpace(darrchange.get(sortlist.get(0).getIndex() + 1).getSpace() - parrchange.get(i).getProneed());
                }else {
                    darrchange.add(sortlist.get(0).getIndex(),new DeskWork(darrchange.get(sortlist.get(0).getIndex()).getDeskname(),parrchange.get(i).getProname(),darrchange.get(sortlist.get(0).getIndex()).getStartadd(),parrchange.get(i).getProneed(),"busy"));
//                    darrchange.get(sortlist.get(0).getIndex() + 1).setStartadd(darrchange.get(sortlist.get(0).getIndex() + 1).getStartadd() + parrchange.get(i).getProneed());
//                    darrchange.get(sortlist.get(0).getIndex() + 1).setSpace(darrchange.get(sortlist.get(0).getIndex() + 1).getSpace() - parrchange.get(i).getProneed());
                    darrchange.remove(sortlist.get(0).getIndex()+1);
                }
                count++;
            }
//            for(DeskWork d:sortlist){
//                System.out.println(d.getSpace());
//            }
        }
//        if(!isfind){
//            System.out.println("未找到合适大小的分区！");
//            return ;
//        }
        for(int k=0;k<pnum1.size();k++){
            for(int y=0;y<pnum.size();y++){
                if(pnum.get(y).getProname().equals(pnum1.get(k).getProname())){
                    pnum.remove(y);
                }
            }
        }
        if(pnum1.size()==0){
            System.out.println("全部未找到合适大小的分区！");
            return ;
        }

        if(count<parr.size()){
            System.out.print("未找到合适大小的进程：");
            for(ProcessWork p:pnum){
                System.out.print(p.getProname()+"  ");
            }
            System.out.println();
        }
        showdesk(darrchange);
    }

    //循环首次算法
    private static void cyclefirst() throws IOException, ClassNotFoundException {
        darrchange=deepCopy(darr);
        parrchange=deepCopy(parr);
        boolean idfind=false;
        int j=0;
        int count=0;
        List<ProcessWork> pnum=deepCopy(parr);
        List<ProcessWork> pnum1=new ArrayList<>();
        int count1=0;
        for(int i=0;i<parrchange.size();i++){
            count=0;
            for(;j<darrchange.size();){
                if(parrchange.get(i).getProneed()<=darrchange.get(j).getSpace()&&darrchange.get(j).getStatus().equals("free")){
                    darrchange.add(j,new DeskWork(darrchange.get(j).getDeskname(),parrchange.get(i).getProname(),darrchange.get(j).getStartadd(),parrchange.get(i).getProneed(),"busy"));
                    if(!(darrchange.get(j+1).getSpace()-parrchange.get(i).getProneed()<=0)) {
                        darrchange.get(j + 1).setStartadd(darrchange.get(j + 1).getStartadd() + parrchange.get(i).getProneed());
                        darrchange.get(j + 1).setSpace(darrchange.get(j + 1).getSpace() - parrchange.get(i).getProneed());
                    }else {
//                        darrchange.get(j + 1).setStartadd(darrchange.get(j + 1).getStartadd() + parrchange.get(i).getProneed());
//                        darrchange.get(j + 1).setSpace(darrchange.get(j + 1).getSpace() - parrchange.get(i).getProneed());
                        darrchange.remove(j+1);
                    }
                    pnum1.add(new ProcessWork(parrchange.get(i).getProname(),parrchange.get(i).getProneed()));
                    count1++;
                    idfind=true;
                    break;
                }
                j+=1;
                if(j>=darrchange.size()){
                    j=0;
                }
                count++;
                if(count>darrchange.size()){
                    break;
                }
            }
        }
//        if(!idfind){
//            System.out.println("未找到合适大小的分区！");
//            return ;
//        }
        if(pnum1.size()==0){
            System.out.println("全部未找到合适大小的分区！");
            return ;
        }
        for(int k=0;k<pnum1.size();k++){
            for(int y=0;y<pnum.size();y++){
                if(pnum.get(y).getProname().equals(pnum1.get(k).getProname())){
                    pnum.remove(y);
                }
            }
        }
        if(count1<parr.size()){
            System.out.print("未找到合适大小的进程：");
            for(ProcessWork p:pnum){
                System.out.print(p.getProname()+"  ");
            }
            System.out.println();
        }
        showdesk(darrchange);
    }

    //回收内存方法
    private static void recycle() {
        Scanner sc=new Scanner(System.in);
        System.out.println("输入要回收的进程名:");
        String proname = sc.next();
        boolean isfind=false;
        boolean iszero=false;
        boolean ischange=false;
        for(int i=0;i<darrchange.size();i++){
            if(darrchange.get(i).getProname().equals(proname)){
                DeskWork d=darrchange.get(i);
                if(i-1>=0){
                    iszero=true;
                }
                if(darrchange.get(i+1).getStatus().equals("free")/*||(iszero?darrchange.get(i-1).getStatus().equals("free"):true)*/){
                    DeskWork d1 = darrchange.get(i + 1);
                    if(iszero) {
                        DeskWork d11 = darrchange.get(i - 1);
                    }
//                    if(darrchange.get(i+1).getStatus().equals("free")) {
                        if(darrchange.get(i).getDeskname().equals(d1.getDeskname())) {
                            if (darrchange.get(i).getStartadd() + darrchange.get(i).getSpace() == d1.getStartadd()) {
                                d1.setStartadd(d.getStartadd());
                                d1.setSpace(d.getSpace() + d1.getSpace());
                                darrchange.remove(i);

                                if (iszero&&darrchange.get(i - 1).getStatus().equals("free") && darrchange.get(i - 1).getDeskname().equals(darrchange.get(i).getDeskname())) {
                                    DeskWork d2 = darrchange.get(i - 1);
                                    DeskWork d3 = darrchange.get(i);
                                    if (d2.getStartadd() + d2.getSpace() == d3.getStartadd()) {
                                        d2.setSpace(d2.getSpace() + d3.getSpace());
                                        darrchange.remove(i);
                                    }
                                }
                                isfind = true;
                                ischange = true;
                            }
                        }
//                    }
//                    else if (darrchange.get(i-1).getStatus().equals("free")){
//                        if(darrchange.get(i).getDeskname().equals(d11.getDeskname())) {
//                            d11.setSpace();
//                        }
//                    }
                }else{
                    d.setProname("");
                    d.setStatus("free");
                    isfind=true;
                    break;
                }
            }
        }
        if(!isfind){
            System.out.println("未找到该进程！");
            return;
        }
        showdesk(darrchange);
    }

    //深度拷贝list序列化方法
public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {
    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
    ObjectOutputStream out = new ObjectOutputStream(byteOut);
    out.writeObject(src);

    ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
    ObjectInputStream in = new ObjectInputStream(byteIn);
    @SuppressWarnings("unchecked")
    List<T> dest = (List<T>) in.readObject();
    return dest;
}
//首次适应算法
    private static void firstfit() throws IOException, ClassNotFoundException {
         darrchange=deepCopy(darr);
         parrchange=deepCopy(parr);
        List<ProcessWork> pnum=deepCopy(parr);
        List<ProcessWork> pnum1=new ArrayList<>();
        int count=0;
        boolean idfind=false;
        for(int i=0;i<parrchange.size();i++){
            for(int j=0;j<darrchange.size();j++){
                if(parrchange.get(i).getProneed()<=darrchange.get(j).getSpace()&&darrchange.get(j).getStatus().equals("free")){
                    darrchange.add(j,new DeskWork(darrchange.get(j).getDeskname(),parrchange.get(i).getProname(),darrchange.get(j).getStartadd(),parrchange.get(i).getProneed(),"busy"));
                    if(!(darrchange.get(j+1).getSpace()-parrchange.get(i).getProneed()<=0)) {
                        darrchange.get(j + 1).setStartadd(darrchange.get(j + 1).getStartadd() + parrchange.get(i).getProneed());
                        darrchange.get(j + 1).setSpace(darrchange.get(j + 1).getSpace() - parrchange.get(i).getProneed());
                    }else {
//                        darrchange.get(j + 1).setStartadd(darrchange.get(j + 1).getStartadd() + parrchange.get(i).getProneed());
//                        darrchange.get(j + 1).setSpace(darrchange.get(j + 1).getSpace() - parrchange.get(i).getProneed());
                        darrchange.remove(j+1);
                    }
                    pnum1.add(new ProcessWork(parrchange.get(i).getProname(),parrchange.get(i).getProneed()));
                    count++;
                    idfind=true;
                    break;
                }
            }
        }
        for(int k=0;k<pnum1.size();k++){
            for(int y=0;y<pnum.size();y++){
                if(pnum.get(y).getProname().equals(pnum1.get(k).getProname())){
                    pnum.remove(y);
                }
            }
        }
        if(pnum1.size()==0){
            System.out.println("全部未找到合适大小的分区！");
            return ;
        }

        if(count<parr.size()){
            System.out.print("未找到合适大小的进程：");
            for(ProcessWork p:pnum){
                System.out.print(p.getProname()+"  ");
            }
            System.out.println();
        }
//        if(!idfind){
//            System.out.println("未找到合适大小的分区！");
//            return ;
//        }
        showdesk(darrchange);
    }
    //录入信息
    private static void add() throws IOException, ClassNotFoundException {
        Scanner sc=new Scanner(System.in);
       out: while(true) {
            System.out.println("-----------------------------------");
            System.out.println("----------1.录入分区信息---------");
            System.out.println("----------2.录入进程信息---------");
            System.out.println("----------3.查看分区信息---------");
            System.out.println("----------4.查看进程信息---------");
            System.out.println("----------5.重置分区信息---------");
            System.out.println("----------6.返回上一层-----------");
            System.out.println("-----------------------------------");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    AddDesk();
                    break;
                case 2:
                    AddProcess();
                    break;
                case 6:
                    break out;
                case 3:
                    showdesk(darr);
                    break;
                case 4:
                    showprocess(parr);
                    break;
                case 5:
                    resetdesk();
                    break ;
            }
        }
    }
//重置分区信息（冗余方法）
    private static void resetdesk() throws IOException, ClassNotFoundException {
        darr=deepCopy(darrchange);
        parr=deepCopy(parrchange);
    }

    //查看分区信息
    private static void showdesk(List<DeskWork> darr1) {
        System.out.println("分区信息如下:");
        System.out.println("------------------------------------");
        System.out.println(" 区号    进程    起址    大小    状态");
        for(DeskWork d:darr1){
            System.out.println("------------------------------------");
            System.out.println("  "+d.getDeskname()+"       "+d.getProname()+"       "+d.getStartadd()+"      "+d.getSpace()+"      "+d.getStatus());
        }
    }
//查看进程信息
    private static void showprocess(List<ProcessWork> parr) {
        System.out.println("进程信息如下:");
        System.out.println("------------------------------------");
        System.out.println(" 进程名                     所需内存");
        for(ProcessWork p:parr){
            System.out.println("------------------------------------");
            System.out.println("  "+p.getProname()+"                          "+p.getProneed());
        }
    }

    //录入进程信息
    private static void AddProcess() {
        Scanner sc=new Scanner(System.in);
        System.out.println("有几个进程?");
        int pronum = sc.nextInt();
        parr=new ArrayList<ProcessWork>();
//        parr=new ProcessWork[pronum];//设置存放进程对象的数组
//        int count=0;//设置存放标记
        while(pronum>0){
            System.out.println("输入进程名和所需内存大小:");
            String proname = sc.next();
            int proneed = sc.nextInt();
            parr.add(new ProcessWork(proname,proneed));
//            count++;
            pronum--;
        }
    }
//录入磁盘信息
    private static void AddDesk() {
        Scanner sc=new Scanner(System.in);
        System.out.println("有几个分区?");
        int desknum = sc.nextInt();
//        darr=new DeskWork[desknum];//设置存放进程对象的数组
//        int count=0;//设置存放标记
        darr=new ArrayList<DeskWork>();
        while(desknum>0){
            System.out.println("输入分区名、起址、大小、状态:");
            String deskname = sc.next();
            int start = sc.nextInt();
            int space = sc.nextInt();
            String status = sc.next();
            darr.add(new DeskWork(deskname,start,space,status));
//            count++;
            desknum--;
        }
    }
}
