package game2048;

import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author TODO: Garrett Moore
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private final Board _board;
    /** Current score. */
    private int _score;
    /** Maximum score so far.  Updated when game ends. */
    private int _maxScore;
    /** True iff game is ended. */
    private boolean _gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public int[][] merged = new int[4][4];
    public Model(int size) {
        _board = new Board(size);
        _score = _maxScore = 0;
        _gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        _board = new Board(rawValues);
        this._score = score;
        this._maxScore = maxScore;
        this._gameOver = gameOver;
    }

    /** Same as above, but gameOver is false. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore) {
        this(rawValues, score, maxScore, false);
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     * */
    public Tile tile(int col, int row) {
        return _board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return _board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (_gameOver) {
            _maxScore = Math.max(_score, _maxScore);
        }
        return _gameOver;
    }

    /** Return the current score. */
    public int score() {
        return _score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return _maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        _score = 0;
        _gameOver = false;
        _board.clear();
        setChanged();
    }

    /** Allow initial game board to announce a hot start to the GUI. */
    public void hotStartAnnounce() {
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        _board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE.
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     */
    public void tilt(Side side) {
        _board.setViewingPerspective(side);
        for (int i = 0; i < 4; i++) {
            processColumn(i);
        }
        _board.setViewingPerspective(Side.NORTH);
        checkGameOver();
    }

    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    public void processColumn(int column) {
        /** exception handling, check w gsi */
        int value, j, move_y;
        for (int i = 3; i >= 0; i--) {
            if (_board.tile(column, i) == null) { /** board??? */
                value = 0;
                continue; /** expecting this to jump over values of 0 */
            }
            else {
                value = _board.tile(column, i).value();
            }
            j = i;
            move_y = i;
            while (j <= 3) {
                if (movePossible(value, column, j)) {
                    move_y = j;
                }
                j++;
            }
            if (move_y != i) {
                Tile t = _board.tile(column, i);
                if (_board.move(column, move_y, t)) {
                    updateScore(column, move_y);
                    merged[column][move_y] = 1;
                }
            }
        }
        merged = new int[4][4];
    }
    public boolean movePossible(int tval, int x, int y) {
        if ((_board.tile(x, y)) == null || ((_board.tile(x, y).value() == tval) && (merged[x][y] != 1))) {
            return true;
        }
        else {
            return false;
        }
    }
    public void updateScore(int x, int y) {
        _score += _board.tile(x, y).value();
    }
    private void checkGameOver() {
        _gameOver = checkGameOver(_board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     */
    public static boolean emptySpaceExists(Board b) {
        /** tile(int col, int row) and size() methods of the Board */
        int x = 4; /** consider using size(board) or a variant of it */
        for (int col = 0; col < x; col++) {
            for (int row = 0; row < x; row++) {
                /**System.out.println(b.tile(col, row)); */
                if (b.tile(col, row) == null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by this.MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        int x = 4; /** consider using size(board) or a variant of it */
        for (int col = 0; col < x; col++) {
            for (int row = 0; row < x; row++) {
                if ((b.tile(col, row) != null) && (b.tile(col, row).value() == MAX_PIECE)){
                    /** is tile not null and MAX_PIECE; examine line even if it works
                     * specifically examine why this.MAX_PIECE did not work but MAX_PIECE did
                     * */
                return true;
                }
            }
        }
        return false;
    }
    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        if (emptySpaceExists(b)) {
            return true;
        }
        /**middle tiles: (1,1), (1,2), (2,2), (2,1) */
        int[][] middle = new int[4][2];
        middle[0] = new int[]{1, 1};
        middle[1] = new int[]{1, 2};
        middle[2] = new int[]{2, 2};
        middle[3] = new int[]{2, 1};
        int value, value1, value2, value3, x, y;
        for (int i=0; i<4; i++) {
            value = b.tile(middle[i][0], middle[i][1]).value();
            if (middle[i][0] == 1) {
                x = 0;
            }
            else {
                x = 3;
            }
            if (middle[i][1] == 1) {
                y = 0;
            }
            else {
                y = 3;
            }
            value1 = b.tile(middle[i][0], y).value();
            value2 = b.tile(x, middle[i][1]).value();
            if ((value == value1) || (value == value2)) {
                return true;
            }
            else if ((i + 1 < 4) && (value == b.tile(middle[i+1][0], middle[i+1][1]).value())) { /**probably inefficient */
                return true;
            }
            else if ((i == 3) && (value == b.tile(middle[i -3][0], middle[i - 3][1]).value())) { /**probably inefficient */
                return true;
            }
        }
        x = 1;
        y = 0;
        int last_value;
        last_value = b.tile(x, y).value();
        while (!(x ==0 && y==0)) {
            if (x < 3 && y == 0) {
                x++;
            } else if (x == 3 && y < 3) {
                y++;
            } else if (x > 0 && y == 3) {
                x = x - 1;
            } else if (x == 0 && y > 0) {
                y = y - 1;
             }
            value = b.tile(x, y).value();
            if (value == last_value) {
                return true;
            }
            last_value = value;
        }
        return false;
    }

    /** Returns the model as a string, used for debugging. */
    @Override
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    /** Returns whether two models are equal. */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    /** Returns hash code of Model’s string. */
    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
