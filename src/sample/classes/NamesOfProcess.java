package sample.classes;

import java.util.ArrayList;
import java.util.Random;



public class NamesOfProcess
{
    private ArrayList<String> processNames = new ArrayList<>();
    private Random random = new Random();

    public NamesOfProcess(){
        processNames.add("P1");
        processNames.add("P2");
        processNames.add("P3");
        processNames.add("P4");
        processNames.add("P5");
    }

    public String getRandomProcessName(){
        int index = random.nextInt(processNames.size()-1);
        return this.processNames.get(index);
    }
}
