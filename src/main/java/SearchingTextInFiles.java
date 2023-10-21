import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SearchingTextInFiles {

    private static final File MAIN_PATH = new File(System.getProperty("user.dir") + "\\src\\test");

    private static final String SEARCHING_WORD = "testAPI";
    private static final int MAX_QUEUE = 5;

    private static final File END_FILE = new File("end");

    public static void main(String[] args) {
        BlockingQueue<File> queue = new ArrayBlockingQueue<>(MAX_QUEUE);
        new Thread(new SearcherFiles(MAIN_PATH, queue)).start();
        for (int i = 0; i < MAX_QUEUE; i++) {
            new Thread(new SearcherWordInFiles(SEARCHING_WORD, queue)).start();
        }
    }

    static class SearcherFiles implements Runnable {

        File searchingPath;
        BlockingQueue<File> queue;

        public SearcherFiles(File searchingPath, BlockingQueue<File> queue) {
            this.searchingPath = searchingPath;
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                searcherPath(searchingPath);
                queue.put(END_FILE);
            } catch (InterruptedException e) {
                System.out.println("Błąd przeszukiwacza ścieżek: " + e.getMessage());
            }
        }

        public void searcherPath(File path) throws InterruptedException {
            File[] paths = path.listFiles();
            if (paths != null && paths.length > 0) {
                for (File file : paths) {
                    if (file.isDirectory()) {
//                    System.out.println("Przeszukuję katalog: " + file.getPath());
                        searcherPath(file);
                    } else {
//                    System.out.println("Wrzucam na kolejkę plik: " + file.getName());
                        queue.put(file);
                    }
                }
            }
        }
    }

    static class SearcherWordInFiles implements Runnable {

        BlockingQueue<File> queue;

        String searchingWord;

        public SearcherWordInFiles(String searchingWord, BlockingQueue<File> queue) {
            this.searchingWord = searchingWord;
            this.queue = queue;
        }


        @Override
        public void run() {
            try {
                boolean isSearchingFinished = false;
                while (!isSearchingFinished) {
                    File searchingFile = queue.take();
//                    System.out.println("Pobieram z kolejki plik: " + searchingFile.getName());
                    if (searchingFile.equals(END_FILE)) {
                        isSearchingFinished = true;
                        queue.put(END_FILE);
                    } else {
                        searcherWord(searchingFile);
                    }
                }
            } catch (Exception e) {
                System.out.println("Błąd przeszukiwania pliku: " + e.getMessage());
            }
        }

        public void searcherWord(File searchingFile) throws FileNotFoundException {

            int lineNumber = 0;

            Scanner reader = new Scanner(new BufferedReader(new FileReader(searchingFile)));
            while (reader.hasNext()) {
                lineNumber++;
                if (reader.nextLine().contains(searchingWord)) {
                    System.out.println("Znaleziono słowo '" + searchingWord + "' w pliku '" + searchingFile.getPath() + "' w linii:" + lineNumber);
                }
            }
            reader.close();
        }
    }
}
