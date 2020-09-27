import java.io.File;

public class Test3 {
    private static final int THREAD_COUNT = 4;

    public static void main(String[] args) {
        File folder = new File("/home/phamthang/Desktop/Multi");
        File[] listOfFiles = folder.listFiles();

        if(listOfFiles != null)
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    System.out.println(file.getName());
                }
            }


    }
}
