package sample.classes;

import sample.Controller;

import java.util.ArrayList;
import java.util.Comparator;

public class Processes {

    private MemoryScheduler memoryScheduler;

    public ArrayList<Process> getList(){return list;}

    private ArrayList<Process> list;
    public Queue queue;

    public static Comparator<Process> bySort = new Comparator<Process>() {
        @Override
        public int compare(Process o1, Process o2) {
            return o1.priority > o2.priority? 1: o1.priority < o2.priority ? -1 : 0 ;
        }
    };
    public Processes(MemoryScheduler memoryScheduler){
        this.list = new ArrayList<>();
        this.memoryScheduler = memoryScheduler;
    }




    public void PriorityCheck(){
        Process runningProcess = null;

        for (Process process:getList()) {
            if(process.getTypeState().equals(TypeOfProcess.RUNNING)){
                runningProcess = process;
            }

            if(runningProcess!=null) {
                if (process.getId() < runningProcess.getId()) {
                    getList().get(getList().indexOf(runningProcess)).setTypeState(TypeOfProcess.WAITING);
                    try {
                        toWork(process);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public Process processCheckType(){
        int minPriority=31;
        Process currentProcess = null;
        for (Process process:getList()) {
            if(process.getPriority()<minPriority&&!process.getTypeState().equals(TypeOfProcess.REJECTED)&&!process.getTypeState().equals(TypeOfProcess.TERMINATED)){
                minPriority=process.getPriority();
                currentProcess = process;
            }
        }
        return currentProcess;
    }


    public void Work() throws InterruptedException {
        Process currentProcess = processCheckType();
        queue = new Queue(memoryScheduler);
        if(currentProcess!=null) {
            if (queue.add(currentProcess)) {
                toWork(currentProcess);
                queue.addConfirmedProcess(currentProcess);
            } else {
                getList().get(getList().indexOf(currentProcess)).setTypeState(TypeOfProcess.REJECTED);
                queue.addRejectedProcess(currentProcess);
            }
        }
    }

    private void toWork(Process process) throws InterruptedException {
        getList().get(getList().indexOf(process)).setTypeState(TypeOfProcess.RUNNING);
        MemoryBlock memoryBlock = new MemoryBlock(process.getId(), queue.getStart() + 1, queue.getStart() + process.getSize()+1);
        memoryScheduler.add(memoryBlock);
        memoryScheduler.getMemoryBlocks().sort(MemoryBlock.byAsc);
        Controller.Refresh();
        Thread.sleep(process.getTime()*100);
        process.setTypeState(TypeOfProcess.TERMINATED);
        memoryScheduler.deleteBlock(memoryBlock);
        Controller.Refresh();
    }
}
