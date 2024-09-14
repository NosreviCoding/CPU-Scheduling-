package cpuschedulingalgorithms;

import java.text.DecimalFormat;
import java.util.*;

class NPProcess {

    int pid; // process number 
    int bt; // burst time
    int at; // arrival time
    int priority; // priority
    int wt; // waiting time
    int tat; // turnaround time
    int ct; // completion time

    public NPProcess(int pid, int bt, int at, int priority) {
        this.pid = pid;
        this.bt = bt;
        this.at = at;
        this.priority = priority;
        this.wt = 0;
        this.tat = 0;
        this.ct = 0;
    }
}

public class NonPreemptivePriority {

    public NonPreemptivePriority(){
        Scanner sc = new Scanner(System.in);

        boolean test = true;
        do {
            System.out.println("\n-----------------------NonPreemptive Priority CPU Scheduling Algorithm----------------------");
            System.out.println("--------------------------");
            System.out.println("!!MIN = 3 & MAX 6 PROCESS!!");
            System.out.println("--------------------------");
            System.out.print("Enter the number of processes: ");
            int no_pro = sc.nextInt();
            if (no_pro > 6 || no_pro < 3) {
                System.out.println("Invalid!");
                test = false;
            } else {

                NPProcess[] processes = new NPProcess[no_pro];
                for (int i = 0; i < no_pro; i++) {
                    System.out.println("Process NUMBER: " + (i + 1));
                    System.out.print("\tArrival time " + (i + 1) + ": ");
                    int at = sc.nextInt();
                    System.out.print("\tBurst time " + (i + 1) + ": ");
                    int bt = sc.nextInt();
                    System.out.print("\tPriority time " + (i + 1) + ": ");
                    int priority = sc.nextInt();

                    processes[i] = new NPProcess(i + 1, bt, at, priority);

                }

                //inputed table
                System.out.println();
                System.out.println("----------------------------------------");
                System.out.println("|     Process   | Arrival Time | Burst Time | PRIORITY |");
                for (int i = 0; i < no_pro; i++) {
                    System.out.printf("|     p%d     |     %d     |     %d     |     %d     |\n",
                           processes[i].pid ,processes[i].at, processes[i].bt, processes[i].priority);
                }
                System.out.println("----------------------------------------");
                System.out.println();

                // SORTING PART
                Arrays.sort(processes, new Comparator<NPProcess>() {
                    @Override
                    public int compare(NPProcess p1, NPProcess p2) {
                        if (p1.at == p2.at) {
                            return Integer.compare(p1.priority, p2.priority);
                        }
                        return Integer.compare(p1.at, p2.at);
                    }
                });

                int currentTime = processes[0].at;
                int totalWaitingTime = 0;
                int totalTurnaroundTime = 0;

                //GANTT CHART
                System.out.println("Gantt Chart:");
                int previousCT = 0;
                for (int i = 0; i < no_pro; i++) {
                    System.out.print("---------");
                }
                System.out.println();

                boolean hasProcessArrivingAtZero = false;
                for (int i = 0; i < no_pro; i++) {
                    if (processes[i].at == 0) {
                        hasProcessArrivingAtZero = true;
                        break;
                    }
                }

                if (!hasProcessArrivingAtZero) {
                    // if there is no process arriving at time zero, print "IDLE" before printing the first process
                    System.out.print("| IDLE ");
                }

                System.out.print("|");

                for (int i = 0; i < no_pro; i++) {
                    System.out.print(" P" + processes[i].pid + "  |");
                }
                System.out.println();
                for (int i = 0; i < no_pro; i++) {
                    System.out.print("---------");
                }
                System.out.println();

                for (int i = 0; i < no_pro; i++) {

                    if (i == 0) {
                        if (!hasProcessArrivingAtZero) {
                            System.out.print("0\t" + processes[0].at);
                            previousCT = processes[0].at;
                        } else {
                            System.out.print(processes[0].at);
                            previousCT = processes[0].at;
                        }

                        while (processes[i].at > currentTime) {
                            System.out.print(previousCT);
                            System.out.print("\t");
                            currentTime++;
                        }

                    }
                    processes[i].wt = currentTime - processes[i].at;
                    totalWaitingTime += processes[i].wt;
                    currentTime += processes[i].bt;
                    processes[i].tat = currentTime - processes[i].at;
                    totalTurnaroundTime += processes[i].tat;
                    processes[i].ct = currentTime; // Update completion time
                    System.out.print("\t" + currentTime);
                }

                System.out.println();

                System.out.println("\nTable:");
                System.out.println("----------------------------------------------------------------------------------------------------");
                System.out.println("| PROCESS | BURST Time | ARRIVAL Time | PRIORITY | COMPLETION Time | TURNAROUND Time | WAITING Time |");
                System.out.println("----------------------------------------------------------------------------------------------------");
                for (int i = 0; i < no_pro; i++) {
                    System.out.printf("|     P%d     |     %d     |     %d     |     %d     |       %d         |       %d      |      %d    | \n",
                            processes[i].pid, processes[i].bt, processes[i].at, processes[i].priority, processes[i].ct,
                            processes[i].tat, processes[i].wt);
                }
                System.out.println("----------------------------------------------------------------------------------------------------");

                int totalBurstTime = 0;
                for (int i = 0; i < no_pro; i++) {
                    totalBurstTime += processes[i].bt;
                }
                double cpuUtilization = (totalBurstTime / (double) processes[no_pro - 1].ct) * 100;
                double avgWaitingTime = (double) totalWaitingTime / no_pro;
                double avgTurnaroundTime = (double) totalTurnaroundTime / no_pro;

                DecimalFormat df = new DecimalFormat("#.##");

                System.out.println("Formulas: ");
                System.out.println("TurnAround Time: CT - AT");
                System.out.println("Waiting Time: BT - TAT");
                System.out.println("AverageWaitingTime: total waiting time / number of process.");
                System.out.println("Average TurnAround Time: total turn around time / number of process.");
                System.out.println("---------------------------------------");

                System.out.println("\nAverage Waiting Time: " + totalWaitingTime + "/" + no_pro + " " + "= " + df.format(avgWaitingTime) + " ms");
                System.out.println("Average Turnaround Time: " + totalTurnaroundTime + "/" + no_pro + " " + "= " + df.format(avgTurnaroundTime) + " ms");
                System.out.println("CPU Utilization: " + df.format(cpuUtilization) + "%");

                System.out.println("---------------------------------------");

                System.out.println("Do you want to continue? (y/n)");
                String choice = sc.next();
                if (choice.equalsIgnoreCase("n")) {
                    test = false;
                }
            }
        } while (test == true);
    }
}
