public class Root13 {
    static String chars = "aáàbcçdeéèfghiíìïjklmnñoóòpqrstuúùüvwxyz";
    public static char[] majuscules = chars.toUpperCase().toCharArray();
    public static char[] minuscules = chars.toCharArray();

    public static void main(String[] args) {
        String[] msgs = {"ABC","XYZ","Hola, Mr. calçot", "Perdó, per tu què és?"};
        String[] msgsXifrats = new String[msgs.length];

        System.out.println("\nXifrat\n---------");
        for(int i = 0; i < msgs.length; i++) {
            msgsXifrats[i] = xifraRot13(msgs[i]);
            System.out.printf("%-23s => %s%n", msgs[i], msgsXifrats[i]);
        }
        System.out.println("\nDesxifrat\n---------");
        for(String msg: msgsXifrats) {
            System.out.printf("%-23s => %s%n", msg, desxifraRot13(msg));
        }

    }

    public static String xifraRot13(String chain) {
        String xifrat = "";
        for(int i = 0; i < chain.length(); i++) {
            char caracter = chain.charAt(i);
            int newPos = 0;
            int posArray = 0;
            boolean upper = false;
            boolean lower = false;
            if(IsLetter(caracter)) {
            for(int j = 0; j < majuscules.length; j++) {
                if(majuscules[j] == caracter) {
                    posArray = j;
                    upper = true;
                    lower = false;
                    break;
                } else if(minuscules[j] == caracter) {
                    posArray = j;
                    lower = true;
                    upper = false;
                    break;
                }
            }
            newPos = (posArray + 13) % majuscules.length;
            if(upper) xifrat+=majuscules[newPos];
            else if(lower) xifrat+=minuscules[newPos];
            } else {
                xifrat+=caracter;
            }
        }
        return xifrat;
    }

    public static String desxifraRot13(String chain) {
        String desxifrat = "";
        for(int i = 0; i < chain.length(); i++) {
            boolean lower = false;
            boolean upper = false;
            char caracter = chain.charAt(i);
            int newPos = 0;
            int posArray = 0;
            if(IsLetter(caracter)) {
                for(int j = 0; j < majuscules.length; j ++) {
                    if(majuscules[j] == caracter) {
                        posArray = j;
                        upper = true;
                        lower = false;
                        break;
                    } else if(minuscules[j] == caracter) {
                        posArray = j;
                        lower = true;
                        upper = false;
                        break;
                    }
                }
                newPos = (posArray - 13 + majuscules.length) % majuscules.length;
                if(upper) desxifrat+=majuscules[newPos];
                else if(lower) desxifrat+=minuscules[newPos];
            } else {
                desxifrat+=caracter;
            }
        }
        return desxifrat;
    }

    public static  boolean IsLetter(char caracter) {
        caracter = Character.toUpperCase(caracter);
        for(int i = 0; i < majuscules.length; i++) {
            if(caracter == majuscules[i]) return true;
        }
        return false;
    }
}