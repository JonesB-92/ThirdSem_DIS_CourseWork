package Opgave_8_FletteSorteringThreads;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ThreadClass extends Thread {
    private List<Integer> list;
    private int l;
    private int h;

    private FletteSortering fletteSortering;

    public ThreadClass(FletteSortering fletteSortering, List<Integer> list, int l, int h) {
        this.l = l;
        this.h = h;
        this.fletteSortering = fletteSortering;
        this.list = list;
    }

    @Override
    public void run() {
       fletteSortering.mergesort(list, l, h);
    }

}
