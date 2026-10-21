package iticbcn.xifratge;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;

public class ClauPublica {
    public static void main(String[] args) {
        ClauPublica cp = new ClauPublica();

        String msg = "Missatge de prova per xifrar áéíóú àèìòù äëïöü";

        try {
            KeyPair parellClaus = cp.generaParellClausRSA(); //Generem les claus.
            byte[] msgXifrat = cp.xifraRSA(msg, parellClaus.getPublic()); //Xifrem amb publica.
            String msgDesxifrat = cp.desxifraRSA(msgXifrat, parellClaus.getPrivate()); //Desxifrem amb privada.

            System.out.println("=========================================");
            System.out.print("Text xifrat:");
            System.out.println(DatatypeConverter.printHexBinary(msgXifrat)); //Imprimim el missatge en hexabinary, ya que si no no es pot llegir, es per això, que amb aquesta funció, convertim el text en hexadecimal. Cada byte, són 2 caràcters.
            System.out.println("=========================================");
            System.out.print("Text desxifrat:");
            System.out.println(msgDesxifrat); //Mostrem el missatge desxifrat, aquest cop, ja convertit a String.            
            String strClauPub = DatatypeConverter.printHexBinary(parellClaus.getPublic().getEncoded()); //Imprimim en hexadecimal les claus convertides a bytes.

            String strClauPriv = DatatypeConverter.printHexBinary(parellClaus.getPublic().getEncoded()); //El que he explicat a dalt.

            System.out.println("=========================================");
            System.out.println("Clau Pública: " + strClauPub); //Imprimim les claus públiques.
            System.out.println("=========================================");
            System.out.println("Clau privada: " + strClauPriv); //Imprimim les claus privades.
        } catch (Exception e) {
            System.out.println("Excepció general trobada durant el xifratge. Aquí tens més informació: " + e.getMessage()); //Capturem qualsevol mena de possible excepció al procés de xifratge/desxifratge o generació de clau, els quals llençen un Exception, que ha de ser si o si capturat.
        }

    }

    public KeyPair generaParellClausRSA() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA"); //Demanem tipus de xifrador segons classe d'algoritme
        generator.initialize(2048); //Definim mida de la clau.
        return generator.genKeyPair(); //La generem (ambues claus) i la retornem.
    }

    public byte[] xifraRSA(String msg, PublicKey clauPublica) throws Exception {
        Cipher cypher = Cipher.getInstance("RSA"); //Generem el xifrador/desxifrador amb RSA.
        cypher.init(Cipher.ENCRYPT_MODE, clauPublica); //Instanciem/configurem el xifrador per a encriptar amb la nostre clau pública.
        return cypher.doFinal(msg.getBytes(StandardCharsets.UTF_8)); //Retornem el missatge xifrat, en bytes de format UTF-8.
    }

    public String desxifraRSA(byte[] msgXifrat, PrivateKey clauPrivada) throws Exception {
        Cipher cypher = Cipher.getInstance("RSA"); //Fem el mateix, pero aquest cop, el farem servir per a desxifrar.
        cypher.init(Cipher.DECRYPT_MODE, clauPrivada); //Aquest cop, desxifrem, fent servir la clau privada.
        byte[] desxifrat = cypher.doFinal(msgXifrat); //Guardem els bytes desxifrats a un array.
        return new String(desxifrat, StandardCharsets.UTF_8); //Retornem els bytes pero en format String, per a que sigui llegible.
    }
}