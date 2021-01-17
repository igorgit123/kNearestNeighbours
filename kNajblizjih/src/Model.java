import java.util.Arrays;

public class Model {
    String ime;
    double [] atributi;

    @Override
    public String toString() {
        return "Model{" +
                "ime='" + ime + '\'' +
                ", atributi=" + Arrays.toString(atributi) +
                '}';
    }

    public Model(String ime, double[] atributi) {
        this.ime = ime;
        this.atributi = atributi;
    }

    public Model(String razred) {
        this.ime = razred;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public double[] getAtributi() {
        return atributi;
    }

    public void setAtributi(double[] atributi) {
        this.atributi = atributi;
    }
}
