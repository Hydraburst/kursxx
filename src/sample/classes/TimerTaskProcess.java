package sample.classes;

import sample.Controller;

import java.util.TimerTask;

public class TimerTaskProcess  extends TimerTask {
    private Processes processes;
    private GeneratingOfProcess generateProcesses = new GeneratingOfProcess();


    public TimerTaskProcess(Processes processes){
        this.processes = processes;
    }

    @Override
    public void run() {
        generateProcesses.generate(processes);
        Controller.Refresh();
    }
}
