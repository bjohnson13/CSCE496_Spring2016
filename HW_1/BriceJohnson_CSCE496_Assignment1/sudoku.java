

import java.io.*;
import java.util.*;

public class sudoku {
    public static void main(String[] args)
    {
        // Extended Program
        // Get array from input file
        int[][] sudoku = getInput(args[0]);
        System.out.println("Initial Sudoku");
        printSudoku(sudoku);

        // Get fixed numbers for Sudoku
        int[][] fixedNumbres = getFixedNumbers(sudoku);

        // Apply all numbers that are missing
        int[][] newSudoku = fillInSudoku(sudoku);
        System.out.println("\nSudoku with all number in each row");
        printSudoku(newSudoku);

        // Solve Sudoku
        int[][] solvedSudoku = fullHillClimb(newSudoku, fixedNumbres);
        System.out.println("\nFinal Sudoku");
        printSudoku(solvedSudoku);

        /*
        // Base Program
        int[][] grid = makeGrid();
        int[][] initialSudoku = grid;

        System.out.println("Initial Sudoku");
        printSudoku(initialSudoku);

        grid = hillClimb(initialSudoku);

        System.out.println("\nFinal Sudoku");
        printSudoku(grid);
        */

    }

    public static int[][] fillInSudoku(int[][] sudoku)
    {
        // Fills in missing numbers from each column
        // i is column
        // j is rows

        int found = 0;

        for(int i = 0; i < 9; i++)
        {
            for(int k = 1; k < 10; k++)
            {
                found = 0;
                for(int j = 0; j < 9; j++)
                {
                    if((sudoku[j][i] == k) && (found == 0))
                    {
                        found = 1;
                    }
                }

                for(int j = 0; j < 9; j++)
                {
                    if((sudoku[j][i] == 0) && (found == 0))
                    {
                        sudoku[j][i] = k;
                        found = 1;
                    }
                }
            }
        }


        return sudoku;
    }

    public static int[][] getFixedNumbers(int[][] sudoku)
    {
        // Determine what spaces are fixed from the initial sudoke problem

        int[][] fixedNumbers = new int[9][9];

        for(int i = 0; i < 9; i++)
        {
            for(int j =0; j < 9; j++)
            {
                if(sudoku[i][j] != 0)
                {
                    fixedNumbers[i][j] = 1;
                }
            }
        }

        return fixedNumbers;
    }

    public static int[][] fullHillClimb(int[][] sudoku, int[][] fixedNumbers)
    {
        int rowFitness, subSquareFitness, newRowFitness, newSubSqaureFitness;
        int swapVar, i;
        int numIterations = 10000000;

        Date date           = new Date();
        Random randomNumber = new Random(date.getTime());
        int randCol, randRow1, randRow2, randNum;

        // Initial Fitness
        rowFitness       = calculateRowFitness(sudoku);
        subSquareFitness = calculateSubSquareFitness(sudoku);

        for(i = 0; i < numIterations; i++)
        {
            if((rowFitness != 0) || (subSquareFitness != 0)) {
                // For probability to use a bad move
                randNum  = randomNumber.nextInt(1000);
                // Random Column and 2 random Rows
                randCol  = randomNumber.nextInt(9);
                randRow1 = randomNumber.nextInt(9);
                randRow2 = randomNumber.nextInt(9);

                // Check id thw two elements are fixed, if they are, choose different elements
                while((fixedNumbers[randRow1][randCol] == 1) || (fixedNumbers[randRow2][randCol] == 1)) {
                    randCol  = randomNumber.nextInt(9);
                    randRow1 = randomNumber.nextInt(9);
                    randRow2 = randomNumber.nextInt(9);
                    while (randRow1 == randRow2) {
                        randRow2 = randomNumber.nextInt(9);
                    }
                }

                // Swap
                swapVar                   = sudoku[randRow1][randCol];
                sudoku[randRow1][randCol] = sudoku[randRow2][randCol];
                sudoku[randRow2][randCol] = swapVar;

                // Calculate new fitness
                newRowFitness       = calculateRowFitness(sudoku);
                newSubSqaureFitness = calculateSubSquareFitness(sudoku);

                // Check if new fitness is better
                if (((newRowFitness <= rowFitness) && (newSubSqaureFitness <= subSquareFitness))) {
                    // Fitness is better, use new Sudoku grid
                    rowFitness       = calculateRowFitness(sudoku);
                    subSquareFitness = calculateSubSquareFitness(sudoku);
                } else if (randNum < 10) {
                    // Random time to use a potentially worse fitness, use new Sudoku grid
                    rowFitness       = calculateRowFitness(sudoku);
                    subSquareFitness = calculateSubSquareFitness(sudoku);
                } else {
                    // Worse fitness, revert to original Sudoku grid
                    swapVar = sudoku[randRow1][randCol];
                    sudoku[randRow1][randCol] = sudoku[randRow2][randCol];
                    sudoku[randRow2][randCol] = swapVar;
                }
            }
            else {
                break;
            }
        }

        System.out.println("\nIterations: " + i);
        return sudoku;
    }

    public static int[][] hillClimb(int[][] sudoku)
    {
        int rowFitness, subSquareFitness, newRowFitness, newSubSqaureFitness;
        int swapVar, i;
        int numIterations = 10000000;

        Date date           = new Date();
        Random randomNumber = new Random(date.getTime());
        int randCol, randRow1, randRow2, randNum;

        // Initial Fitness
        rowFitness       = calculateRowFitness(sudoku);
        subSquareFitness = calculateSubSquareFitness(sudoku);

        for(i = 0; i < numIterations; i++)
        {
            if((rowFitness != 0) || (subSquareFitness != 0)) {
                // For probability to use a bad move
                randNum  = randomNumber.nextInt(1000);
                // Random Column and 2 random Rows
                randCol  = randomNumber.nextInt(9);
                randRow1 = randomNumber.nextInt(9);
                randRow2 = randomNumber.nextInt(9);
                while(randRow1 == randRow2)
                {
                    randRow2 = randomNumber.nextInt(9);
                }

                // Swap
                swapVar                   = sudoku[randRow1][randCol];
                sudoku[randRow1][randCol] = sudoku[randRow2][randCol];
                sudoku[randRow2][randCol] = swapVar;

                // Calculate new fitness
                newRowFitness       = calculateRowFitness(sudoku);
                newSubSqaureFitness = calculateSubSquareFitness(sudoku);

                // Check if new fitness is better
                if (((newRowFitness <= rowFitness) && (newSubSqaureFitness <= subSquareFitness))) {
                    // Fitness is better, use new Sudoku grid
                    rowFitness       = calculateRowFitness(sudoku);
                    subSquareFitness = calculateSubSquareFitness(sudoku);
                } else if (randNum < 10) {
                    // Random time to use a potentially worse fitness, use new Sudoku grid
                    rowFitness       = calculateRowFitness(sudoku);
                    subSquareFitness = calculateSubSquareFitness(sudoku);
                } else {
                    // Worse fitness, revert to original Sudoku grid
                    swapVar = sudoku[randRow1][randCol];
                    sudoku[randRow1][randCol] = sudoku[randRow2][randCol];
                    sudoku[randRow2][randCol] = swapVar;
                }
            }
            else {
                break;
            }
        }

        System.out.println("\nIterations: " + i);
        return sudoku;
    }

    public static int calculateRowFitness(int[][] sudoku)
    {
        // Calculate row fitness, For each missing element in each row, do not
        //    subtract from the fitness
        // i = rows
        // j = columns

        int fitness = 81;
        int found = 0;

        for(int i = 0; i < 9; i++)
        {
            for(int k = 1; k < 10; k++)
            {
                found = 0;
                for(int j = 0; j < 9; j++)
                {
                    if((sudoku[i][j] == k) && (found == 0))
                    {
                        fitness -= 1;
                        found = 1;
                    }
                }
            }
        }

        return fitness;
    }

    public static int calculateSubSquareFitness(int[][] sudoku)
    {
        // Calculate sub square fitness, For each missing element in each subsquare, do not
        //    subtract from the fitness
        // i = rows
        // j = columns
        /* Sub square Numbering
           ... | ... | ...
           .1. | .2. | .3.
           ... | ... | ...
           ---------------
           ... | ... | ...
           .4. | .5. | .6.
           ... | ... | ...
           ---------------
           ... | ... | ...
           .7. | .8. | .9.
           ... | ... | ...
         */


        int fitness = 81;
        int found = 0;

        // Subsquare 1
        for(int k = 1; k < 10; k++) {
            found = 0;
            for (int i = 0; i < 3; i++) // row
            {
                for (int j = 0; j < 3; j++) // column
                {
                    if ((sudoku[i][j] == k) && (found == 0)) {
                        fitness -= 1;
                        found = 1;
                    }
                }
            }
        }

        // Subsquare 2
        for(int k = 1; k < 10; k++) {
            found = 0;
            for (int i = 0; i < 3; i++) // row
            {


                for (int j = 3; j < 6; j++) // column
                {
                    if ((sudoku[i][j] == k) && (found == 0)) {
                        fitness -= 1;
                        found = 1;
                    }
                }
            }
        }

        // Subsquare 3
        for(int k = 1; k < 10; k++) {
            found = 0;
            for (int i = 0; i < 3; i++) // row
            {
                for (int j = 6; j < 9; j++) // column
                {
                    if ((sudoku[i][j] == k) && (found == 0)) {
                        fitness -= 1;
                        found = 1;
                    }
                }
            }
        }

        // Subsquare 4
        for(int k = 1; k < 10; k++) {
            found = 0;
            for (int i = 3; i < 6; i++) // row
            {

                for (int j = 0; j < 3; j++) // column
                {
                    if ((sudoku[i][j] == k) && (found == 0)) {
                        fitness -= 1;
                        found = 1;
                    }
                }
            }
        }

        // Subsquare 5
        for(int k = 1; k < 10; k++) {
            found = 0;
            for (int i = 3; i < 6; i++) // row
            {
                for (int j = 3; j < 6; j++) // column
                {
                    if ((sudoku[i][j] == k) && (found == 0)) {
                        fitness -= 1;
                        found = 1;
                    }
                }
            }
        }

        // Subsquare 6
        for(int k = 1; k < 10; k++) {
            found = 0;
            for (int i = 3; i < 6; i++) // row
            {
                for (int j = 6; j < 9; j++) // column
                {
                    if ((sudoku[i][j] == k) && (found == 0)) {
                        fitness -= 1;
                        found = 1;
                    }
                }
            }
        }

        // Subsquare 7
        for(int k = 1; k < 10; k++) {
            found = 0;
            for (int i = 6; i < 9; i++) // row
            {

                for (int j = 0; j < 3; j++) // column
                {
                    if ((sudoku[i][j] == k) && (found == 0)) {
                        fitness -= 1;
                        found = 1;
                    }
                }
            }
        }

        // Subsquare 8
        for(int k = 1; k < 10; k++)
        {
            found = 0;
            for(int i = 6; i < 9; i++) // row
            {
                for(int j = 3; j < 6; j++) // column
                {
                    if((sudoku[i][j] == k) && (found == 0))
                    {
                        fitness -= 1;
                        found = 1;
                    }
                }
            }
        }

        // Subsquare 9
        for(int k = 1; k < 10; k++) {
            found = 0;
            for (int i = 6; i < 9; i++) // row
            {
                for (int j = 6; j < 9; j++) // column
                {
                    if ((sudoku[i][j] == k) && (found == 0)) {
                        fitness -= 1;
                        found = 1;
                    }
                }
            }
        }

        return fitness;
    }

    public static int[][] makeGrid()
    {
        // For Base Probem, create initial grid to look like:
        /*
        1 1 1 | 1 1 1 | 1 1 1
        2 2 2 | 2 2 2 | 2 2 2
        3 3 3 | 3 3 3 | 3 3 3
        ---------------------
        4 4 4 | 4 4 4 | 4 4 4
        5 5 5 | 5 5 5 | 5 5 5
        6 6 6 | 6 6 6 | 6 6 6
        ---------------------
        7 7 7 | 7 7 7 | 7 7 7
        8 8 8 | 8 8 8 | 8 8 8
        9 9 9 | 9 9 9 | 9 9 9
        */

        int sudokuSize = 9;
        int[][] grid = new int[sudokuSize][sudokuSize];
        for(int i = 0; i < sudokuSize; i++)
        {
            for(int j = 0; j < sudokuSize; j++)
            {
                grid[i][j] = i + 1;
            }
        }

        return grid;
    }

    public static void printSudoku(int[][] grid)
    {
        // Prints a nicey formatted Sudoku grid
        for(int i = 0; i < 9; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                System.out.print(grid[i][j] + " ");

                if(((j + 1) % 3 == 0) && (j != 8))
                {
                    System.out.print("| ");
                }
            }

            if(((i + 1) % 3 == 0) && (i != 8))
            {
                System.out.println("\n---------------------");
            }
            else
            {
                System.out.println("");
            }
        }
    }

    public static int[][] getInput(String in)
    {
        // Reads input file and parses data into Sudoku grid, represented by a 2-d array
        int[][] sudoku = new int[9][9];

        BufferedReader br = null;

        try {

            File inFile = new File(in);

            String sCurrentLine;
            String[] arr;
            int i = 0;
            br = new BufferedReader(new FileReader(inFile));


            while (((sCurrentLine = br.readLine()) != null) && (i < 9)) {
                arr = sCurrentLine.split("\\s+");
                for (int j = 0; j < arr.length; j++) {
                    sudoku[i][j] = Integer.parseInt(arr[j]);
                }
                i++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return sudoku;
    }

}
