package days;

import utils.Utils;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Scanner;

public class Day2 {

    int positionA = 0;
    int depthA = 0;
    int positionB = 0;
    int depthB = 0;
    int aim = 0;

    public Day2() throws FileNotFoundException, URISyntaxException {

        Scanner scanner = utils.getScannerFromFileName("Day2.txt");
        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split(" ");
            updatePosition(line[0], Integer.parseInt(line[1]));
            updatePositionB(line[0], Integer.parseInt(line[1]));
        }

        getAnswerA();
        getAnswerB();

    }
    Utils utils = new Utils();

    public void updatePosition(String dir, int step) {
        if(dir.equals("forward")) {
            positionA+= step;
        } else if(dir.equals("down")) {
            depthA+= step;
        } else {
            depthA-= step;
        }
    }

    public void updatePositionB(String dir, int step) {
        if(dir.equals("forward")) {
            positionB+= step;
            depthB+= aim * step;

        } else if(dir.equals("down")) {
            aim+= step;
        } else if(dir.equals("up")) {
            aim-= step;
        }
    }

    public void getAnswerA() {
        System.out.println(positionA * depthA);
    }

    public void getAnswerB() {
        System.out.println(positionB * depthB);
    }
}
