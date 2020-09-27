import java.io.*;
import java.text.Normalizer;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Pattern;

public class Test3 {

    public static class FileReaderCallable implements Callable<LinkedHashMap<String, Integer>> {
        private String filename;

        public FileReaderCallable(String filename) {
            this.filename = filename;
        }

        public static String removeAccent(String s) {

            String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(temp).replaceAll("").toLowerCase().replace('đ','d');
        }

        @Override
        public LinkedHashMap<String, Integer> call() {
            LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
            try {
                File fileIn = new File(filename);
                FileInputStream fileStream = new FileInputStream(fileIn);
                InputStreamReader input = new InputStreamReader(fileStream);
                BufferedReader reader = new BufferedReader(input);
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.equals("")) {
                        String[] wordList = line.split("[\\s:,.?;()]+");
                        for (String s : wordList) {
                            String word = removeAccent(s);
                            if (word.matches("[a-z]+")) {
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
            } catch (IOException e) {
                e.printStackTrace();
            }
            return map;
        }
    }
    public static void main(String[] args) {
        File folder = new File("/home/phamthang/Desktop/Multi");
        File[] listOfFiles = folder.listFiles();

        if(listOfFiles != null) {
            ExecutorService executor = Executors.newFixedThreadPool(6);
            List<Future<LinkedHashMap<String, Integer>>> futureList = new ArrayList<>();
            LinkedHashMap<String, Integer> countWord = new LinkedHashMap<>();

            for (int i = 0; i < listOfFiles.length; i++) {
                futureList.add(executor.submit(new FileReaderCallable(listOfFiles[i].getAbsolutePath())));
            }
            executor.shutdown();
            while (!executor.isTerminated()) {

            }

            for (int i = 0; i < futureList.size(); i++) {
                try {
                    LinkedHashMap<String, Integer> map = futureList.get(i).get();
                    for(String key : map.keySet()) {
                        if (countWord.containsKey(key)) {
                            countWord.put(key, countWord.get(key) + map.get(key));
                        } else {
                            countWord.put(key, map.get(key));
                        }
                    }
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("The 10 most common words:");
            countWord.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .limit(10)
                    .forEach(entry -> {
                        System.out.println(entry.getKey() + " : " + entry.getValue());
                    });


            System.out.println("10 words that appear at least: ");
            countWord.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .limit(10)
                    .forEach(entry -> {
                        System.out.println(entry.getKey() + " : " + entry.getValue());
                    });

        }
        else {
            System.out.println("No file in folder!");
        }


    }
}
