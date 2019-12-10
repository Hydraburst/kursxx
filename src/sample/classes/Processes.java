package sample.classes;

import sample.Controller;

import java.util.ArrayList;

public class Processes {

    private MemoryScheduler memoryScheduler;

    public ArrayList<Process> getList(){return list;}

    private ArrayList<Process> list;
    private Queue queue;

    public Processes(MemoryScheduler memoryScheduler){
        this.list = new ArrayList<>();
        this.memoryScheduler = memoryScheduler;
    }

    

    public Process CheckPriority(){
        Process runningProcess = null;

        for (Process process:getList()) {
            if(process.getTypeState().equals(TypeOfProcess.RUNNING)){
                runningProcess = process;
            }

           return runningProcess;
        }
        return null;
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
