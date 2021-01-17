import java.util.Arrays;

public class Klasificirane {
    double [] atributiTestne;
    String imeTestne;
    String imeKlasifikacije;

    public Klasificirane(double[] atributiTestne, String imeTestne, String imeKlasifikacije) {
        this.atributiTestne = atributiTestne;
        this.imeTestne = imeTestne;
        this.imeKlasifikacije = imeKlasifikacije;
    }

    public double[] getAtributiTestne() {
        return atributiTestne;
    }

    public void setAtributiTestne(double[] atributiTestne) {
        this.atributiTestne = atributiTestne;
    }

    public String getImeTestne() {
        return imeTestne;
    }

    public void setImeTestne(String imeTestne) {
        this.imeTestne = imeTestne;
    }

    public String getImeKlasifikacije() {
        return imeKlasifikacije;
    }

    public void setImeKlasifikacije(String imeKlasifikacije) {
        this.imeKlasifikacije = imeKlasifikacije;
    }


    @Override
    public String toString() {
        return "Klasificirane{" +
                "atributiTestne=" + Arrays.toString(atributiTestne) +
                ", imeTestne='" + imeTestne + '\'' +
                ", imeKlasifikacije='" + imeKlasifikacije + '\'' +
                '}';
    }
}
