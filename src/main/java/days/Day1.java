package days;

import utils.AocUtils;
import java.util.ArrayList;
import java.util.List;

public class Day1 implements DayInterface {

    AocUtils utils = new AocUtils();
    List<Integer> numbers = new ArrayList();

    public Day1() {

        numbers = utils.getIntegerListFromFile("Day1.txt");
        getAnswerA();
        getAnswerB();
    }


    public void getAnswerA() {
        int higher = 0;
        for( int i = 1; i < numbers.size(); i++) {
            if(numbers.get(i) > numbers.get(i-1))
                higher++;
        }
        System.out.println(higher);
    }

    public void getAnswerB() {
        int higher = 0;
        for(int i = 1; i < numbers.size() -2; i++) {
            int current = numbers.get(i) + numbers.get(i+1) + numbers.get(i+2);
            int previous = numbers.get(i-1) + numbers.get(i) + numbers.get(i+1);

            if(current > previous)
                higher++;
        }
        System.out.println(higher);
    }
}
