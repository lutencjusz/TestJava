import java.lang.reflect.Field;

import static java.lang.Thread.currentThread;

public class ZmiennaPrzezNazwe {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        DemoClass demoClass = new DemoClass(2, "Ala ma kota");
        String variableStringName = "stringB";
        String variableIntName = "intA";
        Class<?> cls = Class.forName("DemoClass", true, currentThread().getContextClassLoader());
        Field stringField = cls.getDeclaredField(variableStringName);
        Field intField = cls.getDeclaredField(variableIntName);
        stringField.setAccessible(true);
        intField.setAccessible(true);
        Object stringValue = stringField.get(demoClass);
        Object intValue = intField.get(demoClass);
        System.out.println("Wartość zmiennej typu String '" + variableStringName + "' to: " + stringValue);
        System.out.println("Wartość zmiennej typu int '" + variableIntName + "' to: " + intValue);
    }
}
