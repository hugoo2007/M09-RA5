package iticbcn.xifratge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.List;
public class XifradorPolialfabetic implements Xifrador {
        public Random random;

        public String caracters = "aáàbcçdeéèfghiíìïjklmnñoóòpqrstuúùüvwxyz";
        public char[] alphabetArray = caracters.toUpperCase().toCharArray();
        public char[] permuted = new char[alphabetArray.length]; 

    public void randomInit(int clauSecreta) {
        random = new Random(clauSecreta);
    }

    public void permutaAlfabet(char[] alfabet) {
        List<Character> alfabetList = new ArrayList<Character>();
        for(char caracter : alfabet) {
            alfabetList.add(caracter);
        }
        Collections.shuffle(alfabetList, random);
        for(int i = 0; i < alfabetList.size(); i++) {
            permuted[i] = alfabetList.get(i);
        }
    }

    public String hibridRotation(String text, boolean xifra) {
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

    public int Troba(char caracter, boolean permutar) {
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
