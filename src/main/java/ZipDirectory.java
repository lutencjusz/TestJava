import net.bytebuddy.asm.Advice;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipDirectory {
    static ZipOutputStream zos;
    static List<String> excludedPathAndFiles;
    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    static LocalDateTime today = LocalDateTime.now();

    public static void main(String[] args) throws IOException {
        excludedPathAndFiles = new ArrayList<>();
        excludedPathAndFiles.add("modules");
        excludedPathAndFiles.add("target");
        excludedPathAndFiles.add(".idea");
        excludedPathAndFiles.add("node_modules");

        List<String> dir = new ArrayList<>();
        dir.add("C:\\Data\\Java\\TestJava2");
        dir.add("C:\\Data\\Java\\VaadinPobieraczFV-PROD");
        System.out.println(isFileModifyDateBetweenDates(dir.get(0), today.minusWeeks(2), today) ? "Mieści się" : "Nie mieści się");

//        compressToZipDirectory(dir);
    }

    public static boolean isFileModifyDateBetweenDates(String filePath, LocalDateTime beginDate, LocalDateTime endDate) {
        LocalDateTime lastModifyDate = LocalDateTime.ofInstant(new Date(new File(filePath).lastModified()).toInstant(), ZoneId.systemDefault());
        return (beginDate.isBefore(lastModifyDate) && endDate.isAfter(lastModifyDate));
    }

    public static void compressToZipDirectory(List<String> dirs) {
        if (dirs.size() == 0) System.out.println("Brak katalogów do kompresji");
        dirs.forEach(dir -> {
            File srcDir = new File(dir);
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(srcDir.getName() + "_" + today.format(dateTimeFormatter) + ".zip");
                zos = new ZipOutputStream(new BufferedOutputStream(fos));
                addDirectory(srcDir, srcDir);
                zos.close();
                fos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void addDirectory(File baseDir, File dir) throws IOException {
        File[] files = dir.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isDirectory()) {
                addDirectory(baseDir, file);
                continue;
            }

            String name = file.getCanonicalPath().substring(baseDir.getCanonicalPath().length() + 1);
            boolean isPathFound = false;
            for (String ex : excludedPathAndFiles) {
                if (name.contains(ex)) {
                    isPathFound = true;
                    System.out.println("wykluczam: " + file.getCanonicalPath());
                    break;
                }
            }
            if (isPathFound) continue;

            FileInputStream fis = new FileInputStream(file);
            ZipEntry zipEntry = new ZipEntry(name);
            zos.putNextEntry(zipEntry);

            byte[] bytes = new byte[4096];
            int length;
            System.out.print(file.getPath() + " ");
            while ((length = fis.read(bytes)) >= 0) {
                zos.write(bytes, 0, length);
//                System.out.print(".");
            }

            zos.closeEntry();
            System.out.println();
            fis.close();
        }
    }
}
