import java.util.*;

public class Monoalfabetic {
    public static String caracters = "aáàbcçdeéèfghiíìïjklmnñoóòpqrstuúùüvwxyz";
    public static char[] Alfabet = caracters.toUpperCase().toCharArray();
    public static char[] Permuted = new char[Alfabet.length];
    public static String[] Tests = {"Test01 àrbritre, coixí, Perímetre", "Test02 Taüll, DÍA, año", "Test03 Peça, Òrrius, Bòlivia"};
    
    public static char[] PermutaAlfabet(char[] alfabet) {
        List<Character> swifted = new ArrayList<>();
        for(char caracter : alfabet) {
            swifted.add(caracter);
        }
        Collections.shuffle(swifted);  
        char[] swiftedArray = new char[Alfabet.length];
        for(int i = 0; i < Alfabet.length; i++) {
            swiftedArray[i] = swifted.get(i);
        }
        return swiftedArray; 
    }

    public static String XifraMonoAlfa(String chain) {
        StringBuilder permuted = new StringBuilder();
        for(int i = 0; i < chain.length(); i++) {
            char caracter = chain.charAt(i);
            boolean min = Character.isUpperCase(caracter) ? false : true;
            int pos = Troba(caracter, true);
            permuted.append(Character.isLetter(caracter) ? min ? Character.toLowerCase(Permuted[pos]) : Permuted[pos] : caracter);
        }
        return permuted.toString();
    }

    public static String DesxifraMonoAlfabet(String cadena) {
        StringBuilder solved = new StringBuilder();

        for(int i = 0; i < cadena.length(); i++) {
            char caracter = cadena.charAt(i);
            boolean min = Character.isUpperCase(caracter) ? false : true;
            int pos = Troba(caracter, false);
            solved.append(Character.isLetter(caracter) ? min ? Character.toLowerCase(Alfabet[pos]) : Alfabet[pos] : caracter);
        }
        return solved.toString();
    }

    public static int Troba(char caracter, boolean permutar) {
        int pos = 0;
        for(int i = 0; i < Permuted.length; i++) {
            if(permutar) {
                if(Character.toUpperCase(caracter) == Alfabet[i]) {
                    pos = i;
                    break;
                }
            } else {
                if(Character.toUpperCase(caracter) == Permuted[i]) {
                    pos = i;
                    break;
                }
            }
        }
        return pos;
    }

    public static void main(String[] args) {
        Permuted = PermutaAlfabet(Alfabet);
        for(int i = 0; i < Alfabet.length; i++) {
            System.out.print(Alfabet[i]);
        }
        System.out.println();
        for(int i = 0; i < Alfabet.length; i++) {
            System.out.print(Permuted[i]);
        }
        System.out.println();
        System.out.println("Xifratge: ");

        String[] encriptedTests = new String[Tests.length];
        for(int i = 0; i < Tests.length; i++) {
            String encriptedTest = XifraMonoAlfa(Tests[i]);
            System.out.printf("%-35s -> %s%n", Tests[i], encriptedTest);
            encriptedTests[i] = encriptedTest;
        }
        System.out.println();
        System.out.println("Desxifratge: ");
        for(int i = 0; i < encriptedTests.length; i++) {
            System.out.printf("%-35s -> %s%n", encriptedTests[i], DesxifraMonoAlfabet(encriptedTests[i]));
        }
    }
}