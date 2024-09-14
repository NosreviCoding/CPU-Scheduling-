package cpuschedulingalgorithms;

import java.util.*;
import java.text.*;

public class CPUSchedulingAlgorithms { 
    
    static public void menu(){
        Scanner sc = new Scanner(System.in);
        
        System.out.println("\n-----------------------------CPU Scheduling Algorithm Simulator-----------------------------");
        System.out.println("\n|1| First Come First Serve (FCFS)");
        System.out.println("\n|2| Shortest Job First (SJF)");
        System.out.println("\n|3| Non-Preemptive Priority");
        System.out.println("\n|4| Round Robin");
        System.out.println("\n|5| Shortest Remaining Time First (SRTF)");
        System.out.println("\n|6| Preemptive Priority");
        System.out.println("\n|0| Exit");
        System.out.print("\nEnter Choice: ");
        int choice = sc.nextInt();
        
        switch (choice) {
            case 0:
                return;
                
            case 1:
                new FirstComeFirstServe();
                menu();
                break;
                
            case 2:
                new ShortestJobFirst();
                menu();
                break;
            case 3:                
                new NonPreemptivePriority();
                menu();
                break;
                
            case 4:
                new RoundRobin();
                menu();
                break;
                
            case 5:
                new ShortestRemainingTimeFirst();
                menu();
                break;
                
            case 6:
                new PreemptivePriority();
                menu();
                break;
            default:
                menu();
                break;
        }  
    }
    
    public static void main(String[] args) {
        menu();
    }
    
}
