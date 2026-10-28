import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Hashes {
    public int npass;
    public String getSHA512AmbSalt(String pw, String salt) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt.getBytes(StandardCharsets.UTF_8));
        byte[] bytes = md.digest(pw.getBytes(StandardCharsets.UTF_8));
        HexFormat hex = HexFormat.of();
        return hex.formatHex(bytes);
    } 

    public String getPBKDF2AmbSalt(String pw, String salt) throws Exception {
        PBEKeySpec spec = new PBEKeySpec(
            pw.toCharArray(),
            salt.getBytes(StandardCharsets.UTF_8),
            65536,
            512
        );

        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] bytes = skf.generateSecret(spec).getEncoded();

        HexFormat hex = HexFormat.of();
        return hex.formatHex(bytes);
    }

    public String forcaBruta(String alg, String hash, String salt) throws Exception {
        npass = 0;
        String characters = "abcdefABCDEF1234567890!";
        String successTry = "";
        boolean found = false;
        for(int a = 0; a < characters.length(); a++) {
            for(int b = 0; b < characters.length(); b++) {
                for(int c = 0; c < characters.length(); c++) {
                    for(int d = 0; d < characters.length(); d++) {
                        for(int e = 0; e < characters.length(); e++) {
                            for(int f = 0; f < characters.length(); f++) {
                                char[] intent = new char[6];
                                intent[0] = characters.charAt(a);
                                intent[1] = characters.charAt(b);
                                intent[2] = characters.charAt(c); 
                                intent[3] = characters.charAt(d); 
                                intent[4] = characters.charAt(e); 
                                intent[5] = characters.charAt(f);
                                String intentStringed = new String(intent);
                                String intentHashed = alg.equals("SHA-512") ? getSHA512AmbSalt(intentStringed, salt) : getPBKDF2AmbSalt(intentStringed, salt);
                                if(intentHashed.equals(hash)) {
                                    successTry = intentHashed;
                                    found = true;
                                    break;
                                }
                                npass++;
                            }
                            if(found) break;
                        }
                        if(found) break;
                    }
                    if(found) break;
                }
                if(found) break;
            }
            if(found) break;
        }
        return successTry;
    }

    public String getInterval(long t1, long t2) {
        long interval = t2 - t1;
        long milesimes = interval % 1000;
        long segons = (interval / 1000) % 60;
        long minuts = (interval / 60000) % 60;
        long hores = (interval / 360000) % 24;
        long dies = interval / 86400000;
        return String.format("%d dies / %d hores / %d minuts / %d segons / %d millis", dies, hores, minuts, segons, milesimes);
    }

    public static void main(String[] args) {
        try {
        String salt = "qpoweiruañslkdfjz";
        String pw = "aaabF!";
        Hashes h = new Hashes();
        String[] aHashes = { h.getSHA512AmbSalt(pw, salt),
                h.getPBKDF2AmbSalt(pw, salt) };
        String pwTrobat = null;
        String[] algorismes = {"SHA-512", "PBKDF2"};
        for(int i=0; i< aHashes.length; i++){
            System.out.printf("============================\n");
            System.out.printf("Algorisme: %s\n", algorismes[i]);
            System.out.printf("Hash: %s\n",aHashes[i]);
            System.out.printf("--------------------------\n");
            System.out.printf("-- Inici de força bruta ---\n");
            long t1 = System.currentTimeMillis();
            pwTrobat = h.forcaBruta(algorismes[i], aHashes[i], salt);
            long t2 = System.currentTimeMillis();
            System.out.printf("Pass : %s\n", pwTrobat);
            System.out.printf("Provats: %d\n", h.npass);
            System.out.printf("Temps : %s\n", h.getInterval(t1, t2));
            System.out.printf("--------------------------\n\n");
        }
        } catch(Exception e) {
            System.out.println("S'ha trobat una excepció a nivel general, alhora de xifrar. Aquí tens, més informació. " + e.getMessage());
        }
    }
}