package days;

import utils.Utils;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day1 {

    ArrayList<Integer> numbers = new ArrayList();

    public Day1() throws FileNotFoundException, URISyntaxException {
        getAnswerA();
        getAnswerB();
    }
    Utils utils = new Utils();


    public void getAnswerA() throws FileNotFoundException, URISyntaxException {
        int higher = 0;
        Scanner scanner = utils.getScannerFromFileName("Day1.txt");
        int target = scanner.nextInt();

        while (scanner.hasNextInt()) {
            int tempInt = scanner.nextInt();
            if(tempInt > target) {
                higher++;
            }
            target = tempInt;

        }

        System.out.println(higher);
    }

    public void getAnswerB() throws FileNotFoundException, URISyntaxException {
        int higher = 0;
        Scanner scanner = utils.getScannerFromFileName("Day1.txt");

        while (scanner.hasNextInt()) {
            numbers.add(scanner.nextInt());
        }

        for(int i = 1; i < numbers.size() -2; i++) {
            int current = numbers.get(i) + numbers.get(i+1) + numbers.get(i+2);
            int previous = numbers.get(i-1) + numbers.get(i) + numbers.get(i+1);

            if(current > previous) {
                higher++;
            }
        }

        System.out.println(higher);
    }
}
