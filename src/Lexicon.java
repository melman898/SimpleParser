import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.io.File;

class Parser {
    private String fileName;
    private String pattern = "[A-Za-z0-9,.()]+";
    private String word;
    private int val;
    private Path path;
    private Scanner scanner;
    private Map<String , Integer> words;

    public Parser(String fileName) throws IOException {
        fileName = fileName;
        val = 1;
        words = new TreeMap<>();
        path = Paths.get(fileName);
        scanner = new Scanner(path);
        // если юзать TreeMap сложность будет О(log(n))
        // если юзать HashMap среднем О(1) в худшем О(n),
        while (scanner.hasNext()){
            if(scanner.hasNext(pattern)){
                word = scanner.next();
                word = word.replaceAll("[^A-Za-z0-9]", "");
                if (words.containsKey(word)){
                    int value = words.get(word);
                    value++;
                    words.put(word,value);
                }
                else words.put(word,val);
            }
            else scanner.next();
        }
        writeResult();
    }
    private void writeResult(){
        File file = new File("lexicon.txt");
        FileWriter fr = null;
        try {
            fr = new FileWriter(file);
            for(Map.Entry<String,Integer> entry : words.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                fr.write(key + " - " + value+"\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
public class Lexicon {
    public static void main(String[] args) throws IOException {
        Parser par = new Parser(args[0]);
    }
}
