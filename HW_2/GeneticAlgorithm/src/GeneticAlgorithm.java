/**
 * Created by bricejohnson on 2/1/16.
 */
public class GeneticAlgorithm
{
    public static void main(String[] args)
    {
        System.out.println("Hello, World");
        System.out.println("Brice Johnson");

        int[] data;
        data = new int[] {66,114,105,99,101};

        String myName = convertASCIItoString(data);
    }

    public static String convertASCIItoString(int[] asciiArray)
    {
        String word = "";

        for(int i = 0; i < asciiArray.length; i++)
        {
            word = word + (char)asciiArray[i];
        }

        System.out.println(word);

        return word;
    }
}
