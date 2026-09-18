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
            if(Character.isLetter(caracter)) {
            for(int j = 0; j < majuscules.length; j++) {
                if(majuscules[j] == caracter || minuscules[j] == caracter) posArray = j;
            }
            newPos = (posArray + 13) % majuscules.length;
            if(Character.isUpperCase(caracter)) xifrat+=majuscules[newPos];
            else xifrat+=minuscules[newPos];
            } else {
                xifrat+=caracter;
            }
        }
        return xifrat;
    }

    public static String desxifraRot13(String chain) {
        String desxifrat = "";
        for(int i = 0; i < chain.length(); i++) {
            char caracter = chain.charAt(i);
            int newPos = 0;
            int posArray = 0;
            if(Character.isLetter(caracter)) {
                for(int j = 0; j < majuscules.length; j++) {
                    if(majuscules[j] == caracter || minuscules[j] == caracter) posArray = j;
                }
                newPos = (posArray - 13 + majuscules.length) % majuscules.length;
                if(Character.isUpperCase(caracter)) desxifrat+=majuscules[newPos];
                else desxifrat+=minuscules[newPos];
            } else {
                desxifrat+=caracter;
            }
        }
        return desxifrat;
    }
}