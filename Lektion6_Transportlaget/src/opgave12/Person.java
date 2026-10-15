package opgave12;

public class Person {
    /**
     * Opgave 12
     * I denne opgave skal vi prøve at sende en ArrayList.
     * Start med at lave en personklasse med attributter id (int), navn (String) og by (String).
     * Navn og by indeholder ikke specialtegnene # eller ¤ - undlad at bruge andre specialtegn, da disse
     * kan anvendes af RegEx.
     * Lav en ArrayList, der indeholder nogle få person-objekter.
     * Lav herefter to metoder:
     */

    private int id;
    private String navn;
    private String by;

    public Person(int id, String navn, String by) {
        this.id = id;
        this.navn = navn;
        this.by = by;
    }

    public int getId() {
        return id;
    }

    public String getNavn() {
        return navn;
    }

    public String getBy() {
        return by;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", navn='" + navn + '\'' +
                ", by='" + by + '\'' +
                "}\n";
    }
}
