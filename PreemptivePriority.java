package cpuschedulingalgorithms;

import java.text.DecimalFormat;
import java.util.*;

class PPrioProcess {

    int id;
    int at;
    int bt;
    int pr;
    int wt;
    int tat;
    int rt;
    int ct;

    public PPrioProcess(int pid, int arrivalTime, int burstTime, int priority) {
        this.id = pid;
        this.at = arrivalTime;
        this.bt = burstTime;
        this.pr = priority;
        this.wt = 0;
        this.tat = 0;
        this.rt = burstTime;
        this.ct = 0;
    }

    public int getId() {
        return id;
    }

    public int getArrivalTime() {
        return at;
    }

    public int getPriority() {
        return pr;
    }

    public int getRemainingTime() {
        return rt;
    }
    
    
    
}

public class PreemptivePriority {

    public PreemptivePriority() {
        System.out.println("\n------------------------Preemptive Priority CPU Scheduling Algorithm------------------------");

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = sc.nextInt();
        
        while(n <= 0 || n < 3 || n > 7){
            System.out.println("\nInvalid number of processes!");            
            if(n <= 0){
                System.out.println("\nNumber of processes cannot be less than or equal to 0!");
            }else if(n < 3)         {
                System.out.println("\nMinimun number of processes is 3!");
            }else if(n > 6){
                System.out.println("\nMaximum number of processes is 7!");
            }
            System.out.print("\nEnter the number of processes: ");
            n = sc.nextInt();
        }

        ArrayList<PPrioProcess> ps = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            System.out.println("\nProcess " + (i + 1));
            System.out.print("\tArrival Time: ");
            int arrivalTime = sc.nextInt();
            System.out.print("\tBurst Time: ");
            int burstTime = sc.nextInt();
            System.out.print("\tPriority: ");
            int priority = sc.nextInt();
            ps.add(new PPrioProcess((i + 1), arrivalTime, burstTime, priority));
        }

        Collections.sort(ps, (PPrioProcess p1, PPrioProcess p2) -> Integer.compare(p1.at, p2.at));

        ArrayList<PPrioProcess> rq = new ArrayList<>();

        int ct = 0, st = 0, et = 0;
        int ttat = 0;
        int twt = 0;
        double tbt = 0;

        int cp = 0;

        StringBuilder tl = new StringBuilder();
        StringBuilder pid = new StringBuilder();
        StringBuilder bt = new StringBuilder();
        StringBuilder t = new StringBuilder();

        PPrioProcess cps = null;

        while (cp != n) {
            int nat = Integer.MAX_VALUE;

            for (PPrioProcess p : ps) {
                if (p.at == ct) {
                    rq.add(p);
                } else if (p.at > ct) {
                    nat = Math.min(nat, p.at);
                }
            }

            if (rq.size() > 1) {
                 rq.sort(Comparator.comparing(PPrioProcess::getPriority)
                            .thenComparing(PPrioProcess::getArrivalTime).thenComparing(PPrioProcess::getId));
            }

            if (!rq.isEmpty()) {
                cps = rq.get(0);

                int timeExecution = nat - ct;
                if (cps.rt <= timeExecution) {
                    st = ct;

                    et = ct + cps.rt;                  

                    ct = et;

                    ps.get(ps.indexOf(cps)).rt = 0;

                    ps.get(ps.indexOf(cps)).ct = ct;
                    ps.get(ps.indexOf(cps)).tat = cps.ct - cps.at;
                    ps.get(ps.indexOf(cps)).wt = cps.tat - cps.bt;

                    rq.remove(cps);
                    
                    cp++;
                } else {
                    st = ct;

                    et = ct + timeExecution;

                    ct = et;

                    ps.get(ps.indexOf(cps)).rt -= timeExecution;                  
                }

                tl.append("---------");

                pid.append(" |   ").append("P").append(ps.get(ps.indexOf(cps)).id).append("   ");
                if (cp != n) {
                    t.append("     ").append(st);
                } else {
                    tl.append("---------");
                    pid.append("|");
                    t.append("      ").append(st).append("       ").append(et);
                    bt.append("---------");
                }

                if (st >= 10) {
                    t.append("   ");
                } else {
                    t.append("    ");
                }

                bt.append("---------");
            } else {
                tl.append("---------");

                pid.append(" |   ").append("--").append("   ");
                t.append("     ").append(ct);

                if (ct >= 10) {
                    t.append("  ");
                } else {
                    t.append("    ");
                }

                bt.append("---------");

                ct = nat;
            }
        }

        System.out.println("\nGantt Chart:");
        System.out.println(" " + tl.toString());
        System.out.println(pid.toString());
        System.out.println(" " + bt.toString());
        System.out.println(t.toString());

        Collections.sort(ps, new Comparator<PPrioProcess>() {
            @Override
            public int compare(PPrioProcess p1, PPrioProcess p2) {
                return Integer.compare(p1.id, p2.id);
            }
        });    
        
        System.out.println("\nProcess \tArrival Time\tBurst Time\tPriority\tCompletion Time\tTurnaround Time\tWaiting Time");
        for (PPrioProcess p : ps) {
            System.out.println(p.id + "\t\t" + p.at + "\t\t" + p.bt + "\t\t" + p.pr + "\t\t" + p.ct + "\t\t" + p.tat + "\t\t" + p.wt);
            ttat += p.tat;
            twt += p.wt;
            tbt += p.bt;
        }

        double avgTurnaroundTime = ttat / n;
        double avgWaitTime = twt / n;
        double cpuUtil = (tbt / (double) et) * 100;                
        
        DecimalFormat df = new DecimalFormat("#.##"); 
        System.out.println("\nAverage Turnaround Time: " + ttat + " / " + n + " = "+ df.format(avgTurnaroundTime) + " ms");
        System.out.println("Average Wait Time: " + ttat + " / " + n + " = " +  df.format(avgWaitTime) + " ms");
        
        System.out.println("\nCPU Utilization: "  + df.format (cpuUtil) + "%");
    }
}
