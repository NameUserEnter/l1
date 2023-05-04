import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Reader {
    public static void main(String[] arg)
    {
        Path currentRelativePath = Paths.get("").toAbsolutePath();
        try(BufferedReader reader = Files.newBufferedReader(Path.of(("%s\\words.txt".formatted(currentRelativePath.toString())))))
        {
            HashSet<String> wordsInFile = new HashSet<>();
            while(reader.ready())
            {
                String line = reader.readLine();
                var wordsInLine = Arrays.asList(line.split("([^a-zA-Z])"));
                wordsInFile.addAll(wordsInLine);
                meetRequirement(wordsInFile);
            }
            reader.close();
            if(wordsInFile.contains("")){wordsInFile.remove("");}
            HashMap<String,Integer> wordsWithNumber = new HashMap<>();
            put(wordsWithNumber,wordsInFile);
            printer(wordsWithNumber);
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }
    public static void meetRequirement(HashSet<String> words)
    {
        for (var w : words)
        {
            w = w.length() > 30 ? w.substring(0, 30) : w;
        }
    }
    public static int numberOfLetters(String word)
    {
        int count = 0;
        for (var w : word.toCharArray())
        {
            if(!(w=='a'||w=='e'||w=='i'||w=='o'||w=='u'||w=='y'))
            {count++;}
        }
        return count;
    }
    public static void put(HashMap<String,Integer> wordsWithNumber, HashSet<String> wordsInFile)
    {
        for (var w : wordsInFile)
        {
            wordsWithNumber.put(w,numberOfLetters(w));
        }
    }
    public static void printer(HashMap<String,Integer> maps) {
        HashSet<Integer> uniqueMapsValues = new HashSet<>(maps.values());
        ArrayList<Integer> mapsValues = new ArrayList<>(uniqueMapsValues);
        Collections.sort(mapsValues);
        int i = 0;
        while (i != mapsValues.size()) {
            for (Map.Entry<String, Integer> entry : maps.entrySet()) {
                if (entry.getValue().equals(mapsValues.get(i))) {
                    System.out.println(entry.getKey());
                }
            }
            i++;
        }
    }
}
