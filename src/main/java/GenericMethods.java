import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GenericMethods {
    public static void wypisz(List<? extends Figura> lista) {

        for (Figura figura : lista)
            System.out.println(figura.pobierzNazwe());


        lista.add(null);
        System.out.println("---------------------");

        for (Figura figura : lista) {
            if (figura == null) {
                System.out.println("null");
                break;
            }
            System.out.println(figura.pobierzNazwe());
        }
    }

    public static <T extends Figura> void wypisz2(List<T> lista) {

        for (T figura : lista)
            System.out.println(figura.pobierzNazwe());

        lista.add((T) new Kolo());
        lista.add((T) new Kolo());
        lista.add((T) new Kolo());
        lista.add((T) new Kolo());
        lista.add(null);
        System.out.println("---------------------");

        for (T figura : lista) {
            if (figura == null) {
                System.out.println("null");
                break;
            }
            System.out.println(figura.pobierzNazwe());
        }
    }

    public static <Type> void przepiszTabliceDoKolekcji(Type[] tab, Collection<Type> c) {
        for (Type o : tab)
            c.add(o);
    }

    public static void main(String[] args) {
        Figura figura1 = new Kolo();
        Figura figura2 = new Kwadrat();
        Figura[] figury = new Figura[50];
        List<Figura> listaFigur = new ArrayList<Figura>();
        listaFigur.add(figura1);
        listaFigur.add(figura2);

        GenericMethods.wypisz2(listaFigur);

        Float[] fl = new Float[50];
        Integer[] in = new Integer[50];
        Number[] nu = new Number[50];
        Collection<Number> cn = new ArrayList<Number>();
        GenericMethods.przepiszTabliceDoKolekcji(fl, cn);
        GenericMethods.przepiszTabliceDoKolekcji(in, cn);
        GenericMethods.przepiszTabliceDoKolekcji(nu, cn);


    }
}

class Figura<K> {
    K lala;

    public String pobierzNazwe() {
        return nazwa;
    }

    String nazwa = "";
}

class Kolo extends Figura {
    public Kolo() {
        super.nazwa = "Kolo";
    }
}

class Kwadrat extends Figura {
    public Kwadrat() {
        super.nazwa = "Kwadrat";
    }
}

