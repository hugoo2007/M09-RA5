import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
    public static final String ALGORISME_XIFRAT = "AES";
    public static final String ALGORISME_HASH  = "SHA-256";
    public static final String FORMAT_AES = "AES/CBC/PKCS5Pading";

    public static final int MIDA_IV = 16;
    private static byte[] iv = new byte[MIDA_IV];
    private static final String CLAU = "AbreteSesamo";
    public static void main(String[] args) {
        String msgs[] = {"Lorem ipsum dicet",
                "Hola Andrés cómo está tu cuñado",
                "Àgora illa Ôtto"};

        for (int i = 0; i < msgs.length; i++) {
            String msg = msgs[i];

            byte[] bXifr ats = null;
            String desxifrat = "";
            try {
                bXifr ats = xifraAES(msg, CLAU);
                desxifrat = desxifraAES(bXifr ats, CLAU);
            } catch (Exception e) {
                System.err.println("Error de xifrat: "
                        + e.getLocalizedMessage());
            }

            System.out.println("--------------------");
            System.out.println("Msg: " + msg);
            System.out.println("Enc: " + new String(bXifr ats));
            System.out.println("DEC: " + desxifrat);
        }
    }

    public static Byte[] xifraAES(String msg, String clau) throws Exception {
        //Obtenir els bytes segons l'String
        byte[] Bytes = msg.getBytes();

        //Generem IV Parameter Spec
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);

        //Generem el Hash
        MessageDigest digest = MessageDigest.getInstance(ALGORISME_HASH);
        byte[] hash = digest.digest(Bytes);

        //Generem l'encriptació
        SecretKeySpec key = new SecretKeySpec(hash, ALGORISME_XIFRAT);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        byte[] xifrat = cipher.doFinal(Bytes);

        //Acoplem la part xirada amb l'IV
        byte[] resultat = new byte[iv.length + xifrat.length];
        System.arraycopy(iv, 0, resultat, 0, iv.length);
        System.arraycopy(xifrat, 0, resultat, iv.length, resultat.length);
    }
}
