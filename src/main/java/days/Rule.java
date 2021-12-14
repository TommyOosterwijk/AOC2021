package days;

public class Rule {

    String value;
    String addedChar;


    public String output1() {
        return value.charAt(0) + addedChar;
    }

    public String output2() {
        return addedChar + value.charAt(1);
    }
}
