package schedulerAlgorithm;

import dataStructure.Node;
import dataStructure.PCB;
import dataStructure.Queue;
import scheduler.SchedulerSimulationController;

public class FCFS extends Queue implements ReadyQueue {

    private int totalBurstTime = 0;
    private int totalwaitingTime = 0;
    private int totalTurnaroundtime = 0;
    private int noofProcesses = 0;

    @Override
    public void insert(PCB newPCB) {
        Node newNode = new Node(newPCB);

        if (head == null) {
            enqueue(newPCB);
        } else {
            Node traverse_node = head;

            if (newNode.getPcb().getArrivalTime() < traverse_node.getPcb().getArrivalTime()) {
                newNode.setNext(head);
                head = newNode;
            } else {
                while (traverse_node.getNext() != null) {
                    if (newNode.getPcb().getArrivalTime() < traverse_node.getNext().getPcb().getArrivalTime()) {
                        newNode.setNext(traverse_node.getNext());
                        traverse_node.setNext(newNode);
                        break;
                    }
                    traverse_node = traverse_node.getNext();
                }
                if (traverse_node.getNext() == null) {
                    enqueue(newPCB);
                }
            }
        }
    }

    @Override
    public void DrawGanttChart(SchedulerSimulationController ctrl) {
        prepare();
        
        Node currNode = head;

        // Traverse through the LinkedList 
        while (currNode != null) {
            // Print the data at current node
            if (ctrl.getCurrentTime() < currNode.getPcb().getArrivalTime()) {
                ctrl.drawIdleProcess((currNode.getPcb().getArrivalTime() - ctrl.getCurrentTime()));
            }
            ctrl.draw(currNode.getPcb().getBurstTime(), currNode.getPcb().getPID(), currNode.getPcb().getColor());
            noofProcesses++;
            // Go to next node 
            totalBurstTime += currNode.getPcb().getBurstTime();
            totalTurnaroundtime += (ctrl.getCurrentTime() - currNode.getPcb().getArrivalTime());
            currNode = currNode.getNext();
        }
        totalwaitingTime = totalTurnaroundtime - totalBurstTime;
        ctrl.writeAvgWaitingTime((totalwaitingTime / (double) noofProcesses));
        ctrl.writeAvgTurnarroundTime((totalTurnaroundtime / (double) noofProcesses));
    }

    private void prepare() {
        noofProcesses = 0;
        totalBurstTime = 0;
        totalwaitingTime = 0;
        totalTurnaroundtime = 0;
    }

    @Override
    public void edit(PCB pcb) {
        delete(pcb);
        insert(pcb);
    }

    @Override
    public void delete(PCB pcb) {
        if (head.getPcb().equals(pcb)) {
            head = head.getNext();
            if (head == null) {
                tail = null;
            }
        } else {
            Node traverseNode = head;
            while (traverseNode.getNext() != null) {
                if (traverseNode.getNext().getPcb().equals(pcb)) {
                    if (traverseNode.getNext().getNext() == null) {
                        traverseNode.setNext(null);
                        tail = traverseNode;
                    } else {
                        traverseNode.setNext(traverseNode.getNext().getNext());
                    }
                    break;
                }
                traverseNode = traverseNode.getNext();
            }
        }
    }
}
