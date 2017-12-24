package com.lemmon714.decision.theory.util;

import com.lemmon714.decision.theory.Main;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Two player designed game matrix
 * */
public class Matrix {

    private final List<List<Pair<Integer>>> data;

    private Matrix(List<List<Pair<Integer>>> data) {
        this.data = data;
    }

    public int get(int playerIndex, int firstPlayerStrategy, int secondPlayerStrategy) {
        return data.get(secondPlayerStrategy).get(firstPlayerStrategy).get(playerIndex);
    }

    public int getStrategiesCount(int playerIndex) {
        if (playerIndex == 0) {
            return data.get(0).size();
        } else {
            return data.size();
        }
    }

    public static Matrix of(String fileName) throws IOException {

        ClassLoader classLoader = Main.class.getClassLoader();
        URL resource = Objects.requireNonNull(classLoader.getResource(fileName));
        File path = new File(resource.getFile());

        List<List<Pair<Integer>>> matrix = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                List<Pair<Integer>> row = new ArrayList<>();
                String[] pairs = line.split(" ");
                for (String pair : pairs) {
                    String[] split = pair.substring(1, pair.length() - 1).split(";");
                    row.add(new Pair<>(Integer.valueOf(split[0]), Integer.valueOf(split[1])));
                }
                matrix.add(row);
            }
        }
        return new Matrix(matrix);
    }
}
