package days;

import utils.AocUtils;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day23 implements DayInterface {
    long minStepCounter = -1;
    List<String> leastSteps = new ArrayList<>();
    AocUtils utils = new AocUtils();
    String[] index = {"A", "B", "C", "D"};
    int[] stepValue = {1, 10, 100, 1000};

    public Day23() throws FileNotFoundException, URISyntaxException {
        System.out.println("Day23!");

        List<String> strings = new ArrayList<>();
        Scanner scanner = utils.getScannerFromFileName("day23.txt");
        while(scanner.hasNextLine()) {
            strings.add(scanner.nextLine());
        }

        List<Amphipods> amphipodsList = new ArrayList<>();
        boolean[] hall = new boolean[11];

        for(int x= 0; x < hall.length; x++) {
            hall[x] = false;
        }

        boolean[][] finishedRoom = new boolean [strings.size()][strings.get(0).length()];
        boolean[][] room = new boolean [strings.size()][strings.get(0).length()];

        for(int y =0; y < room.length; y++) {
            for(int x= 0; x < room[y].length; x++) {
                room[y][x] = true;
                finishedRoom[y][x] = false;
            }
        }

        for(int y = 0; y < strings.size(); y++) {
            String str = strings.get(y);
            for (int x = 0; x < str.length(); x++) {
                String s = str.charAt(x) + "";
                for (int indexId = 0; indexId < index.length; indexId++) {
                    if (index[indexId].equals(s)) {
                        Amphipods amphipods = new Amphipods(indexId, stepValue[indexId], y, x);

                        //This is only true in the example and prod data but not ALWAYS. if lvl up would also be finished it would not detect.
                        if(indexId == x && y == strings.size()-1) {
                            amphipods.finished = true;
                            finishedRoom[y][x] = true;
                        }
                        amphipodsList.add(amphipods);
                        break;
                    }
                }
            }
        }
        playTheGame(amphipodsList, 0, hall, room, finishedRoom, new ArrayList<>());
        //playGame(amphipodsList, 0, hall, room, new ArrayList<>());
        getAnswerA();
    }

    void playTheGame(List<Amphipods> amphipodsList, long score, boolean[] hall, boolean[][] room, boolean[][] finishedRoom, List<String> steps) {
        if(!(score > minStepCounter && minStepCounter != -1)) {
            if (isFinished(finishedRoom)) {
                if (minStepCounter > score || minStepCounter == -1) {
                    minStepCounter = score;
                    leastSteps = steps;
                }
            } else {
                // lets PLAY
                // if a amphipods can return to finish spot, its the only action to prefent extra steps.
                boolean amphipodsFinished = false;
                for (int i = 0; i < amphipodsList.size(); i++) {
                    if(!amphipodsFinished) {
                        Amphipods amphipods = amphipodsList.get(i);

                        // in hall
                        if (!amphipods.finished && !amphipods.inRoom) {

                            int xHallEntry = 2 + (amphipods.roomIndex * 2);
                            if (isHallPathClear(amphipods.x, xHallEntry, hall)) {
                                int stepsTaken = Math.abs(xHallEntry - amphipods.x);

                                int highestOpenSpot = finishedRoom.length - 1;
                                boolean roomClear = true;

                                for (int y = 0; y < finishedRoom.length; y++) {
                                    if (!finishedRoom[y][amphipods.roomIndex]) {
                                        if (room[y][amphipods.roomIndex]) {
                                            roomClear = false;
                                            break;
                                        }
                                        highestOpenSpot = y;
                                    }
                                }

                                if (roomClear) {
                                    List<Amphipods> tempList = new ArrayList<>();
                                    for (int d = 0; d < amphipodsList.size(); d++) {
                                        tempList.add(new Amphipods(amphipodsList.get(d)));
                                    }
                                    Amphipods tempAmphipods = tempList.get(i);

                                    boolean[] tempHall = new boolean[11];
                                    for (int tempI = 0; tempI < hall.length; tempI++) {
                                        tempHall[tempI] = hall[tempI];
                                    }
                                    boolean[][] tempRoom = new boolean[room.length][room[0].length];
                                    for (int tempY = 0; tempY < room.length; tempY++) {
                                        for (int tempX = 0; tempX < room[tempY].length; tempX++) {
                                            tempRoom[tempY][tempX] = room[tempY][tempX];
                                        }
                                    }

                                    boolean[][] tempFinishedRoom = new boolean[finishedRoom.length][finishedRoom[0].length];
                                    for (int tempY = 0; tempY < finishedRoom.length; tempY++) {
                                        for (int tempX = 0; tempX < finishedRoom[tempY].length; tempX++) {
                                            tempFinishedRoom[tempY][tempX] = finishedRoom[tempY][tempX];
                                        }
                                    }

                                    List<String> newSteps = new ArrayList<>(steps);


                                    tempHall[tempAmphipods.x] = false;
                                    tempRoom[highestOpenSpot][tempAmphipods.roomIndex] = true;
                                    tempFinishedRoom[highestOpenSpot][tempAmphipods.roomIndex] = true;
                                    tempAmphipods.finished = true;
                                    amphipodsFinished = true;

                                    long tempScore = score + ((highestOpenSpot + 1 + stepsTaken) * tempAmphipods.stepValue);
                                    String step = "Going from hall " + tempAmphipods.x + " to finalRoom = " + highestOpenSpot+" , " + tempAmphipods.roomIndex+ " with value " + ((highestOpenSpot + 1 + stepsTaken) * tempAmphipods.stepValue);
                                    newSteps.add(step);
                                    playTheGame(tempList, tempScore, tempHall, tempRoom, tempFinishedRoom, newSteps);
                                }
                            }
                        }
                    }
                }

                if(!amphipodsFinished){
                    for (int i = 0; i < amphipodsList.size(); i++) {
                        Amphipods amphipods = amphipodsList.get(i);

                        if (!amphipods.finished && amphipods.inRoom ) {
                            int xHallEntry = 2 + (amphipods.x * 2);
                            boolean roomPathClear = true;
                            if(hall[xHallEntry]) {
                                roomPathClear = false;
                            }
                            else {
                                for (int wayY = amphipods.y - 1; wayY >= 0; wayY--) {
                                    if (room[wayY][amphipods.x] == true) {
                                        roomPathClear = false;
                                        break;
                                    }
                                }
                            }
                            if (roomPathClear) {
                                for (int x = 0; x < hall.length; x++) {
                                    if (x != 2 && x != 4 && x != 6 && x != 8) {
                                        if (isPathClear(x, xHallEntry, hall)) {

                                            List<Amphipods> tempList = new ArrayList<>();
                                            for (int d = 0; d < amphipodsList.size(); d++) {
                                                tempList.add(new Amphipods(amphipodsList.get(d)));
                                            }
                                            Amphipods tempAmphipods = tempList.get(i);

                                            boolean[] tempHall = new boolean[11];
                                            for (int tempI = 0; tempI < hall.length; tempI++) {
                                                tempHall[tempI] = hall[tempI];
                                            }
                                            boolean[][] tempRoom = new boolean[room.length][room[0].length];
                                            for (int tempY = 0; tempY < room.length; tempY++) {
                                                for (int tempX = 0; tempX < room[tempY].length; tempX++) {
                                                    tempRoom[tempY][tempX] = room[tempY][tempX];
                                                }
                                            }
                                            List<String> newSteps = new ArrayList<>(steps);
                                            tempRoom[tempAmphipods.y][tempAmphipods.x] = false;
                                            tempAmphipods.inRoom = false;
                                            String step = "Going from room " + tempAmphipods.y + " - " + tempAmphipods.x + " to hall x = " + x + " with value " + ((tempAmphipods.y + 1 + Math.abs(x - xHallEntry)) * tempAmphipods.stepValue);

                                            tempAmphipods.x = x;
                                            tempHall[x] = true;

                                            long tempScore = score + ((tempAmphipods.y + 1 + Math.abs(x - xHallEntry)) * tempAmphipods.stepValue);
                                            newSteps.add(step);
                                            playTheGame(tempList, tempScore, tempHall, tempRoom, finishedRoom, newSteps);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    boolean isHallPathClear(int firstStep, int lastStep, boolean[] hall) {
        int startX = firstStep + 1;
        int endX = lastStep;

        if(firstStep > lastStep) {
            startX = lastStep;
            endX = firstStep -1;
        }
        for (int xCheck = startX; xCheck <= endX; xCheck++) {
            if (hall[xCheck]) {
                return false;
            }
        }
        return true;
    }

    boolean isPathClear(int firstStep, int lastStep, boolean[] hall) {
        int startX = Math.min(firstStep, lastStep);
        int endX = Math.max(firstStep, lastStep);
        for (int xCheck = startX; xCheck <= endX; xCheck++) {
            if (hall[xCheck]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void getAnswerA() {
        System.out.println("A) = " + minStepCounter);

        for( String s : leastSteps) {
            System.out.println(s);
        }
    }

    @Override
    public void getAnswerB() {

    }

    private boolean isFinished(boolean[][] finishedRoom) {
        for(int y = 0; y < finishedRoom.length; y++) {
            for(int x = 0; x < finishedRoom[y].length; x++) {
                if(!finishedRoom[y][x]) {
                    return false;
                }
            }
        }
        return true;
    }
}

class Amphipods {

    int roomIndex;
    int stepValue;
    boolean inRoom = true;
    int x;
    int y;
    boolean finished = false;

    public Amphipods(int roomIndex, int stepValue, int y, int x) {
        super();
        this.roomIndex = roomIndex;
        this.stepValue = stepValue;
        this.x =  x;
        this.y = y;
    }

    public Amphipods(Amphipods amphipods) {
        super();
        this.roomIndex = amphipods.roomIndex;
        this.stepValue = amphipods.stepValue;
        this.x =  amphipods.x;
        this.y = amphipods.y;
        this.inRoom = amphipods.inRoom;
        this.finished = amphipods.finished;
    }

    @Override
    public String toString() {
        return "Amphipods roomIndex " + roomIndex + " and stepvalue " + stepValue + " y,x " + y + "," + x + " inRoom " + inRoom + " and is finished " + finished;
    }
}
