package com.lemmon714.decision.theory.algorithm;

import com.lemmon714.decision.theory.util.Matrix;

import java.util.concurrent.ThreadLocalRandom;

public class NashEquilibrium {

    private static final int MAX_STEP = 100;

    private final Matrix matrix;

    private int firstPlayerStrategy = 0;
    private int secondPlayerStrategy = 0;
    private int sameDecisionCounter = 0;
    private int step = 0;

    public NashEquilibrium(Matrix matrix) {
        this.matrix = matrix;
    }

    public void playRandom() {
        int firstPlayerScore = 0;
        int secondPlayerScore = 0;

        for (int i = 0; i < 1000; i++) {
            int firstPlayerStrategy = ThreadLocalRandom.current().nextInt(0, matrix.getStrategiesCount(0));
            int secondPlayerStrategy = ThreadLocalRandom.current().nextInt(0, matrix.getStrategiesCount(1));

            firstPlayerScore += matrix.get(0, firstPlayerStrategy, secondPlayerStrategy);
            secondPlayerScore += matrix.get(1, firstPlayerStrategy, secondPlayerStrategy);
        }
        System.out.println("Score: " + firstPlayerScore + ", " + secondPlayerScore);
    }

    public void compute() {

        while (step < MAX_STEP) {
            step++;

            play(0);
            play(1);

        }
        if (sameDecisionCounter > 10) {
            System.out.println("Nash Equilibrium: " + firstPlayerStrategy + "," + secondPlayerStrategy);
        } else {
            System.out.println("Nash Equilibrium is absent");
        }
    }

    private void play(int playerIndex) {
        int thisScore = matrix.get(playerIndex, firstPlayerStrategy, secondPlayerStrategy);

        for (int strategy = 0; strategy < matrix.getStrategiesCount(playerIndex); strategy++) {

            if (playerIndex == 0) {
                if (strategy == firstPlayerStrategy) {
                    continue;
                }
            } else {
                if (strategy == secondPlayerStrategy) {
                    continue;
                }
            }

            int nextScore;
            if (playerIndex == 0) {
                nextScore = matrix.get(playerIndex, strategy, secondPlayerStrategy);
            } else {
                nextScore = matrix.get(playerIndex, firstPlayerStrategy, strategy);
            }

            if (nextScore > thisScore) {

                if (playerIndex == 0) {
                    firstPlayerStrategy = strategy;
                } else {
                    secondPlayerStrategy = strategy;
                }

                sameDecisionCounter = 0;
            } else {
                sameDecisionCounter++;
            }
        }
    }

    public void checkZeroSum() {
        int sum = 0;
        for (int i = 0; i < matrix.getStrategiesCount(0); i++) {
            for (int j = 0; j < matrix.getStrategiesCount(1); j++) {
                sum += matrix.get(0, i, j);
                sum += matrix.get(1, i, j);
            }
        }
        if (sum == 0) {
            System.out.println("zero-sum game");
        } else {
            System.out.println("non zero-sum game");
        }
    }
}
