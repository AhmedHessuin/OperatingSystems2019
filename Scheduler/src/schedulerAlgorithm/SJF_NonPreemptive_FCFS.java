package schedulerAlgorithm;

import dataStructure.PCB;
import dataStructure.Queue;
import scheduler.SchedulerSimulationController;

public class SJF_NonPreemptive_FCFS extends Queue implements ReadyQueue {

    @Override
    public void insert(PCB newPCB) {
    }

    @Override
    public void DrawGanttChart(SchedulerSimulationController ctrl) {
    }

    @Override
    public double getAvgWaitingTime() {
        return 0;
    }

    @Override
    public double getAvgTurnarroundTime() {
        return 0;
    }

}
