package sample.classes;

import java.util.ArrayList;

public class Queue {
    private ConfirmedQueue confirmedQueue = new ConfirmedQueue();
    private RejectedQueue rejectedQueue = new RejectedQueue();
    private MemoryScheduler memoryScheduler;
    private int start;

    public int getStart() {return start;}

    public Queue (){

    }

    public Queue(MemoryScheduler memoryScheduler){
        this.memoryScheduler = memoryScheduler;
    }

    public ArrayList getConfirmedProcesses(){
        return confirmedQueue.getConfirmedQueue();
    }

    public void addConfirmedProcess(Process process){
        confirmedQueue.add(process);
    }

    public void addRejectedProcess(Process process){
        rejectedQueue.add(process);
    }

    public ArrayList getRejectedProcesses(){
        return rejectedQueue.getRejectedQueue();
    }

    boolean add(Process process){
        ArrayList<Integer> listResults = new ArrayList<>();
        int min=0;
        for(int i = 0; i< memoryScheduler.getMemoryBlocks().size()-1; i++){
            if(MemoryBlock.byEnd.compare(memoryScheduler.getMemoryBlocks().get(i), memoryScheduler.getMemoryBlocks().get(i+1))>=process.getSize()){
                listResults.add(MemoryBlock.byEnd.compare(memoryScheduler.getMemoryBlocks().get(i), memoryScheduler.getMemoryBlocks().get(i+1)));
                min = MemoryBlock.byEnd.compare(memoryScheduler.getMemoryBlocks().get(i), memoryScheduler.getMemoryBlocks().get(i+1));
            }
        }

        for (int i:listResults) {
            if(i<min){
                min=i;
            }
        }

        boolean isConfirmed = false;
        for (int i = 0; i< memoryScheduler.getMemoryBlocks().size()-1; i++) {
            if(MemoryBlock.byEnd.compare(memoryScheduler.getMemoryBlocks().get(i), memoryScheduler.getMemoryBlocks().get(i+1))==min){
                this.start = memoryScheduler.getMemoryBlocks().get(i).getEnd();
                process.setTypeState(TypeOfProcess.READY);
                confirmedQueue.add(process);
                isConfirmed=true;
                break;
            }
        }
        if(!isConfirmed) {
            process.setTypeState(TypeOfProcess.REJECTED);
            rejectedQueue.add(process);
        }
        return isConfirmed;
    }
}
