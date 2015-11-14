package edu.csula.cs460.file;

import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

public class MatrixFile
{
    private int[][] matrix;

    public MatrixFile(String filepath)
    {
        // TODO: read file from filepath ('exercise-1/2d-array-1.txt' for
        // example) and parse line by line to fill out matrix

        try
        {
          File file = new File(filepath);
          FileReader fr = new FileReader(file);
          BufferedReader br = new BufferedReader(fr);

          String line;
          String numbersLine = "";
          int rowNum = 0;
          int colNum = 0;

          while((line = br.readLine()) != null)
          {
            line = line.replace(" ", "");
            numbersLine += line;

            if(colNum < line.length())
            {
              colNum = line.length();
            }
            rowNum++;
          }

          matrix = new int[rowNum][colNum];

          for(int i = 0; i < numbersLine.length(); i++)
          {
            matrix[i/rowNum][i % colNum] = Character.getNumericValue(numbersLine.charAt(i));
          }

          br.close();
          fr.close();
        }
        catch(IOException ignored) {}
    }

    public int getValue(int row, int col)
    {
        // TODO: get value of a specific row and column (starting index is 0)
        return matrix[row][col];
    }

    public int getSum()
    {
        // TODO: return the sum of all numbers in matrix
        int sum = 0;

        for (int[] aMatrix : matrix) {
            for (int colNumber = 0; colNumber < matrix.length; colNumber++) {
                sum += aMatrix[colNumber];
            }
        }

        return sum;
    }
}
