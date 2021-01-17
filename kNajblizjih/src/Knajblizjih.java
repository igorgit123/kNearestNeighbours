import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Knajblizjih {
    static double [] recVsakeVrstice;
    static double [] precVsakegaStolpca;

    public static void main(String[] args) {
        int k=5;
        String filenameUcna= "C:\\Users\\igorj\\Downloads\\iris_ucna.csv";
        String filenameTestna= "C:\\Users\\igorj\\Downloads\\iris_testna.csv";


        List <String[]> ucna= readCSV(filenameUcna);
        List <String[]> testna= readCSV(filenameTestna);


        ArrayList<Model> ucnaModel = new ArrayList<Model>();
        ArrayList<Model> testnaModel = new ArrayList<Model>();

        ucnaModel = ucnaToModel(ucna);
        testnaModel = ucnaToModel(testna);

        ArrayList<Rezultat> razdalje = new ArrayList<Rezultat>();
        ArrayList<Klasificirane> klasificirane = new ArrayList<Klasificirane>();

/*
        for(int i=0; i<ucnaModel.size(); i++){
            System.out.println(ucnaModel.get(i).toString());
        }
*/
        String [] razredi = new String[ucnaModel.size()];
        for(int i=0; i<ucnaModel.size(); i++){

            razredi[i]=ucnaModel.get(i).getIme();
        }
        String[] unique = Arrays.stream(razredi).distinct().toArray(String[]::new);

        double [][] zmeda = new double[unique.length][unique.length];




        for(Model t: testnaModel ){

            for(Model u : ucnaModel){
                double dist = 0.0;
                for(int j = 0; j < u.getAtributi().length; j++){

                    dist += Math.pow(u.getAtributi()[j] - t.getAtributi()[j], 2) ;

                }
                double distance = Math.sqrt( dist );

                razdalje.add(new Rezultat(distance,u.getIme()));

            }
            Collections.sort(razdalje, new DistanceComparator());


            String[] kRazredov = new String[k];
            for(int i = 0; i < k; i++){

                kRazredov[i] = razdalje.get(i).getOdKod();

            }



            String najpogostejsi = mostFrequent(kRazredov);


            klasificirane.add(new Klasificirane(t.getAtributi(), t.getIme(), najpogostejsi));

            razdalje.clear();


        }



        for(Klasificirane klas: klasificirane){
            zmeda[findIndex(unique, klas.imeTestne)][findIndex(unique, klas.imeKlasifikacije)] += 1;

        }

        System.out.println("Vrednost k: "+ k);
        System.out.println("Datoteka ucne mnozice: "+ filenameUcna);
        System.out.println("Datoteka testne mnozice: "+ filenameTestna);
        System.out.println("Confusion Matrix:");
        System.out.println();
        for(int i=0; i<unique.length; i++){
            System.out.print(unique[i]+"   ");
        }
        System.out.println();
        for(int i=0; i<zmeda.length; i++){
            for(int j=0;j<zmeda[i].length;j++){

                System.out.print((int)zmeda[i][j]+ "      ");

            }
            System.out.println(unique[i]);
        }
        System.out.println();
        System.out.println("Accuracy: "+ accuracy(zmeda, klasificirane.size()));
        System.out.println();
        System.out.println("Precision: "+ precision(zmeda));
        System.out.println();
        System.out.println("Recall: " + recall(zmeda));
        System.out.println();
        System.out.println("F-Score: "+ fscore(precVsakegaStolpca, recVsakeVrstice));



    }

    private static double fscore(double[] precVsakegaStolpca, double[] recVsakeVrstice) {
        double [] fscoreVsakeVrstice = new double[recVsakeVrstice.length];
        double fscoreSum=0;

        for(int i=0; i<fscoreVsakeVrstice.length; i++){

            fscoreVsakeVrstice[i]= 2*((precVsakegaStolpca[i]*recVsakeVrstice[i])/(precVsakegaStolpca[i]+recVsakeVrstice[i]));
            fscoreSum+=fscoreVsakeVrstice[i];

        }






        return fscoreSum/fscoreVsakeVrstice.length;
    }

    private static double recall(double[][] zmeda) {
        double sestevek=0;
        double [] sumVrstic= new double[zmeda.length];
        double [] diagVrednosti= new double[zmeda.length];
        recVsakeVrstice= new double[zmeda.length];

        for(int i=0; i<zmeda.length; i++){
            for(int j=0; j<zmeda[i].length;j++) {

                sumVrstic[i]= sumVrstic[i]+zmeda[i][j];
                if(i==j){
                    diagVrednosti[i]=zmeda[i][j];
                }
            }
        }

        for(int i=0; i<zmeda.length; i++){
            recVsakeVrstice[i]= diagVrednosti[i]/sumVrstic[i];
           sestevek+=recVsakeVrstice[i];
        }



        return sestevek/recVsakeVrstice.length;
    }

    public static double precision(double[][] zmeda) {
        double sestevek=0;
        double [] sumStolpcev= new double[zmeda.length];
        double [] diagVrednosti= new double[zmeda.length];
        precVsakegaStolpca= new double[sumStolpcev.length];

        for(int i=0; i<zmeda.length; i++){
            for(int j=0; j<zmeda[i].length;j++) {

                sumStolpcev[j]= sumStolpcev[j]+zmeda[i][j];
                if(i==j){
                    diagVrednosti[i]=zmeda[i][j];
                }
            }
        }
        for(int i=0; i<diagVrednosti.length; i++){
            precVsakegaStolpca[i]= diagVrednosti[i]/sumStolpcev[i];
            sestevek+=precVsakegaStolpca[i];
        }





        return sestevek/precVsakegaStolpca.length;
    }

    public static double accuracy(double[][] zmeda, int vse) {
        double acc=0;
        double sumDiagonale=0;

        for(int i=0; i<zmeda.length; i++){
            for(int j=0; j<zmeda[i].length; j++){
                if(i==j){
                    sumDiagonale += zmeda[i][j];
                }
            }
        }
        acc=sumDiagonale/ vse;


        return acc;
    }

    //branje podatkov
    private static ArrayList<Model> ucnaToModel(List<String[]> ucna) {
        ArrayList<Model> ucnaModel = new ArrayList<Model>();
        double [] p ;
        String ime="";

        for(int i=0; i<ucna.size();i++){
            p=new double[ucna.get(1).length-1];
            if(i!=0){
                for(int j=0;j< ucna.get(i).length; j++){
                    if(j==ucna.get(i).length-1){
                        ime=ucna.get(i)[j];
                    }else{
                        p[j]=Double.parseDouble(ucna.get(i)[j]);
                  }
                }
                ucnaModel.add(new Model(ime, p));
            }

        }
        return ucnaModel;
    }

    public static List<String[]> readCSV(String filename) {
        List<String[]> result = new ArrayList<>();
        BufferedReader br = null;
        String line = "";

        try {
            br = new BufferedReader(new FileReader(filename));

            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    String[] strings = line.split(","); // razdelimo z vejicami
                    result.add(strings);
                }
            }

            br.close();
        } catch (Exception e) { e.printStackTrace(); }

        return result;
    }



    public static void izpisCsv(List <String[]> csv){
        for(int i=0; i<csv.size(); i++){
            for(int j=0; j<csv.get(i).length; j++){

                System.out.print(csv.get(i)[j]);

            }
            System.out.println();
        }


    }


    static String mostFrequent(String arr[])
    {
        int n= arr.length;
        // Sort the array
        Arrays.sort(arr);

        // find the max frequency using linear
        // traversal
        int max_count = 1;
        String res = arr[0];
        int curr_count = 1;

        for (int i = 1; i < n; i++)
        {
            if (arr[i].equals(arr[i - 1]) )
                curr_count++;
            else
            {
                if (curr_count > max_count)
                {
                    max_count = curr_count;
                    res = arr[i - 1];
                }
                curr_count = 1;
            }
        }

        // If last element is most frequent
        if (curr_count > max_count)
        {
            max_count = curr_count;
            res = arr[n - 1];
        }

        return res;
    }

    public static int findIndex(String arr[], String t)
    {

        // if array is Null
        if (arr == null) {
            return -1;
        }

        // find length of array
        int len = arr.length;
        int i = 0;

        // traverse in the array
        while (i < len) {

            // if the i-th element is t
            // then return the index
            if (arr[i].equals(t) ) {
                return i;
            }
            else {
                i = i + 1;
            }
        }
        return -1;
    }






}
