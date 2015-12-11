package edu.csula.cs460.file;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Arrays;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

public class ListFile {
    private Map<String, List<String>> adjancencyList = new HashMap<>();

    public ListFile(String filepath)
    {
        // TODO: read file from filepath ('exercise-1/list-1.txt' for
        // example) and parse line by line to fill out adjancencyList
        try
        {
          File file = new File(filepath);
          FileReader fr = new FileReader(file);
          BufferedReader br = new BufferedReader(fr);

          String line;

          while((line = br.readLine()) != null)
          {
            String[] split = line.split(":");
            String listKey = split[0];
            List<String> listAdjance = Arrays.asList(split[1].split(" "));

            adjancencyList.put(listKey, listAdjance);
          }

          if(adjancencyList == null)
          {
            adjancencyList = new HashMap<>();
          }

          br.close();
          fr.close();
        }
        catch(IOException ignored) {}
    }

    public List<String> getList(String key)
    {
        // TODO: get List of String for specific key
        return adjancencyList.get(key);
    }
}
