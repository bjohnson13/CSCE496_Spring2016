import java.util.*;

/**
 * Created by bricejohnson on 2/1/16.
 */

public class GeneticAlgorithm
{
    public static void main(String[] args)
    {
        //
        int lengthOfWordToMatch          = global.getWordLength();
        Date date                        = new Date();
        Random randomNumber              = new Random(date.getTime());
        Map<Integer, Integer> fitnessMap = new HashMap<>(); // Row, fitness
        Integer[] top8IndexArr           = new Integer[8];
        Integer[] top8FitnessArr         = new Integer[8];
        int popultionSize                = 16;
        int[][] initialGeneration        = new int[popultionSize * 2][lengthOfWordToMatch];
        int[][] nextGeneration           = new int[popultionSize][lengthOfWordToMatch];
        int[][] currentGeneration        = new int[popultionSize][lengthOfWordToMatch];
        int generationNumber             = 0;
        int mutationRate                 = 5; // Fitness1: 5 (1.8% mutation rate)
        int wordFitness, i, j;
        int mutationX;
        int mutationY;
        int crossoverPoint;

        // ============================================
        // ============================================
        // Choose which fitness function to run
        // 1 - Distance
        // 2 - Does the character match
        int CHOOSE_FITNESS_FUNCTION = 2;
        int MAX_NUM_OF_GENERATIONS = 1000000;
        // Choose to run random generation algorithm: 1-yes, 0-no
        int RANDOM_GENERATIONS = 0;
        // Choose which experiment to run: 1-Original 2-Change Crossover Point 3-Change Mutation
        int EXPERIMENTING_ALGORITHMS = 1;
        // ============================================
        // ============================================


        if(RANDOM_GENERATIONS == 1)
        {
            randomGenerations();
        } else {
            // Randomly initialize Generation 0
            for (i = 0; i < 32; i++) {
                for (j = 0; j < lengthOfWordToMatch; j++) {
                    initialGeneration[i][j] = randomNumber.nextInt(57) + 65; // Characters in ASCII: A(65) - z(122)
                }
                if (CHOOSE_FITNESS_FUNCTION == 1) {
                    wordFitness = fitness1(initialGeneration[i]);
                } else {
                    wordFitness = fitness2(initialGeneration[i]);
                }
                fitnessMap.put(i, wordFitness);
                //printASCIItoString(initialGeneration[i]);
            }

            // Choose 8 best
            fitnessMap = sortByValue(fitnessMap);
            for (i = 0; i < 8; i++) {
                top8IndexArr[i] = fitnessMap.entrySet().iterator().next().getKey();
                top8FitnessArr[i] = fitnessMap.get(top8IndexArr[i]);
                fitnessMap.remove(top8IndexArr[i]);
                //System.out.println("Index: " + top8IndexArr[i] + " Fitness: " + top8FitnessArr[i]);
            }
            fitnessMap.clear();

            //System.out.println("Generation " + generationNumber);
            //System.out.println("Best Fitness: " + top8FitnessArr[0]);
            System.out.println(top8FitnessArr[0]);
            //printASCIItoString(initialGeneration[top8IndexArr[0]]);

            // Crossover top 8, create next generation
            generationNumber++;
            if(EXPERIMENTING_ALGORITHMS == 2)
            {
                for (i = 0; i < 8; i++) {
                    System.arraycopy(initialGeneration[top8IndexArr[i]], 0, nextGeneration[i], 0, lengthOfWordToMatch);
                }
                for (i = 8; i < 10; i++) {
                    crossoverPoint = randomNumber.nextInt(lengthOfWordToMatch);
                    System.arraycopy(nextGeneration[i - 8], 0, nextGeneration[i], 0, crossoverPoint);
                    if(lengthOfWordToMatch % 2 == 0) {
                        System.arraycopy(nextGeneration[i - 6], (lengthOfWordToMatch / 2), nextGeneration[i], (lengthOfWordToMatch / 2), (lengthOfWordToMatch / 2));
                    } else {
                        System.arraycopy(nextGeneration[i - 6], (lengthOfWordToMatch / 2), nextGeneration[i], (lengthOfWordToMatch / 2), (lengthOfWordToMatch / 2) + 1);
                    }
                }
                for (i = 12; i < 14; i++) {
                    crossoverPoint = randomNumber.nextInt(lengthOfWordToMatch);
                    System.arraycopy(nextGeneration[i - 8], 0, nextGeneration[i], 0, crossoverPoint);
                    if(lengthOfWordToMatch % 2 == 0) {
                        System.arraycopy(nextGeneration[i - 6], (lengthOfWordToMatch / 2), nextGeneration[i], (lengthOfWordToMatch / 2), (lengthOfWordToMatch / 2));
                    } else {
                        System.arraycopy(nextGeneration[i - 6], (lengthOfWordToMatch / 2), nextGeneration[i], (lengthOfWordToMatch / 2), (lengthOfWordToMatch / 2) + 1);
                    }
                }
            } else {
                for (i = 0; i < 8; i++) {
                    System.arraycopy(initialGeneration[top8IndexArr[i]], 0, nextGeneration[i], 0, lengthOfWordToMatch);
                }
                for (i = 8; i < 10; i++) {
                    crossoverPoint = randomNumber.nextInt(lengthOfWordToMatch);
                    System.arraycopy(nextGeneration[i - 8], 0, nextGeneration[i], 0, crossoverPoint);
                    System.arraycopy(nextGeneration[i - 6], crossoverPoint, nextGeneration[i], crossoverPoint, (lengthOfWordToMatch - crossoverPoint));
                }
                for (i = 12; i < 14; i++) {
                    crossoverPoint = randomNumber.nextInt(lengthOfWordToMatch);
                    System.arraycopy(nextGeneration[i - 8], 0, nextGeneration[i], 0, crossoverPoint);
                    System.arraycopy(nextGeneration[i - 6], crossoverPoint, nextGeneration[i], crossoverPoint, (lengthOfWordToMatch - crossoverPoint));
                }
            }

            // Mutation
            for (i = 0; i < mutationRate; i++)
            {
                mutationX = randomNumber.nextInt(lengthOfWordToMatch);
                mutationY = randomNumber.nextInt(popultionSize);
                nextGeneration[mutationY][mutationX] = randomNumber.nextInt(57) + 65;
            }

            // Calculate fitness
            for (i = 0; i < popultionSize; i++) {
                if (CHOOSE_FITNESS_FUNCTION == 1) {
                    wordFitness = fitness1(nextGeneration[i]);
                } else {
                    wordFitness = fitness2(nextGeneration[i]);
                }
                fitnessMap.put(i, wordFitness);
            }

            // Choose 8 best
            fitnessMap = sortByValue(fitnessMap);
            for (i = 0; i < 8; i++) {
                top8IndexArr[i] = fitnessMap.entrySet().iterator().next().getKey();
                top8FitnessArr[i] = fitnessMap.get(top8IndexArr[i]);
                fitnessMap.remove(top8IndexArr[i]);
                //System.out.println("Index: " + top8IndexArr[i] + " Fitness: " + top8FitnessArr[i]);
            }
            fitnessMap.clear();

            //System.out.println("--------------");
            //System.out.println("Generation " + generationNumber);
            //System.out.println("Best Fitness: " + top8FitnessArr[0]);
            System.out.println(top8FitnessArr[0]);
            //printASCIItoString(nextGeneration[top8IndexArr[0]]);

// ====================================================================================================================================================================================
// ====================================================================================================================================================================================
// ====================================================================================================================================================================================
// ====================================================================================================================================================================================

            while ((generationNumber != MAX_NUM_OF_GENERATIONS) && (top8FitnessArr[0] != 0)) {
                generationNumber++;
                // ============================================
                // ============================================
                // Info to print for each generation
                //System.out.println("Generation " + generationNumber);     // Generation Number
                //printASCIItoString(nextGeneration[top8IndexArr[0]]);      // Best matching String
                System.out.println(top8FitnessArr[0]); // Best fitness
                // ============================================
                // ============================================

                // Crossover top 8
                if(EXPERIMENTING_ALGORITHMS == 2)
                {
                    for (i = 0; i < 8; i++) {
                        System.arraycopy(nextGeneration[top8IndexArr[i]], 0, currentGeneration[i], 0, lengthOfWordToMatch);
                    }
                    for (i = 8; i < 10; i++) {
                        crossoverPoint = randomNumber.nextInt(lengthOfWordToMatch);
                        System.arraycopy(nextGeneration[i - 8], 0, nextGeneration[i], 0, crossoverPoint);
                        if(lengthOfWordToMatch % 2 == 0) {
                            System.arraycopy(currentGeneration[i - 6], (lengthOfWordToMatch / 2), currentGeneration[i], (lengthOfWordToMatch / 2), (lengthOfWordToMatch / 2));
                        } else {
                            System.arraycopy(currentGeneration[i - 6], (lengthOfWordToMatch / 2), currentGeneration[i], (lengthOfWordToMatch / 2), (lengthOfWordToMatch / 2) + 1);
                        }
                    }
                    for (i = 12; i < 14; i++) {
                        crossoverPoint = randomNumber.nextInt(lengthOfWordToMatch);
                        System.arraycopy(nextGeneration[i - 8], 0, nextGeneration[i], 0, crossoverPoint);
                        if(lengthOfWordToMatch % 2 == 0) {
                            System.arraycopy(currentGeneration[i - 6], (lengthOfWordToMatch / 2), currentGeneration[i], (lengthOfWordToMatch / 2), (lengthOfWordToMatch / 2));
                        } else {
                            System.arraycopy(currentGeneration[i - 6], (lengthOfWordToMatch / 2), currentGeneration[i], (lengthOfWordToMatch / 2), (lengthOfWordToMatch / 2) + 1);
                        }
                    }
                } else {
                    for (i = 0; i < 8; i++) {
                        System.arraycopy(nextGeneration[top8IndexArr[i]], 0, currentGeneration[i], 0, lengthOfWordToMatch);
                    }
                    for (i = 8; i < 10; i++) {
                        crossoverPoint = randomNumber.nextInt(lengthOfWordToMatch);
                        System.arraycopy(currentGeneration[i - 8], 0, currentGeneration[i], 0, crossoverPoint);
                        System.arraycopy(currentGeneration[i - 6], crossoverPoint, currentGeneration[i], crossoverPoint, (lengthOfWordToMatch - crossoverPoint));
                    }
                    for (i = 12; i < 14; i++) {
                        crossoverPoint = randomNumber.nextInt(lengthOfWordToMatch);
                        System.arraycopy(currentGeneration[i - 8], 0, currentGeneration[i], 0, crossoverPoint);
                        System.arraycopy(currentGeneration[i - 6], crossoverPoint, currentGeneration[i], crossoverPoint, (lengthOfWordToMatch - crossoverPoint));
                    }
                }

                // Mutation
                if(EXPERIMENTING_ALGORITHMS == 3) {
                    if (top8FitnessArr[0] < 10) {
                        mutationRate = 3;
                    } else if (top8FitnessArr[0] < 5) {
                        mutationRate = 1;
                    }
                }
                for (i = 0; i < mutationRate; i++) {
                    mutationX = randomNumber.nextInt(lengthOfWordToMatch);
                    mutationY = randomNumber.nextInt(popultionSize);
                    currentGeneration[mutationY][mutationX] = randomNumber.nextInt(57) + 65;
                }

                // Calculate fitness
                for (i = 0; i < popultionSize; i++) {
                    for (j = 0; j < lengthOfWordToMatch; j++) {
                        nextGeneration[i][j] = currentGeneration[i][j];
                    }
                    if (CHOOSE_FITNESS_FUNCTION == 1) {
                        wordFitness = fitness1(nextGeneration[i]);
                    } else {
                        wordFitness = fitness2(nextGeneration[i]);
                    }
                    fitnessMap.put(i, wordFitness);
                }

                // Choose 8 best
                fitnessMap = sortByValue(fitnessMap);
                for (i = 0; i < 8; i++) {
                    top8IndexArr[i] = fitnessMap.entrySet().iterator().next().getKey();
                    top8FitnessArr[i] = fitnessMap.get(top8IndexArr[i]);
                    fitnessMap.remove(top8IndexArr[i]);
                    //System.out.println("Index: " + top8IndexArr[i] + " Fitness: " + top8FitnessArr[i]);
                }
                fitnessMap.clear();
            }
            System.out.println("--------------");
            System.out.println("Generation " + generationNumber);
            System.out.println("Best Fitness: " + top8FitnessArr[0]);
            System.out.println("Word to Match: " + global.word);
            System.out.print("Word Found:    ");
            printASCIItoString(nextGeneration[top8IndexArr[0]]);
        }

    }

    public static int fitness1(int[] word)
    {
        int[] wordToMatch = stringToASCII(global.word);
        int lengthOfWord = wordToMatch.length;
        int fitness = 0;
        int number, i;

        for(i = 0; i < lengthOfWord; i++)
        {
            number  = wordToMatch[i] - word[i];
            fitness = fitness + (number * number);
        }

        return fitness;
    }

    public static int fitness2(int[] word)
    {
        int[] wordToMatch = stringToASCII(global.word);
        int lengthOfWord = wordToMatch.length;
        int fitness = 0, i;

        for(i = 0; i < lengthOfWord; i++)
        {
            if(wordToMatch[i] != word[i])
            {
                fitness++;
            }
        }

        return fitness;
    }

    public static void printASCIItoString(int[] asciiArray)
    {
        String word = "";

        for(int i = 0; i < asciiArray.length; i++)
        {
            word = word + (char)asciiArray[i];
        }

        System.out.println(word);
    }

    public static int[] stringToASCII(String word)
    {
        int length = global.getWordLength();
        int[] asciiWord = new int[length];
        int a = 0;

        for(int i = 0; i < length; i++)
        {
            asciiWord[i] = (int) word.charAt(i);
        }

        return asciiWord;
    }

    public static void randomGenerations()
    {
        int[] wordToMatch                = stringToASCII(global.word);
        int lengthOfWordToMatch          = wordToMatch.length;
        int[] randomWord                 = new int[lengthOfWordToMatch];
        Date date                        = new Date();
        Random randomNumber              = new Random(date.getTime());
        int MAX_NUM_OF_GENERATIONS       = 10000;
        int generations                  = 0;
        int i;
        int bestFitness                  = 1000;
        int fitness                      = 1;


        while ((generations != MAX_NUM_OF_GENERATIONS) && (fitness != 0))
        {
            generations++;
            for (i = 0; i < lengthOfWordToMatch; i++)
            {
                randomWord[i] = randomNumber.nextInt(57) + 65;
            }

            fitness = fitness2(randomWord);
            System.out.println("Fitness: " + fitness);

            if(bestFitness > fitness)
            {
                bestFitness = fitness;
            }
        }

        if(fitness == 0)
        {
            System.out.println("--------------");
            System.out.println("Generation:    " + generations);
            System.out.println("Word to Match: " + global.word);
            System.out.print("Word Found:    ");
            printASCIItoString(randomWord);
        } else {
            System.out.println("--------------");
            System.out.println("Best Fitness Found: " + bestFitness);
        }
    }


    public static class global
    {
        public static String word = "HeuristicSE_andAI"; // Works: 2
        //public static String word = "Brice_J";

        public static int getWordLength()
        {
            return word.length();
        }
    }

    // Function from http://stackoverflow.com/a/109389
    static Map sortByValue(Map map) {
        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });

        Map result = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry)it.next();
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}