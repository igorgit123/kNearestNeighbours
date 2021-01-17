import javax.xml.transform.Result;
import java.util.Comparator;

public class DistanceComparator implements Comparator<Rezultat> {

        @Override
        public int compare(Rezultat a, Rezultat b) {
            return a.razdalja < b.razdalja ? -1 : a.razdalja == b.razdalja ? 0 : 1;
        }
}

