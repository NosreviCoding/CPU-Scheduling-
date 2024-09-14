package cpuschedulingalgorithms;

import java.text.DecimalFormat;
import java.util.*;

class Process {

    int pid;
    int arrivalTime;
    int burstTime;
    int completionTime;
    int turnaroundTime;
    int waitingTime;
    int remainingTime;

    public Process(int pid, int arrivalTime, int burstTime) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }
}

public class ShortestRemainingTimeFirst {

    public ShortestRemainingTimeFirst() {
        Scanner sc = new Scanner(System.in);
        Scanner y = new Scanner(System.in);
        int ny;
        boolean yn = false;

        do {
            int minProcess = 3;
            int maxProcess = 6;
            int n = 0;
            System.out.println("\n----------------Shortest Remaining Time First (SRTF) CPU Scheduling Algorithm---------------");
            System.out.println("");

            while (true) {
                System.out.print("Enter the number of processes: ");
                n = sc.nextInt();
                System.out.println("");

                if (n >= minProcess && n <= maxProcess) {
                    break;
                } else {
                    System.out.println("\nInvalid input!!!. Please enter a number between " + minProcess + " and " + maxProcess + ".\n");
                }
            }

            ArrayList<Process> processes = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                System.out.println("Enter details for process " + (i + 1) + ": ");
                System.out.print("\tArrival time: ");
                int arrivalTime = sc.nextInt();
                System.out.print("\tBurst time: ");
                int burstTime = sc.nextInt();
                processes.add(new Process(i + 1, arrivalTime, burstTime));
            }
            System.out.println("");
            System.out.println("-------------------------------------------");
            System.out.println("Process\t\tArrival Time\tBurst Time");
            for (Process p : processes) {
                System.out.println("P" + p.pid + "\t\t" + p.arrivalTime + "\t\t" + p.burstTime);
            }
            System.out.println("-------------------------------------------");
            ArrayList<Process> completedProcesses = new ArrayList<>();
            int currentTime = 0;
            System.out.println();
            System.out.println("Gantt Chart: ");
            System.out.println("----------------------------------------------------------------------------------");

            boolean isIdle = false;
            int lastCompletionTime = 0;
            int totalExecutionTime = 0;
            int totalIdleTime = 0;

            int totalTurnaroundTime = 0;
            int totalWaitingTime = 0;

            while (!processes.isEmpty()) {
                Process currentProcess = null;
                int shortestRemainingTime = Integer.MAX_VALUE;
                for (Process p : processes) {
                    if (p.arrivalTime <= currentTime && p.remainingTime < shortestRemainingTime) {
                        shortestRemainingTime = p.remainingTime;
                        currentProcess = p;
                    } else if (p.arrivalTime <= currentTime && p.remainingTime == shortestRemainingTime) {
                        if (p.arrivalTime < currentProcess.arrivalTime) {
                            currentProcess = p;
                        }
                    }
                }

                if (currentProcess == null) {
                    if (!isIdle) {
                        System.out.print("|     IDLE      ");
                        isIdle = true;
                        totalIdleTime++;
                    }
                    currentTime++;
                    lastCompletionTime = currentTime;
                } else {
                    isIdle = false;
                    currentProcess.remainingTime--;
                    currentTime++;
                    totalExecutionTime++;
                    if (currentProcess.remainingTime == 0) {
                        currentProcess.completionTime = currentTime;
                        currentProcess.turnaroundTime = currentProcess.completionTime - currentProcess.arrivalTime;
                        currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;
                        completedProcesses.add(currentProcess);
                        processes.remove(currentProcess);
                        System.out.print("|\tP" + currentProcess.pid + "\t");
                        lastCompletionTime = currentTime;

                        totalTurnaroundTime += currentProcess.turnaroundTime;
                        totalWaitingTime += currentProcess.waitingTime;
                    }
                }
            }

            System.out.println("|");
            System.out.println("----------------------------------------------------------------------------------");

            int startTime = Integer.MAX_VALUE;
            for (Process p : completedProcesses) {
                if (p.arrivalTime < startTime) {
                    startTime = p.arrivalTime;
                }
            }
            if (totalIdleTime > 0) {
                System.out.print("0\t\t");
            }
            System.out.print(startTime + "\t");

            for (Process p : completedProcesses) {
                System.out.print("\t" + p.completionTime + "\t");
                lastCompletionTime = p.completionTime;
            }
            System.out.println("");
            System.out.println("");

            System.out.print("------------------------------------------------------------------------------------------------------------");
            System.out.println("\nProcess\t\tArrival Time\tBurst Time\tCompletion Time\t\tTurnaround Time\t\tWaiting Time");
            for (Process p : completedProcesses) {
                System.out.println("P" + p.pid + "\t\t" + p.arrivalTime + "\t\t" + p.burstTime + "\t\t" + p.completionTime + "\t\t\t" + p.turnaroundTime + "\t\t\t" + p.waitingTime);
            }
            System.out.print("------------------------------------------------------------------------------------------------------------");
            System.out.println("");
            System.out.println("");

            float avgTurnaroundTime = 0, avgWaitingTime = 0;
            for (Process p : completedProcesses) {
                avgTurnaroundTime += p.turnaroundTime;
                avgWaitingTime += p.waitingTime;
            }

            System.out.println("Number of Processes: " + n);
            System.out.println("Last Completion Time: " + lastCompletionTime);
            System.out.println("Total Burst Time: " + totalExecutionTime);
            System.out.println("Total Turnaround Time: " + totalTurnaroundTime);
            System.out.println("Total Waiting Time: " + totalWaitingTime);

            avgTurnaroundTime = (float) totalTurnaroundTime / completedProcesses.size();
            avgWaitingTime = (float) totalWaitingTime / completedProcesses.size();
            System.out.println("\nAverage Turnaround Time: " + totalTurnaroundTime + " / " + n + " = " + avgTurnaroundTime + " ms");
            System.out.println("Average Waiting Time: " + totalWaitingTime + " / " + n + " = " + avgWaitingTime + " ms");

            
             DecimalFormat df = new DecimalFormat("#.##"); 

            float cpuUtilization = (float) totalExecutionTime / completedProcesses.get(completedProcesses.size() - 1).completionTime * 100;
            System.out.println("CPU Utilization: (" + totalExecutionTime + " / " + completedProcesses.get(completedProcesses.size() - 1).completionTime + ") * 100% = " + df.format(cpuUtilization) + "%");

            System.out.println("");
            System.out.print("Try Again the SRTF? [1] Yes, [0] No: ");
            ny = y.nextInt();
            if (ny == 1) {
                yn = true;
            } else if (ny == 0) {
                yn = false;
            }
        } while (yn);
    }
}
