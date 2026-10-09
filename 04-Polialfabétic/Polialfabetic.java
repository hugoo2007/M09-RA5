import java.util.ArrayList;
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
        String msgsDesxifrats[] = new String[msgs.length];
        System.out.println("Xifratge:\n----------");
        for(int i = 0; i < msgs.length; i++) {
            randomInit(clauSecreta);
            msgsXifrats[i]=hibridRotation(msgs[i], true);
            System.out.printf("%-34s -> %s%n", msgs[i], msgsXifrats[i]);
        }
        System.out.println("Desxifratge:\n----------");
        for(int i = 0; i < msgsXifrats.length; i++) {
            randomInit(clauSecreta);
            msgsDesxifrats[i]=hibridRotation(msgsXifrats[i], false);
            System.out.printf("%-34s -> %s%n", msgsXifrats[i], msgsDesxifrats[i]);
        }
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

    public static String hibridRotation(String text, boolean xifra) {
        String cadena = "";
        for(int i = 0; i < text.length(); i++) {
            permutaAlfabet(alphabetArray);
            char caracter = text.charAt(i);
            int pos = Troba(caracter, xifra == true ? true : false);
            if(xifra) cadena+=Character.isLetter(caracter) ? Character.isUpperCase(caracter) ? permuted[pos] : Character.toLowerCase(permuted[pos]): caracter;
            else cadena+=Character.isLetter(caracter) ? Character.isUpperCase(alphabetArray[pos]) ? alphabetArray[pos] : Character.toUpperCase(alphabetArray[pos]): caracter;
        }
        return cadena;
    }

    public static int Troba(char caracter, boolean permutar) {
        if(permutar) {
            for(int i = 0; i < permuted.length; i++) {
                if(alphabetArray[i] == Character.toUpperCase(caracter)) return i;
            }
        } else if(!permutar) {
            for(int i = 0; i < alphabetArray.length; i++) {
                if(permuted[i] == Character.toUpperCase(caracter)) return i;
            }
        }
        return 0;
    }
}
