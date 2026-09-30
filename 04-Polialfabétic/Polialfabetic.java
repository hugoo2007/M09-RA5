import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.List;
public class Polialfabetic {
        private static final int clauSecreta = 10;
        public static Random random;

        public static String caracters = "aáàbcçdeéèfghiíìïjklmnñoóòpqrstuúùüvwxyz";
        public static char[] alphabetArray = caracters.toUpperCase().toCharArray();
        public static char[] permuted = new char[alphabetArray.length]; 

    public static void randomInit(int clauSecreta) {
        random = new Random(clauSecreta);
    }
    public static void main(String[] args) {
        String msgs[] = {"Test 01 àrbitre, coixí, Perimetre", "Test 02 Taüll, DÍA, año", "Test 03 Peça, Òrrius, Bòvila"};
        String msgsXifrats[] = new String[msgs.length];
        System.out.println("Xifratge:\n----------");
        for(int i = 0; i < msgs.length; i++) {
            randomInit(clauSecreta);
            msgsXifrats[i]=xifraPoliAlfa(msgs[i]);
            System.out.printf("%-34s -> %s%n", msgs[i], msgsXifrats[i]);
        }
    }

    public static String xifraPoliAlfa(String cadena) {
        String xifrat = "";
        for(int i = 0; i < cadena.length(); i++) {
            permutaAlfabet(alphabetArray);
            char caracter = cadena.charAt(i);
            int pos = Troba(caracter, true);
            xifrat += Character.isLetter(caracter) ? Character.isUpperCase(caracter) ? permuted[pos] : Character.toLowerCase(permuted[pos]) : caracter;
        }
        return xifrat;
    }

    public static void permutaAlfabet(char[] alfabet) {
        List<Character> alfabetList = new ArrayList<Character>();
        for(char caracter : alfabet) {
            alfabetList.add(caracter);
        }
        Collections.shuffle(alfabetList, random);
        for(int i = 0; i < alfabetList.size(); i++) {
            permuted[i] = alfabetList.get(i);
        }
    }

    public static int Troba(char caracter, boolean permutar) {
        if(permutar) {
            for(int i = 0; i < permuted.length; i++) {
                if(alphabetArray[i] == caracter) return i;
            }
            return -1;
        }
        return 0;
    }
}
