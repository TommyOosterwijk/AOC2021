package days;

public class BingoCard {

    BingoNumber[][] card = new BingoNumber[5][5];

    public boolean hasBingo() {

        for(int y = 0; y < 5; y++) {
            if(card[y][0].inUse && card[y][1].inUse && card[y][2].inUse && card[y][3].inUse && card[y][4].inUse) {
                return true;
            }
        }

        for(int x = 0; x < 5; x++) {
            if(card[0][x].inUse && card[1][x].inUse && card[2][x].inUse && card[3][x].inUse && card[4][x].inUse) {
                return true;
            }
        }
        return false;
    }

    public boolean hasNumber(String number) {
        for(int y= 0; y <5; y++) {
            for(int x= 0; x <5; x++) {
                if(number.equals(card[y][x].number)) {
                    card[y][x].inUse = true;
                    return true;
                }
            }
        }

        return false;
    }
}
