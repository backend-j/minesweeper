package me.backendj.minesweeper;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

import static java.lang.String.format;

public class MineSweeper {
    public static final Random RANDOM = new Random();
    public static final int ROW_SIZE = 10;
    public static final int COL_SIZE = 10;
    public static final char MINE = 'X';
    public static final char BLOCK = '0';
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static int MINE_COUNT = 10;

    private Character[][] mineBoard;

    public MineSweeper() {
        mineBoard = new Character[ROW_SIZE][COL_SIZE];
        init();
        setMines();
    }

    public void print() {
        Arrays.stream(mineBoard).forEach(row -> {
            Arrays.stream(row).forEach(col -> System.out.print(format(" %s ", col)));
            System.out.println();
        });
    }

    private void init() {
        IntStream.range(ZERO, ROW_SIZE).forEach(row -> {
            int r = row;
            IntStream.range(ZERO, COL_SIZE).forEach(c -> setBlock(r, c));
        });
    }

    private void setMines() {
        while (MINE_COUNT-- > ZERO) {
            int rowIndex = RANDOM.nextInt(ROW_SIZE);
            int colIndex = RANDOM.nextInt(COL_SIZE);
            if (isMine(rowIndex, colIndex)) {
                continue;
            }
            setMine(rowIndex, colIndex);
            setMineCount(rowIndex, colIndex);
        }
    }

    private boolean isMine(int rowIndex, int colIndex) {
        return this.mineBoard[rowIndex][colIndex] == MINE;
    }

    private void setBlock(int rowIndex, int colIndex) {
        mineBoard[rowIndex][colIndex] = BLOCK;
    }

    private void setMine(int rowIndex, int colIndex) {
        mineBoard[rowIndex][colIndex] = MINE;
    }

    private void setMineCount(int rowIndex, int colIndex) {
        setNumber(rowIndex - ONE, colIndex - ONE);
        setNumber(rowIndex - ONE, colIndex);
        setNumber(rowIndex - ONE, colIndex + ONE);
        setNumber(rowIndex, colIndex - ONE);
        setNumber(rowIndex, colIndex + ONE);
        setNumber(rowIndex + ONE, colIndex - ONE);
        setNumber(rowIndex + ONE, colIndex);
        setNumber(rowIndex + ONE, colIndex + ONE);
    }

    private void setNumber(int rowIndex, int colIndex) {
        if (canCountMines(rowIndex, colIndex)) {
            mineBoard[rowIndex][colIndex]++;
        }
    }

    private boolean canCountMines(int rowIndex, int colIndex) {
        return isInRange(rowIndex, colIndex) && !isMine(rowIndex, colIndex);
    }

    private boolean isInRange(int rowIndex, int colIndex) {
        return !(rowIndex < ZERO || colIndex < ZERO || rowIndex >= ROW_SIZE || colIndex >= COL_SIZE);
    }
}
