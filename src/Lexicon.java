import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.io.File;

class Parser {
    private String fileName;
    private String pattern;
    private String word;
    private int val = 1;
    private Path path;
    private Scanner scanner;
    private Map<String, Integer> words;
    private ArrayList<String> elements;
    private File file;
    private FileWriter fw;

    public Parser(String fileN) throws IOException {
        this.fileName = fileN;
        words = new TreeMap<>();
        path = Paths.get(fileName);
        scanner = new Scanner(path);
        elements = new ArrayList<>();
        pattern = ("[\\w.,:;!?»«()]+");//???»«
        scanText(pattern);
        file = new File("lexicon.txt");
        fw = new FileWriter(file);

    }

    private void scanText(String p) {
        pattern = p;
        while (scanner.hasNext()) {
            if (scanner.hasNext(pattern)) {
                word = scanner.next();
                elements.add(word);
            } else scanner.next();
        }
    }

    public void wordsStatistic() {
        for (String w : elements) {
            w = w.replaceAll("[^A-Za-z0-9]", "");
            if (words.containsKey(w)) {
                int value = words.get(w);
                value++;
                words.put(w, value);
            } else words.put(w, val);
        }
        try {
            //fw = new FileWriter(file,true);
            for (Map.Entry<String, Integer> entry : words.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                fw.write(key + " - " + value + "\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void clausesCounter() {
        int clauses = 0;
        for (String w : elements) {
            if (w.contains(".") || w.contains("?") ||
                    w.contains("!") || w.contains("...")) {
                clauses++;
            }
        }
        try {
            fw = new FileWriter(file, true);//????
            fw.write("\r\nClauses count = " + clauses);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Lexicon {
    public static void main(String[] args) throws IOException {
        Parser par = new Parser("input.txt");
        par.wordsStatistic();
        par.clausesCounter();
    }
}
