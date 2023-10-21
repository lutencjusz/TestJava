import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class DemoClass  {
    private int intA;
    private String stringB;

    public DemoClass(int intA, String stringB) {
        this.intA = intA;
        this.stringB = stringB;
    }
}
