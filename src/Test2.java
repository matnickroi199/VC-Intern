import java.io.*;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.regex.Pattern;

public class Test2 {
    public static String removeAccent(String s) {

        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").toLowerCase().replace('đ','d');
    }
    public static void main(String[] args) {
        try {
            File fileIn = new File("/home/phamthang/IdeaProjects/Test/src/input.txt");
            FileInputStream fileStream = new FileInputStream(fileIn);
            InputStreamReader input = new InputStreamReader(fileStream);
            BufferedReader reader = new BufferedReader(input);
            HashMap<String, Integer> map = new HashMap<>();

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.equals("")) {
                    String[] wordList = line.split("[\\s:,.?;()]+");
                    for (String s : wordList) {
                        String word = removeAccent(s);
                        if(word.matches("[a-z]+")) {
                            if (map.containsKey(word)) {
                                map.put(word, map.get(word) + 1);
                            } else {
                                map.put(word, 1);
                            }
                        }
                    }
                }
            }

            reader.close();
            input.close();
            fileStream.close();

            File fileOut = new File("/home/phamthang/IdeaProjects/Test/src/output.txt");
            FileWriter fw = new FileWriter(fileOut);
            StringBuilder str = new StringBuilder();
            for (String i : map.keySet()) {
                str.append(i).append(" : ").append(map.get(i)).append("\n");
            }
            fw.write(str.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
