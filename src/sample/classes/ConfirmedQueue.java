package sample.classes;

import java.util.ArrayList;

public class ConfirmedQueue
{

    public ArrayList<Process> confirmedQueue;

    public ConfirmedQueue(){
        confirmedQueue = new ArrayList<>();
    }

    public ArrayList<Process> getConfirmedQueue() {
        return confirmedQueue;
    }

    public void add(Process process){
        confirmedQueue.add(process);
    }
}
