public class RotX {
    public static String caracters = "aáàbcçdeéèfghiíìïjklmnñoóòpqrstuúùüvwxyz";
    public static char[] minuscules = caracters.toCharArray();
    public static char[] majuscules = caracters.toUpperCase().toCharArray();
    
    public static void main(String[] args) {
        String[] msgs = {"ABC","XYZ","Hola, Mr. calçot", "Perdó, per tu què és?"};
        int[] pos = {0, 2, 4, 6};
        String[] msgsXifrats = new String[msgs.length];

        System.out.println("\nXifrat\n---------");
        for(int i = 0; i < msgs.length; i++) {
            msgsXifrats[i] = xifraRotX(msgs[i], pos[i]);
            System.out.printf("(%d) %-23s => %s%n", pos[i], msgs[i], msgsXifrats[i]);
        }

        System.out.println("\nDesxifrat\n---------");
        int desxfr = 0;
        for(String msg: msgsXifrats) {
            System.out.printf("(%d) %-23s => %s%n", desxfr, msg, desxifraRotX(msg, desxfr));
            desxfr+=2;
        }

        System.out.println();
        forcaBrutaRotX(msgsXifrats[msgsXifrats.length - 1]);
    }

    public static String xifraRotX(String cadena, int desplaçament) {
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

    public static String desxifraRotX(String cadenaXifrada, int desplaçament) {
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

    public static void forcaBrutaRotX(String cadenaXifrada) {
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

    public static int isLetter(char caracter) {
        for(int i = 0; i < majuscules.length; i++) {
            if(caracter == minuscules[i] || caracter == majuscules[i]) return i;
        }
        return -1;
    }

    public static char rota(char caracter, int posicio, int desplaçament, boolean right) {
        int rotated = right ? (posicio + desplaçament) % majuscules.length : (((posicio - desplaçament) + majuscules.length) % majuscules.length);
        return Character.isUpperCase(caracter) ? majuscules[rotated] : minuscules[rotated];
    }
} 