package days;

import utils.AocUtils;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day4 implements DayInterface {
    AocUtils utils = new AocUtils();
    ArrayList<BingoCard> bingoCards = new ArrayList<>();
    String[] bingoNunmbers;

    public Day4() throws FileNotFoundException, URISyntaxException {
        System.out.println("Starting day 4");

        Scanner scanner = utils.getScannerFromFileName("Day4.txt");

        bingoNunmbers = scanner.nextLine().split(",");

        while (scanner.hasNextLine()) {
            String numberLine = scanner.nextLine();

            if(!numberLine.equals("")) {
                BingoCard bingoCard = new BingoCard();

                for(int i = 0; i < 5; i++) {
                    String[] bingoCardNumbers = numberLine.split(" ");
                    bingoCardNumbers = Arrays.stream(bingoCardNumbers).filter(string -> !string.equals(""))
                            .collect(Collectors.toList())
                            .toArray(String[]::new);

                    for(int x = 0; x < 5; x++) {
                        BingoNumber number = new BingoNumber();
                        number.number = bingoCardNumbers[x];
                        bingoCard.card[i][x] = number;
                    }

                    if(i != 4) {
                        numberLine = scanner.nextLine();
                    }
                }
                bingoCards.add(bingoCard);

            }
        }

        getAnswerA();
        getAnswerB();


//        for(int i = 0; i < bingoCards.size(); i++) {
//            for(int y = 0; y < 5; y++) {
//                for(int x = 0; x < 5; x++) {
//                    System.out.print(bingoCards.get(i).card[y][x].number + " ");
//                }
//                System.out.println();
//            }
//            System.out.println();
//        }
    }

    @Override
    public void getAnswerA() {
        boolean hasWinner = false;
        for(int i = 0; i < bingoNunmbers.length; i++) {
            String number = bingoNunmbers[i];
            for(int i2 = 0; i2 < bingoCards.size(); i2++) {
                BingoCard card = bingoCards.get(i2);
                if(!hasWinner) {
                    if (card.hasNumber(number)) {
                        if (card.hasBingo()) {
                            int sum = 0;
                            for (int y = 0; y < 5; y++) {
                                for (int x = 0; x < 5; x++) {
                                    if (!card.card[y][x].inUse) {
                                        sum += Integer.parseInt(card.card[y][x].number);
                                    }
                                }
                            }
                            System.out.println("A: " + sum * Integer.parseInt(number));
                            hasWinner = true;
                            break;
                        }
                    }
                }
            }
        }

    }

    @Override
    public void getAnswerB() {
        boolean winner = false;
        for(int i = 0; i < bingoNunmbers.length; i++) {
            String number = bingoNunmbers[i];
            for(int i2 = bingoCards.size(); i2 > 0; i2--) {
                BingoCard card = bingoCards.get(i2-1);
                if(!winner) {
                    if (card.hasNumber(number)) {
                        if (card.hasBingo()) {

                            if (bingoCards.size() == 1) {
                                int sum = 0;
                                for (int y = 0; y < 5; y++) {
                                    for (int x = 0; x < 5; x++) {
                                        if (!card.card[y][x].inUse) {
                                            sum += Integer.parseInt(card.card[y][x].number);
                                        }
                                    }
                                }
                                System.out.println("B: " + sum * Integer.parseInt(number));
                                winner = true;
                                break;
                            } else {
                                bingoCards.remove(card);
                            }
                        }
                    }
                }
            }
        }
    }
}
