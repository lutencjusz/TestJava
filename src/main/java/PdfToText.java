import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.text.PDFTextStripper;
import technology.tabula.ObjectExtractor;
import technology.tabula.Page;
import technology.tabula.RectangularTextContainer;
import technology.tabula.Table;
import technology.tabula.extractors.BasicExtractionAlgorithm;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PdfToText {
    public static void main(String[] args) {
        String filePath = "C:\\Data\\Java\\TestJava\\fv1.pdf";
        try {
            PDDocument document = PDDocument.load(new File(filePath));
            String textPdf = extractTextFromPagePDF(document, 1);
            System.out.println("Tekst z pliku PDF:\n" + textPdf);
            System.out.println("Wartość z klucza 'Faktura Nr ': '" + getValueFromText(textPdf, "Faktura Nr ") + "'");
            String[][] tableData = extractTableFromPDF(document, 1);
            printTableData(tableData);
            document.close();
        } catch (IOException e) {
            System.err.println("Wystąpił błąd podczas przetwarzania pliku PDF: " + e.getMessage());
        }
    }

    private static String extractTextFromAllPDF(PDDocument document) throws IOException {
        PDFTextStripper pdfTextStripper = new PDFTextStripper();
        return pdfTextStripper.getText(document);
    }

    private static String extractTextFromPagePDF(PDDocument document, int pageNumber) throws IOException {
        PDFTextStripper pdfTextStripper = new PDFTextStripper();
        pdfTextStripper.setStartPage(pageNumber);
        pdfTextStripper.setEndPage(pageNumber);
        return pdfTextStripper.getText(document);
    }

    private static String getValueFromText(String text, String key) {
        if (text == null || key == null) {
            return null;
        }

        String[] lines = text.split("\n");
        for (String line : lines) {
            if (line.contains(key)) {
                return line.substring(line.indexOf(key) + key.length()).trim();
            }
        }
        return null;
    }

    private static String[][] extractTableFromPDF(PDDocument document, int pageNumber) throws IOException {

        ObjectExtractor objectExtractor = new ObjectExtractor(document);
        Page page = objectExtractor.extract(pageNumber);
        PDPage pdPage = document.getPage(pageNumber - 1);

        // Podaj wymiary strony
        PDRectangle pageSize = pdPage.getMediaBox();
        float pageWidth = pageSize.getWidth();
        float pageHeight = pageSize.getHeight();
        System.out.println("Wymiary strony: x: " + pageWidth + " y: " + pageHeight);

//        float x = 358;
//        float y = 312;
//        float x2 = 570;
//        float y2 = 357;
//        List<Float> tableAreaCoordinates = Arrays.asList(x, y, x2, y2);
//        List<Table> tables = basicExtractionAlgorithm.extract(page, tableAreaCoordinates);

        // Utwórz algorytm ekstrakcji tabel
        BasicExtractionAlgorithm basicExtractionAlgorithm = new BasicExtractionAlgorithm();
        List<Table> tables = basicExtractionAlgorithm.extract(page);

        // Przekształć pierwszą tabelę na strukturę tablicową String[][]
        Table table = tables.get(0);
        String[][] tableData = new String[table.getRowCount()][table.getColCount()];
        for (int i = 0; i < table.getRowCount(); i++) {
            for (int j = 0; j < table.getColCount(); j++) {
                RectangularTextContainer<?> cell = table.getCell(i, j);
                if (cell != null) {
                    tableData[i][j] = cell.getText();
                }
            }
        }

        return tableData;
    }

    private static void printTableData(String[][] tableData) {
        for (String[] row : tableData) {
            System.out.println("--------------------");
            for (String cell : row) {
                System.out.print(cell + "|");
            }
            System.out.println();
        }
    }
}
