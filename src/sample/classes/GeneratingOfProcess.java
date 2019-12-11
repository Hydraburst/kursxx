package sample.classes;

import sample.Controller;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;

public class GeneratingOfProcess {
    private int id = 0;


    public void generate(Processes processes) {
        NamesOfProcess processNames = new NamesOfProcess();
        processes.getList().add(new Process(processNames.getRandomProcessName(), ++id));

        ArrayList<Process> tempProcesses = new ArrayList<>();
        for (int i = 0; i < processes.getList().size() / 2; i++) {
            tempProcesses.add(processes.getList().get(i));
        }
        tempProcesses.sort(Comparator.comparingInt(Process::getPriority));
        for (int i = 0; i < tempProcesses.size(); i++) {
            processes.getList().set(i, tempProcesses.get(i));

            //processes.getList().sort(processes.bySort);
            Controller.Refresh();

        }
    }
}
