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
        int generationNumber = 1;
        int myFitness        = 1;
        Map<Integer, Integer> fitnessMap = new HashMap<Integer, Integer>();

        // Randomly initialize Gen 1
        System.out.println("Generation " + generationNumber + " (Initial Generation)");
        int[][] initialGeneration = new int[32][lengthOfWord]; // row, column

        for(int i = 0; i < 32; i++)
        {
            for(int j = 0; j < lengthOfWord; j++)
            {
                initialGeneration[i][j] = randomNumber.nextInt(57) + 65;
            }
            myFitness = fitness1(initialGeneration[i]);
            //myFitness = fitness2(initialGeneration[i]);
            fitnessMap.put(i, myFitness); // Row, Fitness
            //printASCIItoString(initialGeneration[i]);

        }
        System.out.println("Unsorted: " + fitnessMap);
        fitnessMap = sortByValue(fitnessMap);
        System.out.println("Sorted: " + fitnessMap);
    }

    public static int fitness1(int[] word)
    {
        int[] wordToMatch = StringtoASCII(global.word);
        int lengthOfWord = wordToMatch.length;
        int fitness = 0, number = 0, i;

        for(i = 0; i < lengthOfWord; i++)
        {
            number = Math.abs(wordToMatch[i] - word[i]);
            fitness = number * number;
        }

        return fitness;
    }

    public static int fitness2(int[] word)
    {
        int[] wordToMatch = StringtoASCII(global.word);
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

    public static int[] StringtoASCII(String word)
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




