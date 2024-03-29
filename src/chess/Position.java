package chess;

public class Position {
    private final int y;
    private final int x;

    public Position(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public Position(Position pos) {
        this.y = pos.getY();
        this.x = pos.getX();
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

    public Position add(Position pos) {
        return new Position(this.getY() + pos.getY(), this.getX() + pos.getX());
    }

    public static Position getDiffDirection(Position diff) {
        int y = 0;
        int x = 0;

        if (diff.getY() > 0) {
            y = 1;
        } else if (diff.getY() < 0) {
            y = -1;
        }

        if (diff.getX() > 0) {
            x = 1;
        } else if (diff.getX() < 0) {
            x = -1;
        }

        return new Position(y, x);
    }
}
