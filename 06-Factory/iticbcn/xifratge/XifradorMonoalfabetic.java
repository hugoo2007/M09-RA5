
package iticbcn.xifratge;
import java.util.*;
public class XifradorMonoalfabetic implements Xifrador {
    public String caracters = "aáàbcçdeéèfghiíìïjklmnñoóòpqrstuúùüvwxyz";
    public char[] Alfabet = caracters.toUpperCase().toCharArray();
    public char[] Permuted = new char[Alfabet.length];
    public String[] Tests = {"Test 01 àrbritre, coixí, Perímetre", "Test 02 Taüll, DÍA, año", "Test 03 Peça, Òrrius, Bòlivia"};
    
    public char[] PermutaAlfabet(char[] alfabet) {
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

    public String XifraMonoAlfa(String chain) {
        StringBuilder permuted = new StringBuilder();
        for(int i = 0; i < chain.length(); i++) {
            char caracter = chain.charAt(i);
            boolean min = Character.isUpperCase(caracter) ? false : true;
            int pos = Troba(caracter, true);
            permuted.append(Character.isLetter(caracter) ? min ? Character.toLowerCase(Permuted[pos]) : Permuted[pos] : caracter);
        }
        return permuted.toString();
    }

    public String DesxifraMonoAlfabet(String cadena) {
        StringBuilder solved = new StringBuilder();

        for(int i = 0; i < cadena.length(); i++) {
            char caracter = cadena.charAt(i);
            boolean min = Character.isUpperCase(caracter) ? false : true;
            int pos = Troba(caracter, false);
            solved.append(Character.isLetter(caracter) ? min ? Character.toLowerCase(Alfabet[pos]) : Alfabet[pos] : caracter);
        }
        return solved.toString();
    }

    public int Troba(char caracter, boolean permutar) {
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

    public XifradorMonoalfabetic() {
        this.Permuted = PermutaAlfabet(Alfabet);
    }

    @Override
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        if(clau != null) throw new ClauNoSuportada("Monoalfabètic no accepta clau != null");
        return new TextXifrat(XifraMonoAlfa(msg).getBytes());
    }
    @Override
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        if(clau != null) throw new ClauNoSuportada("Monoalfabètic no accepta clau != null");
        return DesxifraMonoAlfabet(xifrat.toString());
    }
}