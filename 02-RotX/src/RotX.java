public class RotX {
    public static String caracters = "aáàbcçdeéèfghiíìïjklmnñoóòpqrstuúùüvwxyz";
    public static char[] minuscules = caracters.toCharArray();
    public static char[] majuscules = caracters.toUpperCase().toCharArray();
    
    public static void main(String[] args) {
        String[] msgs = {"ABC","XYZ","Hola, Mr. calçot", "Perdó, per tu què és?"};
        int[] pos = {0, 2, 4, 6};
        String[] msgsXifrats = new String[msgs.length];
        for(int i = 0; i < majuscules.length; i++) {
            if(i < pos.length) {
                msgsXifrats[i] = xifraRotX(msgs[i], pos[i]);
            } else {
                break;
            }
        }
        for(String msg : msgsXifrats) {
            System.out.println(msg);
        }
    }

    public static String xifraRotX(String cadena, int desplaçament) {
        String xifrat= "";
        if(desplaçament == 0) {
            System.out.println(cadena);
            return cadena;
        }
        for(int i = 0; i < cadena.length(); i++) {
            char caracter = cadena.charAt(i);
            boolean maj = false;
            boolean min = false;
            if(isLetter(caracter)) {
                int pos = 0;
                for(int j = 0; j < majuscules.length; j++) {
                    if(caracter == majuscules[j]) {
                        System.out.println("Es maj");
                        maj = true;
                        min = false;
                        pos = j;
                        break;
                    } else if(caracter == minuscules[j]) {
                        System.out.println("Es min");
                        min = true;
                        maj = false;
                        pos = j;
                        break;
                    }
                }
                int xifr = (pos + desplaçament) % majuscules.length;
                if(maj) xifrat+=majuscules[xifr];
                else if(min) xifrat+=minuscules[xifr]; 
            } else xifrat+=caracter;
        }
       return xifrat;
    }

    public static String desxifraRotX(String cadenaXifrada, int desplaçament) {
        String desxifrat = "";
        for(int i = 0; i < cadenaXifrada.length(); i++) {
            char caracter = cadenaXifrada.charAt(i);
            if(isLetter(caracter)) {
                for(int j = 0; j < majuscules.length; j++) {
                    if(caracter)
                }
            } else {
                desxifrat+=caracter;
            }
        }
    }

    public static boolean isLetter(char caracter) {
        for(int i = 0; i < majuscules.length; i++) {
            if(caracter == minuscules[i] || caracter == majuscules[i]) return true;
        }
        return false;
    }
}