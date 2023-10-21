import java.util.ArrayList;

public class Generic {

    private static void showValue(GenericValue<String> value) {
        System.out.println(value.getGenericValue());
    }

    public static void main(String[] args) {
        GenericValue<Integer> localValue = new GenericValue<>(4);
        System.out.println("Wartość zmiennej lokalnej: " + localValue.getGenericValue());
        localValue.setGenericValue(6);
        System.out.println("Wartość zmiennej lokalnej po modyfikacji: " + localValue.getGenericValue());

        GenericValue<String> localString = new GenericValue<>("Wartość początkowa");
        System.out.println("Wartość zmiennej lokalnej typu String: " + localString.getGenericValue());
        localString.setGenericValue(localString.getGenericValue() + " zmieniona wartość");
        System.out.println("Wartość zmiennej lokalnej typu String, zmieniona: " + localString.getGenericValue());

        ArrayList<GenericValue<String>> listGenericValue = new ArrayList<>();
        listGenericValue.add(new GenericValue<>("Zmienna 1"));
        listGenericValue.add(new GenericValue<>("Zmienna 2"));
        listGenericValue.add(new GenericValue<>("Zmienna 4"));
        listGenericValue.forEach(Generic::showValue);
    }
}

