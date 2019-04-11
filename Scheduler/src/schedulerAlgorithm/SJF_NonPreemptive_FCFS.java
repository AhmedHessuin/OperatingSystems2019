package schedulerAlgorithm;

import dataStructure.Node;
import dataStructure.PCB;
import dataStructure.Queue;
import scheduler.SchedulerSimulationController;

public class SJF_NonPreemptive_FCFS extends Queue implements ReadyQueue {

    @Override
        public void insert(PCB newPCB) {
        Node newNo = new Node(newPCB);
        Node ptr=head;
        Node pre_ptr=head;
        int flag=0;
        int bru_T;
        
        
        if (head == null) {
            head = newNo;
            tail = newNo;
            newNo.setNext(null);
        }
        
        else if(head==tail){
            if(head.getPcb().getArrivalTime() == newNo.getPcb().getArrivalTime()){
                if(head.getPcb().getBurstTime() <= newNo.getPcb().getBurstTime()){
                    enqueue(newPCB);
                    tail.setNext(null);
                }
                else{
                    newNo.setNext(head);
                    head=newNo;
                }
            }
            else if(head.getPcb().getArrivalTime() < newNo.getPcb().getArrivalTime()){
                enqueue(newPCB);
                tail.setNext(null);
            }
            else{
                newNo.setNext(head);
                head=newNo;
            }
        }
        else{
            bru_T = ptr.getPcb().getBurstTime();
            while(ptr.getNext() !=null){
                if(ptr.getPcb().getArrivalTime() == newNo.getPcb().getArrivalTime()){
                    if(ptr.getPcb().getBurstTime()<=newNo.getPcb().getBurstTime()){
                        newNo.setNext(ptr.getNext());
                        ptr.setNext(newNo);
                        flag=1;
                        ptr=head;
                        break;
                    }
                    else{
                        newNo.setNext(ptr);
                        pre_ptr.setNext(newNo);
                        flag=1;
                        ptr=head;
                        break;
                    }
                }
                pre_ptr=ptr;
                ptr=ptr.getNext();
            }
            while(flag==0){
                if(newNo.getPcb().getArrivalTime()<= bru_T){
                   if(ptr.getPcb().getArrivalTime()<= bru_T){
                       if(newNo.getPcb().getBurstTime() < ptr.getPcb().getBurstTime()){
                           newNo.setNext(ptr);
                           pre_ptr.setNext(newNo);
                           break;
                       }
                       else{
                            newNo.setNext(ptr.getNext());
                            ptr.setNext(newNo);
                            break;
                       }
                   }
                   else{
                        newNo.setNext(ptr);
                        pre_ptr.setNext(newNo);
                        break;
                   }
                }
                pre_ptr=ptr;
                if(ptr.getNext() == null){
                    enqueue(newPCB);
                    tail.setNext(null);
                    flag=1;
                }
                ptr=ptr.getNext();
                bru_T = bru_T + ptr.getPcb().getBurstTime();
            }
        }
    }
    @Override
    public void DrawGanttChart(SchedulerSimulationController ctrl) {
    }

    @Override
    public void edit(PCB PCB) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(PCB pcb) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
