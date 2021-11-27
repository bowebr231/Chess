package chess;

public class Position {
    int y;
    int x;

    public Position(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean equals(Position pos) {
        boolean result = false;

        if (x == pos.getX() && y == pos.getY()) {
            result = true;
        }

        return result;
    }

    public Position getDifference(Position end) {
        int yDiff;
        int xDiff;

        yDiff = end.getY() - this.getY();
        xDiff = end.getX() - this.getX();

        return new Position(yDiff, xDiff);
    }
}
