package days;

import utils.AocUtils;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day7 implements DayInterface {
    AocUtils utils = new AocUtils();
    List<Integer> crabPositions;

    public Day7() throws FileNotFoundException, URISyntaxException {
        System.out.println("Day7");
        crabPositions = Arrays.stream(utils.getScannerFromFileName("Day7.txt").nextLine().split(",")).map(Integer::valueOf)
                .sorted()
                .collect(Collectors.toList());

        getAnswerA();
        long startTime = System.nanoTime();

        getAnswerB();
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println(totalTime);


        long startTime1 = System.nanoTime();

        getAnswerBFaster();
        long endTime1   = System.nanoTime();
        long totalTime1 = endTime1 - startTime1;
        System.out.println(totalTime1);
    }

    @Override
    public void getAnswerA() {
        int result = -1;
        for( int  i = crabPositions.get(0); i < crabPositions.get(crabPositions.size()-1); i++) {
            int counter = 0;
            for (Integer pos : crabPositions) {
                counter+= Math.abs(pos - i);
            }
            if(counter < result || result == -1) {
                result = counter;
            } else if(counter > result) {
                break;
            }

        }

        System.out.println("A: " + result);
    }

    @Override
    public void getAnswerB() {
        int result = -1;
        for( int  i = crabPositions.get(0); i < crabPositions.get(crabPositions.size()-1); i++) {
            int counter = 0;
            for (Integer pos : crabPositions) {
                counter+= stepCounter(Math.abs(pos - i));
            }
            if(counter < result || result == -1) {
                result = counter;
            }
        }

        System.out.println("B: " + result) ;
    }

    public void getAnswerBFaster() {
        int lowIndex = 0;
        int highIndex = crabPositions.get(crabPositions.size()-1);
        int targetIndex = highIndex / 2;
        int result = 0;

        boolean lowestStepsFound = false;

        while(!lowestStepsFound) {

            if(lowIndex +1 == highIndex) {
                int lowIndexSteps = fullStepCounter(lowIndex), highIndexSteps = fullStepCounter(highIndex);
                if(lowIndexSteps < highIndexSteps) {
                    result = lowIndexSteps;
                } else {
                    result = highIndexSteps;
                }

                lowestStepsFound = true;
            }

            if(fullStepCounter(targetIndex) > fullStepCounter(targetIndex+1)) {
                // going down, getting closer!
                lowIndex = targetIndex;

            } else {

                highIndex = targetIndex;
                //going up, we are to far.
            }
            targetIndex= Math.abs((highIndex - lowIndex)) / 2 + lowIndex;

        }
        // on my input, 10x faster then bruteforce B.
        System.out.println("B faster: " + result);
    }
    private int fullStepCounter(int index) {
        int result = 0;
        for (Integer pos : crabPositions) {
            result+= stepCounter(Math.abs(pos - index));
        }

        return result;
    }

    private int stepCounter(int steps) {
        return steps * (steps+1) / 2;
    }
}
