import java.util.*;

/**
 * Created by bricejohnson on 2/1/16.
 */

public class GeneticAlgorithm
{
    public static void main(String[] args)
    {
        int lengthOfWord     = global.getWordLength();
        Date date            = new Date();
        Random randomNumber  = new Random(date.getTime());
        int generationNumber = 0;
        int rand             = 0;
        int myFitness        = 1;
        int i, j             = 0;
        Map<Integer, Integer> fitnessMap = new HashMap<>();
        Integer[] top8Arr = new Integer[8];
        int[][] initialGeneration = new int[32][lengthOfWord]; // row, column
        int[][] nextGeneration = new int[16][lengthOfWord];
        int[][] currentGeneration = new int[16][lengthOfWord]; // row, column

        // Randomly initialize Gen 1
        System.out.println("Generation " + generationNumber);


        for(i = 0; i < 32; i++)
        {
            for(j = 0; j < lengthOfWord; j++)
            {
                initialGeneration[i][j] = randomNumber.nextInt(57) + 65;
            }
            myFitness = fitness1(initialGeneration[i]);
            //myFitness = fitness2(initialGeneration[i]);
            fitnessMap.put(i, myFitness); // Row, Fitness
            //printASCIItoString(initialGeneration[i]);
        }

        // Choose 8 best
        fitnessMap = sortByValue(fitnessMap);
        for(i = 0; i < 8; i++)
        {
            top8Arr[i] = fitnessMap.entrySet().iterator().next().getKey();
            fitnessMap.remove(top8Arr[i]);
        }
        fitnessMap.clear();
        System.out.println("Best Fitness: " + top8Arr[0]);


        // Mate top 8, make next currentGeneration
        generationNumber++;
        for(i = 0; i < 2; i++)
        {
            for(j = 0; j < (lengthOfWord / 2); j++)
            {
                currentGeneration[i][j] = initialGeneration[top8Arr[i]][j];
            }
            for(j = (lengthOfWord / 2); j < lengthOfWord; j++)
            {
                currentGeneration[i][j] = initialGeneration[top8Arr[i+2]][j];
            }

        }
        for(i = 2; i < 4; i++)
        {
            for(j = 0; j < (lengthOfWord / 2); j++)
            {
                currentGeneration[i][j] = initialGeneration[top8Arr[i+2]][j];
            }
            for(j = (lengthOfWord / 2); j < lengthOfWord; j++)
            {
                currentGeneration[i][j] = initialGeneration[top8Arr[i]][j];
            }
        }
        for(i = 8; i < 16; i++)
        {
            for(j = 0; j < lengthOfWord; j++)
            {
                currentGeneration[i][j] = initialGeneration[top8Arr[i-8]][j];
            }
        }

        fitnessMap.clear();
        // Mutation
        for(i = 0; i < 16; i++)
        {
            for(j = 0; j < lengthOfWord; j++)
            {
                rand = randomNumber.nextInt(100);
                if(rand < 3)
                {
                    currentGeneration[i][j] = randomNumber.nextInt(57) + 65;
                }
            }
            myFitness = fitness1(initialGeneration[i]);
            fitnessMap.put(i, myFitness);
        }

        System.out.println("Generation " + generationNumber);
        // Choose 8 best
        fitnessMap = sortByValue(fitnessMap);
        for(i = 0; i < 8; i++)
        {
            top8Arr[i] = fitnessMap.entrySet().iterator().next().getKey();
            fitnessMap.remove(top8Arr[i]);
        }
        fitnessMap.clear();
        System.out.println("Best Fitness: " + top8Arr[0]);





     // =============================== =============================== =============================== ===============================
        // =============================== =============================== =============================== ===============================
        // =============================== =============================== =============================== ===============================
        // =============================== =============================== =============================== ===============================


        // Repeat until conditions are met
        while((generationNumber != 1000) && (top8Arr[0] != 0))
        {
            generationNumber++;

            // Mate top 8, make currentGeneration
            for(i = 0; i < 2; i++)
            {
                for(j = 0; j < (lengthOfWord / 2); j++)
                {
                    nextGeneration[i][j] = currentGeneration[top8Arr[i]][j];
                }
                for(j = (lengthOfWord / 2); j < lengthOfWord; j++)
                {
                    nextGeneration[i][j] = currentGeneration[top8Arr[i+2]][j];
                }

            }
            for(i = 2; i < 4; i++)
            {
                for(j = 0; j < (lengthOfWord / 2); j++)
                {
                    nextGeneration[i][j] = currentGeneration[top8Arr[i+2]][j];
                }
                for(j = (lengthOfWord / 2); j < lengthOfWord; j++)
                {
                    nextGeneration[i][j] = currentGeneration[top8Arr[i]][j];
                }
            }
            for(i = 8; i < 16; i++)
            {
                for(j = 0; j < lengthOfWord; j++)
                {
                    nextGeneration[i][j] = currentGeneration[top8Arr[i-8]][j];
                }
            }

            // Mutation
            fitnessMap.clear();
            for(i = 0; i < 16; i++)
            {
                for(j = 0; j < lengthOfWord; j++)
                {
                    rand = randomNumber.nextInt(100);
                    if(rand < 3)
                    {
                        currentGeneration[i][j] = randomNumber.nextInt(57) + 65;
                    }
                    else
                    {
                        currentGeneration[i][j] = nextGeneration[i][j];
                    }
                }
                // Generate Fitness
                myFitness = fitness1(currentGeneration[i]);
                fitnessMap.put(i, myFitness);
            }

            //System.out.println("Generation " + generationNumber);
            // Choose 8 best
            fitnessMap = sortByValue(fitnessMap);
            for(i = 0; i < 8; i++)
            {
                top8Arr[i] = fitnessMap.entrySet().iterator().next().getKey();
                fitnessMap.remove(top8Arr[i]);
            }
            fitnessMap.clear();
            System.out.println("Best Fitness: " + top8Arr[0]);



        }
        System.out.println("Generation: " + generationNumber);
        printASCIItoString(currentGeneration[top8Arr[0]]);

    }

    public static int fitness1(int[] word)
    {
        int[] wordToMatch = stringToASCII(global.word);
        int lengthOfWord = wordToMatch.length;
        int fitness = 0, number = 0, i;

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
        //public static String word = "HeuristicSE_andAI";
        public static String word = "Brice";

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




