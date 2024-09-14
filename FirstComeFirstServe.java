package cpuschedulingalgorithms;

import java.text.DecimalFormat;
import java.util.*;

public class FirstComeFirstServe {

    public FirstComeFirstServe() {
        Scanner s = new Scanner(System.in);
        DecimalFormat df = new DecimalFormat("0.00");
        int n = 0;

        System.out.print("Enter the number of processes (between 3 and 6): ");
        int pronum = s.nextInt(); // pronum = process number

        while (pronum < 3 || pronum > 6) {
            System.out.println("Invalid input! Number of processes should be between 3 and 6.");
            System.out.print("Enter the number of processes (between 3 and 6): ");
            pronum = s.nextInt();
        }

        System.out.println("");

        int proId[] = new int[pronum]; // proId = process ID
        int arrivalTime[] = new int[pronum]; // at = arrival time
        int burstTime[] = new int[pronum]; // bt = burst time
        int completionTime[] = new int[pronum]; // ct = completion time
        int turnaroundTime[] = new int[pronum]; // tat = turnaround time
        int waitingTime[] = new int[pronum]; // wt = waiting time
        int temp;
        float awt = 0, att = 0, totbt = 0, totct = 0; // awt = average waiting time //att = average

        // for inputting of data
        System.out.println("Enter arrival time and burst time for each process:");
        for (int i = 0; i < pronum; i++) {
            System.out.println("\nProcess " + (i + 1));
            System.out.print("\tArrival Time: ");
            arrivalTime[i] = s.nextInt();
            System.out.print("\tBurst Time: ");
            burstTime[i] = s.nextInt();
            proId[i] = i + 1;
        }

        // this is where the sorting will happen
        for (int i = 0; i < pronum; i++) {
            for (int j = 0; j < pronum - (i + 1); j++) {
                if (arrivalTime[j] > arrivalTime[j + 1]) {
                    temp = arrivalTime[j];
                    arrivalTime[j] = arrivalTime[j + 1];
                    arrivalTime[j + 1] = temp;
                    temp = burstTime[j];
                    burstTime[j] = burstTime[j + 1];
                    burstTime[j + 1] = temp;
                    temp = proId[j];
                    proId[j] = proId[j + 1];
                    proId[j + 1] = temp;
                }
            }
        }

        // completion time
        completionTime[0] = burstTime[0] + arrivalTime[0];
        for (int i = 1; i < pronum; i++) {
            completionTime[i] = completionTime[i - 1] + burstTime[i];
        }
        for (int i = 0; i < pronum; i++) {
            turnaroundTime[i] = completionTime[i] - arrivalTime[i];
            waitingTime[i] = turnaroundTime[i] - burstTime[i];
        }

        // for average waiting time
        for (int i = 0; i < pronum; i++) {
            awt += waitingTime[i];
        }

        // for average turnaround time
        for (int i = 0; i < pronum; i++) {
            att += turnaroundTime[i];
        }
        for (int i = 0; i < pronum; i++) {
            totbt += burstTime[i];
        }
        for (int i = 0; i < pronum; i++) {
            if (i == pronum - 1) {
                totct = completionTime[i];
            }
        }

        //gantt chart eme
        System.out.println("- - - - - - - - - - - - - - - G A N T T  C H A R T - - - - - - - - - - - - - - - - ");
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - -  - - - - - - - - - - - - - - - - ");
        if (arrivalTime[0] != 0) { //Idle at Start
            System.out.print("--" + "\t|");
        }
        for (int i = 0; i < pronum; i++) {
            System.out.print("P" + proId[i] + "\t" + "|");
        }
        System.out.println("");
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - -  - - - - - - - - - - - - - - - - -");
        System.out.print("");
        if (arrivalTime[0] != 0) { //Idle at Start
            System.out.print(0 + "\t");
        }
        
       
        for (int i = 0; i < pronum; i++) {
            if (i == 0) {
              System.out.print(arrivalTime[i]);
            }
            System.out.print("\t" + completionTime[i]);
        }

        //tableee
        System.out.println("\n");
        System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - -- - - - - - - T A B L E  - - - - - - - - - - - - - - - - - - - - - - -  - - - - - - - - ");
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
        System.out.println("Process" + " \t" + "Arrival Time" + "\t\t" + "Completion Time" + "\t\t" + "Turnaround Time" + "\t\t" + "Burst Time" + "\t" + "Waiting Time");
        for (int i = 0; i < pronum; i++) {
            System.out.println(proId[i] + " \t\t" + arrivalTime[i] + "\t\t\t" + completionTime[i] + "\t\t\t" + turnaroundTime[i] + "\t\t\t" + burstTime[i] + "\t\t"
                    + waitingTime[i]);
        }
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
        System.out.println("\nAverage waiting time: " + df.format(awt / pronum) + " ms");
        System.out.println("Average turmaround time: " + df.format(att / pronum) + " ms");
        System.out.println("CPU Utilization: " + df.format((totbt / totct) * 100) + "%");
    }

}
