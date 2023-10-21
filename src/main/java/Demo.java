import lombok.Getter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;

@Getter
class Demo implements Serializable {
    private final int a;
    private final String b;

    private final transient String pass = "Tajne hasło";

    // Default constructor
    public Demo(int a, String b) {
        this.a = a;
        this.b = b;
    }

    @Serial
    private void readObject (ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        inputStream.defaultReadObject();
        if (!pass.equals("Tajne hasło")) throw new IOException("Hasło jest różne");
    }

}
