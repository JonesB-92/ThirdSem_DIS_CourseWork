package Opgave_8_FletteSorteringThreads;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestFlettesortering {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();

        Random r = new Random();
        for (int i = 0; i < 1_000_000; i++) {
            list.add(Math.abs(r.nextInt() % 10000));
        }

//        System.out.println(list);


        FletteSortering sort = new FletteSortering();

        int l = 0;
        int h = list.size() - 1;
        int m = (l + h) / 2;

        long l1, l2;
        l1 = System.nanoTime();

        ThreadClass thread1st = new ThreadClass(sort, list, l, m);
        ThreadClass thread2nd = new ThreadClass(sort, list, m + 1, h);

        thread1st.start();
        thread2nd.start();
        try {
            thread1st.join();
            thread2nd.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        sort.mergeWrapper(list, 0, m, h);

        l2 = System.nanoTime();

        System.out.println();
        System.out.println("Køretiden var " + (l2 - l1) / 1_000_000);
        System.out.println();
        System.out.println("\n" + list.getFirst() + " - " + list.get(m) + " - " + list.getLast());
    }

}
