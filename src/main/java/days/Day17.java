package days;

public class Day17 implements DayInterface {

    int minX = 48, maxX = 70, minY = -148, maxY = -189;
    int resultCounter = 0;

    public Day17() {
        System.out.println("Day17!");
        getAnswerA();
        getAnswerB();
    }

    @Override
    public void getAnswerA() {
        int heightValue = 0;

        for(int y = maxY; y <= Math.abs(maxY); y++) {
            for (int x = 0; x <= maxX; x++) {

                int height = 0, width = 0;
                int maxHeight = 0;
                int stepY = y, stepX = x;


                boolean going = true;
                while (going) {
                    height += stepY;
                    if (height > maxHeight) {
                        maxHeight = height;
                    }
                    width += stepX;
                    stepY--;

                    if (stepX > 0) {
                        stepX--;
                    }

                    if (height <= minY && height >= maxY && width >= minX && width <= maxX) {
                        going = false;
                        if (heightValue < maxHeight) {
                            heightValue = maxHeight;
                        }
                        resultCounter++;
                    }

                    if (height < maxY || width > maxX) {
                        going = false;
                    }
                }
            }
        }

        System.out.println("A) " + heightValue);
    }

    @Override
    public void getAnswerB() {
        System.out.println("B) " + resultCounter);
    }
}
