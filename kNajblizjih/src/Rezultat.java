public class Rezultat {
    double razdalja;
    String odKod;

    public Rezultat(double razdalja, String odKod) {
        this.razdalja = razdalja;
        this.odKod = odKod;
    }

    public double getRazdalja() {
        return razdalja;
    }

    public void setRazdalja(double razdalja) {
        this.razdalja = razdalja;
    }

    public String getOdKod() {
        return odKod;
    }

    public void setOdKod(String odKod) {
        this.odKod = odKod;
    }

    @Override
    public String toString() {
        return "Rezultat{" +
                "razdalja=" + razdalja +
                ", odKod='" + odKod + '\'' +
                '}';
    }
}
