package com.lemmon714.decision.theory;

import java.util.Arrays;

public class BrownRobinsonMethodImpl {

    private static final double CONVERGENCE_DELTA = 0.01;
    private static final int MAX_STEP = 100;

    @SuppressWarnings("Duplicates")
    public int[] countFirstPlayerTotalVector(int[] firstPlayerVector, int[][] matrix) {
        int matrixRowCount = matrix.length;
        int matrixCellCount = matrix[0].length;

        int[] result = new int[matrixCellCount];

        for (int i = 0; i < matrixRowCount; i++) {
            for (int j = 0; j < matrixCellCount; j++) {
                result[j] = result[j] + matrix[i][j] * firstPlayerVector[i];
            }
        }
        return result;
    }

    @SuppressWarnings("Duplicates")
    public int[] countSecondPlayerTotalVector(int[] secondPlayerVector, int[][] matrix) {
        int matrixRowCount = matrix.length;
        int matrixCellCount = matrix[0].length;

        int[] result = new int[matrixRowCount];

        for (int j = 0; j < matrixCellCount; j++) {
            for (int i = 0; i < matrixRowCount; i++) {
                result[i] = result[i] + matrix[i][j] * secondPlayerVector[j];
            }
        }
        return result;
    }

    public Object[] isConverged(int[] firstPlayerResVector, int[] secondPlayerResVector, int[][] matrix, float prev, int iteration) {
        int[] secondPlayerTotal = countSecondPlayerTotalVector(secondPlayerResVector, matrix);

        float maxValue = Float.NEGATIVE_INFINITY;
        for (int i = 0; i < secondPlayerTotal.length; i++) {
            if (secondPlayerTotal[i] > maxValue) {
                maxValue = secondPlayerTotal[i];
            }

        }

        int[] firstPlayerTotal = countFirstPlayerTotalVector(firstPlayerResVector, matrix);

        float minValue = Float.POSITIVE_INFINITY;
        for (int j = 0; j < firstPlayerTotal.length; j++) {
            if (firstPlayerTotal[j] < minValue) {

                minValue = firstPlayerTotal[j];
            }
        }

        float high = maxValue / iteration;
        float low = minValue / iteration;
        float avg = (high - low) / 2;

        System.out.println(String.format("Step: %d, Higher: %8.4f, Lower: %8.4f, Avg: %8.4f", iteration, high, low, avg));
        System.out.println("player 1 results: " + Arrays.toString(firstPlayerResVector));
        System.out.println("player 2 results: " + Arrays.toString(secondPlayerResVector));

        boolean converged = Math.abs(prev - avg) < CONVERGENCE_DELTA;
        return new Object[]{converged, avg};
    }

    public int[] brownRobinsonStep(int[] firstPlayerResVector, int[] secondPlayerResVector, int[][] matrix) {
        int[] secondPlayerTotal = countSecondPlayerTotalVector(secondPlayerResVector, matrix);

        float maxValue = Float.NEGATIVE_INFINITY;
        int maxIndex = -1;
        for (int i = 0; i < secondPlayerTotal.length; i++) {
            if (secondPlayerTotal[i] > maxValue) {
                maxValue = secondPlayerTotal[i];
                maxIndex = i;
            }
        }
        firstPlayerResVector[maxIndex] += 1;

        int[] firstPlayerTotal = countFirstPlayerTotalVector(firstPlayerResVector, matrix);

        float minValue = Float.POSITIVE_INFINITY;
        int minIndex = -1;
        for (int j = 0; j < firstPlayerTotal.length; j++) {
            if (firstPlayerTotal[j] < minValue) {
                minValue = firstPlayerTotal[j];
                minIndex = j;
            }
        }
        secondPlayerResVector[minIndex] += 1;

        return new int[]{maxIndex, minIndex};
    }


    public int[] brownRobinson(int[][] matrix) {

        int matrixRowCount = matrix.length;
        int matrixCellCount = matrix[0].length;

        int[] firstPlayerResVector = new int[matrixRowCount];
        int[] secondPlayerResVector = new int[matrixCellCount];

        int minMaxIndex = getMinMaxIndex(matrix, matrixRowCount, matrixCellCount);
        firstPlayerResVector[minMaxIndex] = 1;

        int minIndex = getMinIndex(matrix, matrixCellCount, minMaxIndex);
        secondPlayerResVector[minIndex] = 1;

        int iteration = 1;
        int[] result = new int[2];
        float prev = Float.NEGATIVE_INFINITY;

        while (iteration < MAX_STEP) {
            Object[] convergence = isConverged(firstPlayerResVector, secondPlayerResVector, matrix, prev, iteration);
            prev = (float) convergence[1];
            if ((boolean) convergence[0]) {
                break;
            }
            iteration += 1;
            result = brownRobinsonStep(firstPlayerResVector, secondPlayerResVector, matrix);
        }

        System.out.println("======");
        if ((boolean) isConverged(firstPlayerResVector, secondPlayerResVector, matrix, prev, iteration)[0]) {
            return result;
        } else {
            return new int[]{-1, -1};
        }
    }

    public int getMinMaxIndex(int[][] matrix, int matrixRowCount, int matrixCellCount) {
        int minMaxIndex = 1;
        float minMax = Float.NEGATIVE_INFINITY;
        for (int i = 0; i < matrixRowCount; i++) {
            float minVal = Float.POSITIVE_INFINITY;
            int minIndex = -1;
            for (int j = 0; j < matrixCellCount; j++) {
                if (matrix[i][j] < minVal) {
                    minVal = matrix[i][j];
                    minIndex = i;
                }
            }
            if (minVal > minMax) {
                minMax = minVal;
                minMaxIndex = minIndex;
            }
        }
        return minMaxIndex;
    }

    public int getMinIndex(int[][] matrix, int matrixCellCount, int minMaxIndex) {
        float minVal = Float.POSITIVE_INFINITY;
        int minIndex = -1;
        for (int j = 0; j < matrixCellCount; j++) {
            if (matrix[minMaxIndex][j] < minVal) {
                minVal = matrix[minMaxIndex][j];
                minIndex = j;
            }
        }
        return minIndex;
    }


    public void solveAndPrint(int[][] matrix, String name) {
        System.out.println(name);
        int[] result = brownRobinson(matrix);
        System.out.println("---------");
        System.out.println("matrix: " + Arrays.deepToString(matrix));
        System.out.println("result: " + Arrays.toString(result));
        System.out.println("score: " + matrix[result[0]][result[1]]);
        System.out.println("--------");
    }

    public void perform() {
        solveAndPrint(matrix(new int[]{0, -1, 1}, new int[]{1, 0, -1}, new int[]{-1, 1, 0}), "Rock Paper Scissors");
        solveAndPrint(matrix(new int[]{0, -3}, new int[]{3, 0}), "Prisoner\'s Dilemma");
        solveAndPrint(matrix(new int[]{1, 2}, new int[]{3, 4}), "Custom matrix [2x2]");
        solveAndPrint(matrix(new int[]{1, 2, 3}, new int[]{3, 4, 3}, new int[]{5, 4, 3}), "Custom matrix [3x3]");
    }

    public int[][] matrix(int[]... arrays) {
        if (arrays.length == 0) {
            throw new IllegalArgumentException();
        }
        int[][] matrix = new int[arrays.length][arrays[0].length];
        System.arraycopy(arrays, 0, matrix, 0, arrays.length);
        return matrix;
    }

    public static void main(String... args) {
        new BrownRobinsonMethodImpl().perform();
    }
}