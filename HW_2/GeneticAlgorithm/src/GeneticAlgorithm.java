import java.util.*;

/**
 * Created by bricejohnson on 2/1/16.
 */

public class GeneticAlgorithm
{
    public static void main(String[] args)
    {
        int lengthOfWordToMatch          = global.getWordLength();
        Date date                        = new Date();
        Random randomNumber              = new Random(date.getTime());
        Map<Integer, Integer> fitnessMap = new HashMap<>();
        Integer[] top8IndexArr           = new Integer[8];
        Integer[] top8FitnessArr         = new Integer[8];
        int[][] initialGeneration        = new int[32][lengthOfWordToMatch]; // row, column
        int[][] nextGeneration           = new int[16][lengthOfWordToMatch];
        int[][] currentGeneration        = new int[16][lengthOfWordToMatch];
        int generationNumber             = 0;
        int rand, wordFitness, i, j;

        // ============================================
        // ============================================
        // Choose which fitness function to run
        // 1 - Distance
        // 2 - Does the character match
        int CHOOSE_FITNESS_FUNCTION = 2;
        int MAX_NUM_OF_GENERATIONS = 20000000;
        // ============================================
        // ============================================

        // Randomly initialize Generation 0
        for(i = 0; i < 32; i++)
        {
            for(j = 0; j < lengthOfWordToMatch; j++)
            {
                initialGeneration[i][j] = randomNumber.nextInt(57) + 65;
            }
            if(CHOOSE_FITNESS_FUNCTION == 1) {
                wordFitness = fitness1(initialGeneration[i]);
            }else {
                wordFitness = fitness2(initialGeneration[i]);
            }
            fitnessMap.put(i, wordFitness); // Row, Fitness
            //printASCIItoString(initialGeneration[i]);
        }

        // Choose 8 best
        fitnessMap = sortByValue(fitnessMap);
        for(i = 0; i < 8; i++)
        {
            top8IndexArr[i]   = fitnessMap.entrySet().iterator().next().getKey();
            top8FitnessArr[i] = fitnessMap.get(top8IndexArr[i]);
            fitnessMap.remove(top8IndexArr[i]);
            //System.out.println("Index: " + top8IndexArr[i] + " Fitness: " + top8FitnessArr[i]);
        }
        fitnessMap.clear();

        System.out.println("Generation " + generationNumber);
        System.out.println("Best Fitness: " + top8FitnessArr[0]);
        printASCIItoString(initialGeneration[top8IndexArr[0]]);

        // Crossover top 8, create next generation
        generationNumber++;
        // Copy Parents over

        for(i = 0; i < 8; i++)
        {
            System.arraycopy(initialGeneration[top8IndexArr[i]], 0, nextGeneration[i], 0, lengthOfWordToMatch);
        }
        for(i = 8; i < 10; i++)
        {
            System.arraycopy(nextGeneration[i-8], 0, nextGeneration[i], 0, (lengthOfWordToMatch / 2));
            if(lengthOfWordToMatch % 2 == 0) {
                System.arraycopy(nextGeneration[i - 6], (lengthOfWordToMatch / 2), nextGeneration[i], (lengthOfWordToMatch / 2), (lengthOfWordToMatch / 2));
            } else{
                System.arraycopy(nextGeneration[i - 6], (lengthOfWordToMatch / 2), nextGeneration[i], (lengthOfWordToMatch / 2), (lengthOfWordToMatch / 2) + 1);
            }
        }
        for(i = 12; i < 14; i++)
        {
            System.arraycopy(nextGeneration[i-8], 0, nextGeneration[i], 0, (lengthOfWordToMatch / 2));
            if(lengthOfWordToMatch % 2 == 0) {
                System.arraycopy(nextGeneration[i - 6], (lengthOfWordToMatch / 2), nextGeneration[i], (lengthOfWordToMatch / 2), (lengthOfWordToMatch / 2));
            } else{
                System.arraycopy(nextGeneration[i - 6], (lengthOfWordToMatch / 2), nextGeneration[i], (lengthOfWordToMatch / 2), (lengthOfWordToMatch / 2) + 1);
            }
        }

        // Mutation
        for(i = 0; i < 16; i++)
        {
            for(j = 0; j < lengthOfWordToMatch; j++)
            {
                rand = randomNumber.nextInt(100);
                if(rand < 3)
                {
                    nextGeneration[i][j] = randomNumber.nextInt(57) + 65;
                }
            }
            if(CHOOSE_FITNESS_FUNCTION == 1) {
                wordFitness = fitness1(nextGeneration[i]);
            }else {
                wordFitness = fitness2(nextGeneration[i]);
            }
            fitnessMap.put(i, wordFitness);
        }

        // Choose 8 best
        fitnessMap = sortByValue(fitnessMap);
        for(i = 0; i < 8; i++)
        {
            top8IndexArr[i]   = fitnessMap.entrySet().iterator().next().getKey();
            top8FitnessArr[i] = fitnessMap.get(top8IndexArr[i]);
            fitnessMap.remove(top8IndexArr[i]);
            //System.out.println("Index: " + top8IndexArr[i] + " Fitness: " + top8FitnessArr[i]);
        }
        fitnessMap.clear();

        System.out.println("--------------");
        System.out.println("Generation " + generationNumber);
        System.out.println("Best Fitness: " + top8FitnessArr[0]);
        printASCIItoString(nextGeneration[top8IndexArr[0]]);

// ====================================================================================================================================================================================
// ====================================================================================================================================================================================
// ====================================================================================================================================================================================
// ====================================================================================================================================================================================

        while((generationNumber != MAX_NUM_OF_GENERATIONS) && (top8FitnessArr[0] != 0))
        {
            generationNumber++;
            // ============================================
            // ============================================
            // Info to print for each generation
            //System.out.println("Generation " + generationNumber);     // Generation Number
            printASCIItoString(nextGeneration[top8IndexArr[0]]);      // Best mathcing String
            //System.out.println("Best Fitness: " + top8FitnessArr[0]); // Best fitness
            // ============================================
            // ============================================

            // Crossover top 8
            for(i = 0; i < 8; i++)
            {
                System.arraycopy(nextGeneration[top8IndexArr[i]], 0, currentGeneration[i], 0, lengthOfWordToMatch);
            }
            for(i = 8; i < 10; i++)
            {
                System.arraycopy(currentGeneration[i-8], 0, currentGeneration[i], 0, (lengthOfWordToMatch / 2));
                if(lengthOfWordToMatch % 2 == 0) {
                    System.arraycopy(currentGeneration[i - 6], (lengthOfWordToMatch / 2), currentGeneration[i], (lengthOfWordToMatch / 2), (lengthOfWordToMatch / 2));
                } else{
                    System.arraycopy(currentGeneration[i - 6], (lengthOfWordToMatch / 2), currentGeneration[i], (lengthOfWordToMatch / 2), (lengthOfWordToMatch / 2) + 1);
                }
            }
            for(i = 12; i < 14; i++) {
                System.arraycopy(currentGeneration[i - 8], 0, currentGeneration[i], 0, (lengthOfWordToMatch / 2));
                if (lengthOfWordToMatch % 2 == 0) {
                    System.arraycopy(currentGeneration[i - 6], (lengthOfWordToMatch / 2), currentGeneration[i], (lengthOfWordToMatch / 2), (lengthOfWordToMatch / 2));
                } else {
                    System.arraycopy(currentGeneration[i - 6], (lengthOfWordToMatch / 2), currentGeneration[i], (lengthOfWordToMatch / 2), (lengthOfWordToMatch / 2) + 1);
                }
            }

            // Mutation
            for(i = 0; i < 16; i++)
            {
                for(j = 0; j < lengthOfWordToMatch; j++)
                {
                    rand = randomNumber.nextInt(100);
                    if(rand < 3)
                    {
                        nextGeneration[i][j] = randomNumber.nextInt(57) + 65;
                    }else{
                        nextGeneration[i][j] = currentGeneration[i][j];
                    }

                }
                if(CHOOSE_FITNESS_FUNCTION == 1) {
                    wordFitness = fitness1(nextGeneration[i]);
                }else {
                    wordFitness = fitness2(nextGeneration[i]);
                }
                fitnessMap.put(i, wordFitness);
            }

            // Choose 8 best
            fitnessMap = sortByValue(fitnessMap);
            for(i = 0; i < 8; i++)
            {
                top8IndexArr[i]   = fitnessMap.entrySet().iterator().next().getKey();
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

    public static int fitness1(int[] word)
    {
        int[] wordToMatch = stringToASCII(global.word);
        int lengthOfWord = wordToMatch.length;
        int fitness = 0;
        int number, i;

        for(i = 0; i < lengthOfWord; i++)
        {
            number  = Math.abs(wordToMatch[i] - word[i]);
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

    public static class global
    {
        //public static String word = "HeuristicSE_andAI"; // Fail: 2, 20000000
        //public static String word = "HeuristicSE_andA";  // Works: 2, 10000000
        public static String word = "HeuristicSE_and";   // Works: 2, 10000000
        //public static String word = "HeuristicSE_an";    // Works: 2, 10000000
        //public static String word = "BriceJohnson";      // Works: 2, 10000000
        //public static String word = "Brice";             // Works: 1, 10000000

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