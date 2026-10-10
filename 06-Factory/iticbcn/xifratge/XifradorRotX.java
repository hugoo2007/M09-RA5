package iticbcn.xifratge;

public class XifradorRotX implements Xifrador {
    public String caracters = "aáàbcçdeéèfghiíìïjklmnñoóòpqrstuúùüvwxyz";
    public char[] minuscules = caracters.toCharArray();
    public char[] majuscules = caracters.toUpperCase().toCharArray();

    public String xifraRotX(String cadena, int desplaçament) throws Exception {
        String xifrat= "";
        if(desplaçament == 0) {
            return cadena;
        }
        for(int i = 0; i < cadena.length(); i++) {
            char caracter = cadena.charAt(i);
            xifrat+=isLetter(caracter) != -1 ? rota(caracter, isLetter(caracter), desplaçament, true) : caracter;
        }
       return xifrat;
    }

    public String desxifraRotX(String cadenaXifrada, int desplaçament) throws Exception {
        String desxifrat = "";
        if(desplaçament == 0) {
            return cadenaXifrada;
        }
        for(int i = 0; i < cadenaXifrada.length(); i++) {
            char caracter = cadenaXifrada.charAt(i);
            desxifrat+= isLetter(caracter) != -1  ? rota(caracter, isLetter(caracter), desplaçament, false) : caracter;
        }
        return desxifrat;
    }

    public void forcaBrutaRotX(String cadenaXifrada) {
        System.out.printf("Missatge xifrat: %s%n--------%n", cadenaXifrada);
        int length = majuscules.length;
        for(int a = 0; a < length; a++) {
            if(a == 0) {
                System.out.printf("(%d)->%s%n ", a, cadenaXifrada);
                continue;
            }
            String intentDesxifrar = "";
            for(int i = 0; i < cadenaXifrada.length(); i++) {
                char caracter = cadenaXifrada.charAt(i);
                intentDesxifrar += isLetter(caracter) != -1 ? rota(caracter, isLetter(caracter), a, false) : caracter;
            }
            System.out.printf("(%d)->%s%n", a, intentDesxifrar);
        }
    }

    public int isLetter(char caracter) {
        for(int i = 0; i < majuscules.length; i++) {
            if(caracter == minuscules[i] || caracter == majuscules[i]) return i;
        }
        return -1;
    }

    public char rota(char caracter, int posicio, int desplaçament, boolean right) {
        int rotated = right ? (posicio + desplaçament) % majuscules.length : (((posicio - desplaçament) + majuscules.length) % majuscules.length);
        return Character.isUpperCase(caracter) ? majuscules[rotated] : minuscules[rotated];
    }

    @Override 
    public TextXifrat xifra(String msg, String clau) throws ClauNoSuportada {
        try {
            int clauInt = Integer.parseInt(clau);
            if(clauInt < 0 || clauInt > 40) throw new Exception();
            return new TextXifrat(xifraRotX(msg, clauInt).getBytes());
        } catch (Exception e) {
            throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
        } 
    }

    @Override 
    public String desxifra(TextXifrat xifrat, String clau) throws ClauNoSuportada {
        try {
            int clauInt = Integer.parseInt(clau);
            if(clauInt < 0 || clauInt > 40) throw new Exception();
            return desxifraRotX(xifrat.toString(), clauInt);
        } catch (Exception e) {
            throw new ClauNoSuportada("Clau de RotX ha de ser un sencer de 0 a 40");
        } 
    }
} 