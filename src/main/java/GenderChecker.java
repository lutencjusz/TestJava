public class GenderChecker {
    private static String determineGender(String name, String nameList) {
        String prefix = "Pan";
        String[] names = nameList.split(", ");
        for (String n : names) {
            if (n.equalsIgnoreCase(name)) return "Pani";
        }
        if (name.endsWith("a")) {
            return "Pani";
        } else {
            return "Pan";
        }
    }

    public static void main(String[] args) {

        String femaleNameList = "Ada, Adrianna, Agata, Agnieszka, Aleksandra, Alicja, Alina, Amelia, Aneta, Angelika, Anna, Antonina, Barbara, Basia, Beata, Blanka, Bogna, Bożena, Dagmara, Danuta, Dominika, Dorota, Edyta, Eliza, Elżbieta, Emilia, Ewa, Gabriela, Hanna, Halina, Irena, Izabela, Jadwiga, Jagoda, Jagienka, Jagoda, Janina, Joanna, Jolanta, Julia, Julita, Justyna, Kamila, Karolina, Kasia, Katarzyna, Kinga, Klaudia, Kornelia, Krystyna, Laura, Lidia, Liliana, Lucja, Magdalena, Małgorzata, Maria, Marta, Martyna, Melania, Monika, Natalia, Olga, Oliwia, Patrycja, Paulina, Renata, Rozalia, Sylwia, Teresa, Urszula, Wanda, Weronika, Zofia, Zuzanna";
        String firstName = "Marek"; // Zmień na dowolne imię do sprawdzenia
        String genderForm = determineGender(firstName, femaleNameList);
        System.out.println("Forma grzecznościowa: " + genderForm);
    }
}
