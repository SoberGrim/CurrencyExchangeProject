package org.test.currency;

import java.util.*;

public class Queens {

    //поставить королеву на доску, пометить клетки которые она блокирует единицами
    public static void makeSquaresUnavailable(int queenX, int queenY, BitSet unavailableSquares) {
        for (int x = queenX; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if ((queenX == x)
                        || (queenY == y)
                        || (queenX - queenY) == (x - y)
                        || (queenX + queenY) == (x + y)) {
                    unavailableSquares.set(x * 8 + y);
                }
            }
        }
    }

    //конвертировать позиции королев из массива int[] в строку вида (1:1)(2:5)(3:8)(4:6)(5:3)(6:7)(7:2)(8:4)
    static String toSortedString(int[] queensArray) {
        StringBuilder str = new StringBuilder();
        for (int p : queensArray) {
            str.append(String.format("(%d:%d)", p / 10 + 1, p % 10 + 1));
        }
        return str.toString();
    }

    //текущие координаты королев на доске
    static int[] queenCoords = new int[8];
    //сколько раз новую фигуру ставили на доску
    static int figuresPlaced = 0;
    static int combinationNumber = 0;
    static boolean stopRecursion;

    //Попробовать поставить королеву №queenNum на доску в разных доступных местах
    public static void placeQueen(int queensPlaced, BitSet unavailableSquares) {
        int x = queensPlaced;

        for (int y = 0; y < 8 && !stopRecursion; y++) {
            if (!unavailableSquares.get(x * 8 + y)) {
                //мы нашли место, которое не блокируется ни одной королевой
                figuresPlaced++;
                //ставим фигуру
                queenCoords[queensPlaced] = x * 10 + y;

                //если королев меньше 8 попробовать поставить еще одну
                if (queensPlaced < 7) {
                    //комируем "доску"
                    BitSet newUnavailableSquares = (BitSet) unavailableSquares.clone();
                    //на копии ставим единицы туда, где текущая королева блокирует клетки
                    makeSquaresUnavailable(x, y, newUnavailableSquares);
                    placeQueen(queensPlaced + 1, newUnavailableSquares);
                } else {
                    //иначе напечатать удачную комбинацию
                    System.out.printf("[%d] %s%n", ++combinationNumber, toSortedString(queenCoords));
                    //stopRecursion = true;
                }
            }
        }
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        //начать ставить на доску королев
        System.out.println("nQueens combinations:");
        placeQueen(0, new BitSet(64));
        System.out.println("Figures placed: " + figuresPlaced);
        System.out.println("Time spent: " + (System.currentTimeMillis() - startTime) + " ms");
    }
}
