package sample.classes;

import sample.Controller;

public class GeneratingOfProcess
{
    private int id=0;

    public void generate(Processes processes) {
        NamesOfProcess processNames = new NamesOfProcess();
        processes.getList().add(new Process(processNames.getRandomProcessName(), ++id));
        Controller.Refresh();
    }
}
