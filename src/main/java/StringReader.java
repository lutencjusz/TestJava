import java.io.IOException;
import java.io.StringWriter;

class StringReader {
    public static void main(String[] args) throws IOException {
        java.io.StringReader sr = new java.io.StringReader("Nowy ciąg, który jest stringiem");
        StringWriter sw = new StringWriter(0);
        sr.transferTo(sw);
        System.out.println(sw + ", dupa");
    }

}
