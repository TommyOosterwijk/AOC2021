package days;

import java.math.BigInteger;

public class Day21 implements DayInterface {
    BigInteger player1WinCounter = new BigInteger("0");
    BigInteger player2WinCounter = new BigInteger("0");

    int games = 1;

    int player1Score = 0;
    int player1Location = 10;

    int player2Score = 0;
    int player2Location = 7;

    int diceRolled = 0;
    int diceValue = 1;

    BigInteger[][][][] lookupTable = new BigInteger[31][10][31][10];


    boolean player1Turn =  true;

    public Day21() {
        System.out.println("Day21!");
        getAnswerA();
        getAnswerB();

        getAnswerBGood();
    }

    @Override
    public void getAnswerA() {
        while(true) {
            int roll = 0;
            for(int i = 0; i < 3; i++) {
                roll += diceValue;
                diceValue++;

                if(diceValue  == 101) {
                    diceValue = 1;
                }
            }
            if(diceValue  == 101) {
                diceValue = 1;
            }

            diceRolled += 3;

            if(player1Turn) {
                player1Location = ((player1Location+roll) % 10);
                if(player1Location == 0) {
                    player1Location = 10;
                }
                player1Score+= player1Location;
                player1Turn = false;
            } else {
                player2Location = ((player2Location+roll) % 10);
                if(player2Location == 0) {
                    player2Location = 10;
                }
                player2Score+= player2Location;
                player1Turn = true;

            }

            if(player1Score >= 1000) {
                System.out.println("A) " + (diceRolled*player2Score));
                break;
            } else if(player2Score >= 1000) {
                System.out.println("A) " + (diceRolled*player1Score));
                break;
            }
        }
    }

    @Override
    public void getAnswerB() {
        player1Score = 0;
        player1Location = 10;

        player2Score = 0;
        player2Location = 7;

        diceRolled = 0;
        diceValue = 1;

        player1Turn =  true;

        doRound(true, 1, 19, 8, 0);

        System.out.println("Games played: " + games);
        System.out.println("Player 1 won: " + player1WinCounter);
        System.out.println("Player 2 won: " + player2WinCounter);
    }

    // brute force! :D
    public void doRound(boolean player1, int location1, int score1, int location2, int score2) {
        int scoreToBeat =  21;
        if(score1 >=scoreToBeat) {
            player1WinCounter = player1WinCounter.add(new BigInteger("1"));
        } else if(score2 >= scoreToBeat) {
            player2WinCounter = player2WinCounter.add(new BigInteger("1"));
        } else {
            if (player1) {
                for(int i = 1; i <= 3; i++) {
                    for(int i2 = 1; i2 <= 3; i2++) {
                        for(int i3 = 1; i3 <= 3; i3++) {
                            int temp = (location1 + i + i2 + i3) % 10;
                            if (temp == 0) {
                                temp = 10;
                            }
                            doRound(false, temp, score1 + temp, location2, score2);
                        }
                    }
                }
            } else {
                for(int i = 1; i <= 3; i++) {
                    for(int i2 = 1; i2 <= 3; i2++) {
                        for(int i3 = 1; i3 <= 3; i3++) {
                            int temp = (location2 + i + i2 + i3) % 10;
                            if (temp == 0) {
                                temp = 10;
                            }
                            doRound(true, location1, score1, temp, score2+temp);
                        }
                    }
                }
            }
        }
    }

    public void getAnswerBGood() {
        initFirstSet();
        BigInteger one = new BigInteger("1");
        //Score first Player
        for(int i = 19; i >= 0; i--) {

            //Step player1
            for(int i2 = 0; i2 < 10; i2++) {

                //Score player 2
                for(int i3 = 20; i3 >= 0; i3--) {

                    //Step player2
                    for(int i4 = 0; i4 < 10; i4++) {

                        BigInteger value = new BigInteger("0");

                        int score = i;
                        int location =  i2+1;

                        int score2 = i3;
                        int location2 = i4+1;

                        for(int dice1 = 1; dice1 <= 3; dice1++) {
                            for(int dice2 = 1; dice2 <= 3; dice2++) {
                                for(int dice3 = 1; dice3 <= 3; dice3++) {
                                    int tempStep = (location + dice1 + dice2 + dice3) % 10;
                                    if (tempStep == 0) {
                                        tempStep = 10;
                                    }
                                    int tempScore = score + tempStep;

                                    if(tempScore >= 21) {
                                        value = value.add(one);
                                    } else {
                                        for(int player2Dice1 = 1; player2Dice1 <= 3; player2Dice1++) {
                                            for(int player2Dice2 = 1; player2Dice2 <= 3; player2Dice2++) {
                                                for(int player2Dice3 = 1; player2Dice3 <= 3; player2Dice3++) {
                                                    int player2TempStep = (location2 + player2Dice1 + player2Dice2 + player2Dice3) % 10;
                                                    if (player2TempStep == 0) {
                                                        player2TempStep = 10;
                                                    }
                                                    int player2TempScore = score2 + player2TempStep;

                                                    if(player2TempScore < 21) {
                                                       value = value.add(lookupTable[tempScore][tempStep-1][player2TempScore][player2TempStep-1]);
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }
                            }
                        }
                        lookupTable[i][i2][i3][i4] = value;
                    }
                }
            }
        }
        System.out.println("B) " + lookupTable[0][9][0][6]);
    }

    private void initFirstSet() {
        BigInteger init = new BigInteger("27");
        for(int x = 20; x < 31; x++) {
            for (int i = 0; i < lookupTable[0].length; i++) {
                for (int i2 = 0; i2 < lookupTable[0][i].length; i2++) {
                    for (int i3 = 0; i3 < lookupTable[0][i][i2].length; i3++) {
                        lookupTable[x][i][i2][i3] = init;
                    }
                }
            }
        }
    }
}
