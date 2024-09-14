package cpuschedulingalgorithms;

import java.util.*;

public class ShortestJobFirst {

    public ShortestJobFirst() {
        Scanner scan = new Scanner(System.in);

        System.out.println("");
        System.out.println("--------------------SHORTEST JOB FIRST (SJF)--------------------\n");
        int n;
        do {
            System.out.print("Enter the number of processes min(3) max(6): ");
            n = scan.nextInt();
            if (n < 3 || n > 6) {
                System.out.println("Invalid input! Please enter a number between 3 - 6.");
            }
        } while (n < 3 || n > 6);

        int pid[] = new int[n];
        int ct[] = new int[n];
        int at[] = new int[n];
        int ta[] = new int[n];
        int bt[] = new int[n];
        int wt[] = new int[n];
        int f[] = new int[n];
        int st = 0, tot = 0;
        float avgwt = 0, avgta = 0;
        int cpuTime = 0;
        System.out.println("");
        System.out.println("Enter Arrival Time and Burst Time for each process:\n");

        for (int i = 0; i < n; i++) {
            System.out.println("\nProcess " + (i + 1));
            System.out.print("\tArrival Time: ");
            at[i] = scan.nextInt();
            System.out.print("\tBurst Time: ");
            bt[i] = scan.nextInt();
            pid[i] = i + 1;
            f[i] = 0;
            cpuTime += bt[i];

        }
        boolean a = true;
        //for gantt chart
        ArrayList<Integer> cttry = new ArrayList<>();
        ArrayList<Integer> attry = new ArrayList<>();
        ArrayList<Integer> idtry = new ArrayList<>();
        System.out.println("\n- - - - - - - - - - - - - - - - G A N T T  C H A R T - - - - - - - - - - - - - - - -");
        while (true) {
            int c = n, min = 999;
            if (tot == n) {
                break;
            }
            for (int i = 0; i < n; i++) {
                if ((at[i] <= st) && (f[i] == 0) && (bt[i] < min)) {
                    min = bt[i];
                    c = i;
                }
            }
            if (c == n) {
                st++;
            } else {
                ct[c] = st + bt[c];
                st += bt[c];
                ta[c] = ct[c] - at[c];
                wt[c] = ta[c] - bt[c];
                f[c] = 1;
                tot++;

                //System.out.print("|\tP" + pid[c] + "\t");
                idtry.add(pid[c]);
                cttry.add(ct[c]);
                attry.add(at[c]);
            }
        }

        for (int i = 0; i < n; i++) {
            if (i == 0) {
                System.out.print(attry.get(i) == 0 ? "-----------------------" : "-------------------------");
            } else {
                System.out.print(attry.get(i) > cttry.get(i - 1) ? "----------------------------------------" : "-------------------------");
            }
        }
        System.out.print("~\n");

        //Process
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                System.out.print(attry.get(i) == 0 ? "|\tP" + idtry.get(i) + "\t" : "|Idle\t\t" + "|\tP" + idtry.get(i) + "\t");
            } else {
                System.out.print(attry.get(i) > cttry.get(i - 1) ? "|Idle\t\t" + "|\tP" + idtry.get(i) + "\t" : "|\tP" + idtry.get(i) + "\t");

            }
        }

        //CT
        System.out.print("|\n~");
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                System.out.print(attry.get(i) == 0 ? "-----------------------" : "-------------------------");
            } else {
                System.out.print(attry.get(i) > cttry.get(i - 1) ? "----------------------------------------" : "-------------------------");
            }
        }
        System.out.println("");
        for (int i = 0; i < n; i++) {

            if (i == 0) {
                System.out.print(attry.get(i) == 0 ? "0\t\t" + cttry.get(i) + "\t\t" : "0\t\t" + attry.get(i) + "\t\t" + cttry.get(i) + "\t\t");
            } else {
                System.out.print(attry.get(i) > cttry.get(i - 1) ? attry.get(i) + "\t\t" + cttry.get(i) + "\t\t" : cttry.get(i) + "\t\t");
            }

        }
        System.out.println("");
        System.out.println("");
        System.out.print("\n- - - - - - - - - - - - - - - - O U T P U T  T A B L E - - - - - - - - - - - - - - - -");
        System.out.println("\n---------------------------------------------");
        System.out.println("P" + "\t" + "CT" + "\t" + "AT" + "\t" + "TAT" + "\t" + "BT" + "\t" + "WT");
        for (int i = 0; i < n; i++) {
            avgwt += wt[i];
            avgta += ta[i];
            System.out.println(pid[i] + "\t" + ct[i] + "\t" + at[i] + "\t" + ta[i] + "\t" + bt[i] + "\t" + wt[i]);
        }
        System.out.println("---------------------------------------------");
        System.out.println("Average turn around time: " + (float) (avgta / n) + "ms");
        System.out.println("Average waiting time: " + (float) (avgwt / n) + "ms");
        float cpu_util = ((float) (st - min(at))) / st * 100;
        System.out.println("\nCPU Utilization: " + cpu_util + "%");
    }

    public static int min(int[] array) {
        int minValue = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < minValue) {
                minValue = array[i];
            }
        }
        return minValue;
    }
}
