package com.lemmon714.decision.theory;

import com.lemmon714.decision.theory.algorithm.NashEquilibrium;
import com.lemmon714.decision.theory.util.Matrix;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Main {

    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));
    private static final Map<String, String> INPUT_FILES = new HashMap<String, String>() {{
        put("1", "Prisoners-dilemma.txt");
        put("2", "Rock-paper-scissors.txt");
        put("3", "Custom.txt");
    }};

    public static void main(String... args) throws IOException {
        while (true) {
            start();
        }
    }

    private static void start() throws IOException {

        System.out.println("1 - Prisoner's dilemma");
        System.out.println("2 - Rock–paper–scissors");
        System.out.println("3 - Custom Game");

        Optional<String> fileName = Optional.ofNullable(INPUT_FILES.get(READER.readLine()));
        Matrix matrix = Matrix.of(fileName.orElseThrow(IllegalArgumentException::new));

        Map<String, Runnable> commands = new HashMap<String, Runnable>() {{
            put("1", () -> new NashEquilibrium(matrix).compute());
            put("2", () -> new NashEquilibrium(matrix).playRandom());
            put("3", () -> new NashEquilibrium(matrix).checkZeroSum());
        }};

        System.out.println("1 - Nash Equilibrium");
        System.out.println("2 - Random Game");
        System.out.println("3 - Check whether zero-sum Game or not");

        Optional.ofNullable(commands.get(READER.readLine()))
                .orElseThrow(IllegalArgumentException::new)
                .run();

        System.out.println("\n======\n");
    }
}
