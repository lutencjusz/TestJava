import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Zip {
    private static final int BUFFER = 128;
    private static final String BASE_FILE_NAME = "plikZip";

    private static final byte[] tempData = new byte[BUFFER];

    private static void compressZipFile(String fileName, ZipOutputStream zipOutputStream) throws IOException {

        int totalCounter = 0;
        int counter;

        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(fileName), BUFFER);
        zipOutputStream.putNextEntry(new ZipEntry(fileName));
        while ((counter = inputStream.read(tempData, 0, BUFFER)) != -1) {
            zipOutputStream.write(tempData, 0, counter);
            totalCounter += counter;
        }
        zipOutputStream.closeEntry();
        inputStream.close();
        System.out.println("Skompresowałem zip plik: " + fileName + " rozmiar: " + totalCounter + " B");
    }

    private static void decompressZipFile(File directory, ZipInputStream zipInputStream) throws IOException {
        ZipEntry zipEntry = null;
        while ((zipEntry = zipInputStream.getNextEntry()) != null) {
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(directory + File.separator + zipEntry.getName()));
            int totalCounter = 0;
            int counter;
            while ((counter = zipInputStream.read(tempData, 0, BUFFER)) != -1) {
                outputStream.write(tempData, 0, counter);
                totalCounter += counter;
            }
            outputStream.close();
            zipInputStream.closeEntry();
            System.out.println("Rozkompresowałem plik: " + zipEntry.getName() + ", skompresowany rozmiar: " + zipEntry.getCompressedSize() + " B, rozkompresowany rozmiar: " + totalCounter+" B");
        }
    }


    public static void main(String[] args) {
        String[] filesTOCompress = new String[]{"SerializedFile.txt", "pom.xml", "file.ser"};
        try {
            // kompresja plików
            ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(BASE_FILE_NAME + ".zip")));
            for (String fileName : filesTOCompress) {
                compressZipFile(fileName, zipOutputStream);
            }
            zipOutputStream.close();

            //dekompresja plików
            String directoryName = System.getProperty("user.dir") + File.separator + BASE_FILE_NAME;
            File directory = new File(directoryName);
            if (!directory.exists())
                if (!directory.mkdir())
                    throw new IOException("Nie udało się utworzyć katalogu: " + directory.getName());
            ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(new FileInputStream(BASE_FILE_NAME + ".zip")));
            decompressZipFile(directory, zipInputStream);
            zipInputStream.close();

        } catch (IOException e) {
            System.out.println("Wyjątek: " + e.getMessage());
        }
    }
}
