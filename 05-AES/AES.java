import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
    //Definim el llenguatge de xifrat que farem servir al nostre programa, que en aquest cas, es AES.
    public static final String ALGORISME_XIFRAT = "AES";

    //Aqui, definim que farem servir el SHA-256, per a generar els nostres hashes. Aquests, son una eina,
    //molt sovint utilitzada per a obtenir una "emprempta digital" d'unes dades. Aquest, es molt útil, degut a que sempre generarà la
    //mateixa "emprempta digital" si la cadena coincideix. Així que en aquest cas, farà cada hash en blocs de 32 bytes o millor dit,
    //com el seu nom indica, 256bits.
    public static final String ALGORISME_HASH  = "SHA-256";
    
    //Aquesta es una part molt técnica, pero, bàsicament, li estem indicant el seguent:
    //Volem fer servir AES tal i com hem dit. CBC es el seu funcionament intern,
    //i PKCS5Padding s'encarrega de gestionar els blocs que no tinguin bytes suficients (16 bytes segons l'IV). 
    public static final String FORMAT_AES = "AES/CBC/PKCS5Padding";

    //Aquí, li estem dient que, generem l'iv de 16 bytes. Això, es degut a que AES treballa
    //fent els vectors/Arrays2D d'aquesta mida. Això es especific, ja que es cosa de cada tipus de xifratge.
    //pero en aquest cas, tot IV, que ajuda a que sigui unic cada missatge, serà SEMPRE de 16 bytes.
    public static final int MIDA_IV = 16;
    private static byte[] iv = new byte[MIDA_IV]; //Creem l'array de 16 bytes, tal y com hem dit abans.
    private static final String CLAU = "AbreteSesamo"; //Aquí definim la clau, que bàsicament, farà servir per encriptar els blocs de 16 bytes.
    public static void main(String[] args) {

        //Això no té més misteri, bàsicament, estem fent un array amb els textos per xifrar.
        String msgs[] = {"Lorem ipsum dicet",
                "Hola Andrés cómo está tu cuñado",
                "Àgora illa Ôtto"};

        for (int i = 0; i < msgs.length; i++) { //Encara menys misteri, els iterem.
            String msg = msgs[i];

            byte[] bXifrats = null; //Aqui, estem creant l'array que contindrà el text xifrat. 
            //Recormem que, AES xifra per bytes, així que, per tant, el xifratge ens retornarà un array d'aquests.
            String desxifrat = ""; //Aquí, declarem l'String que generarà el resultat desxifrat.
            try {
                bXifrats = xifraAES(msg, CLAU); //Li donem un valor a la referència nula, que, es la seguent: Xifra el missatge de la possició corresponent,
                //i, que agafi la nostre clau.
                //Internament, tampoc cal endirsar-se especialment, pero bàsicament, agafa la clau, fa 14 variants, i encripta el bloc corresponent aquest nombre de vegades.
                desxifrat = desxifraAES(bXifrats, CLAU); //FA exactament el mateix, pero en ordre invers, te complexitat tècnica, pero això es cosa del programa. Desxifrar es possible gràcies a que
                //que fem servir la mateixa clau, i a més a més, reutilitzem l'iv, ja que si no seria impossible..
            } catch (Exception e) { //Un catch en cas de no poder encriptar.
                System.err.println("Error de xifrat: "
                        + e.getLocalizedMessage());
            }

            System.out.println("--------------------");
            System.out.println("Msg: " + msg);
            System.out.println("Enc: " + new String(bXifrats));
            System.out.println("DEC: " + desxifrat);
        }
    }

    public static byte[] xifraAES(String msg, String clau) throws Exception { //Aquí començem el procés de xifratge.
        //Obtenir els bytes segons l'String
        byte[] Bytes = msg.getBytes(); //Convertim a un array de bytes el nostre missatge.

        //Generem IV Parameter Spec
        SecureRandom random = new SecureRandom(); //Això, crea una cadea de bytes aleatoris segurs. Impredecibles, bàsicament.
        random.nextBytes(iv); //Aquí li diem, agafa aquests bytes (16), i ves afegint fins que no hi capiguin més. D'aquesta manera, s'obté un IV random de 16 bytes.

        //Generem el Hash
        MessageDigest digest = MessageDigest.getInstance(ALGORISME_HASH); //Aqui estem creant un digest, que simplificadament, es un generador de hashes.
        //A aquest, li estem dient, dona'm una instancia teva que faci servir el meu algoritme. ATENCIÓ, que pot llençar excepció, si aquest no existeix.
        byte[] hash = digest.digest(clau.getBytes()); //Aquí ve la part més interessant, li estem dient que, agafi el nostre passowrd/clau, el converteixi a bytes, i generi un hash de 256 bits d'aquest.
        //Tot i que no ens hem endinsat en el seu procés intern, simplificadament, aquest genera com una emprenta que fa servir per a realitzar la encriptació i desencriptació. Sempre i quan aquesta sigui la mateixa, no hi ha cap problema.

        //Generem l'encriptació
        SecretKeySpec key = new SecretKeySpec(hash, ALGORISME_XIFRAT); //No obstant, encara no es una clau. Primer, hem de dir-li que la converteixi en una clau que AES pugui utilitzar, basant-nos en el hash que ja hem creat, i en el llenguatge que volem fer servir.
        //Bàsicament, SecretKeySpec, es un eina que ens permet fer dita acció, sempre i quan indiquem ambós requisits. El nostre has, i el algoritme. COMPTE, que pot esclatar si no es un algoritme compatible.
        IvParameterSpec ivSpec = new IvParameterSpec(iv); //Si bé, nosaltres ja tenim creat el nostre Iv, IvParameterSpec, fa possible que aquest sigui utilitzable per al procés d'encriptació.
        Cipher cipher = Cipher.getInstance(FORMAT_AES); //Tot i que ja tenim preparats tots els requisits, encara no tenim cap eina que ens deixi fer el cifratge. Això, ens ho fa Cipher.
        //Bàsicament, Cipher, es una eina, que, prepara l'entorn fent servir el format que volem. En aquest cas, el FORMAT_AES. COMPTE, QUE LLENÇA EXCEPCIÓ SI NO ES COmPATIBLE O NI SI ENCARA EXISTEIX.
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec); //Aquí, fem servir dit entorn, per incialitzar el xifratge, indicant que, ho volem fer amb encript mode, la nostra clau, i el nostre IvSpec.
        //Segurament n'hi han moltes més opcions, pero, ara ens centrem unicament en aquesta.
        byte[] xifrat = cipher.doFinal(Bytes); //Aquí, ja si que si, li diem que ens xifri els bytes que hem convertit a l'inici.

        //Acoplem la part xirada amb l'IV
        byte[] resultat = new byte[iv.length + xifrat.length]; //Finalment, fem un copy array, que ens copia l'iv que no hem volgut perdre, i el xifrat. Per fer-ho, definim un array que sigui de la longitud d'aquests dos junts.
        System.arraycopy(iv, 0, resultat, 0, iv.length); //Aquí, bàsicament, li estem dient que, copy l'array d'iv, desde la posició 0 d'aquest, al destí, en aquest cas el resultat , desde actualment la possició 0, fins la posició equivalent a la llargada del iv. Ultima possició no inclusiva, important, ja que es la quantitat d'elements a copiar, no la possició..
        System.arraycopy(xifrat, 0, resultat, iv.length, xifrat.length); //Seguidament, fem el mateix amb el xifratge, pero fins la longitud general de l'array de xifratge. Compte, que si l'array es queda curt, pot torna a llençar una excepció de nou.
        
        //Retornem el resultat
        return resultat; //I finalment, retornem el resultat.
    }

    //Arriba el desxifratge, on només comentaré les parts que realment hi canvien.

    public static String desxifraAES(byte[] bIvMsgXifrat, String clau) throws Exception {
        //Extreiem l'iv
        byte[] iv = Arrays.copyOfRange(bIvMsgXifrat, 0, 16);

        //Extreure la part xifrada
        byte[] xifrat = Arrays.copyOfRange(bIvMsgXifrat, 16, bIvMsgXifrat.length);

        //Fer el hash de la clau
        MessageDigest digest = MessageDigest.getInstance(ALGORISME_HASH);
        byte[] hash = digest.digest(clau.getBytes());

        //Desxifrem
        SecretKeySpec key = new SecretKeySpec(hash, ALGORISME_XIFRAT);
        IvParameterSpec ivSpec = new IvParameterSpec(iv); //Generem un iv copatible partint DEL NOSTRE PROPI IV, ja que recordem que aquests, son aleatoris.
        Cipher cipher = Cipher.getInstance(FORMAT_AES);
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec); //Ho executem en decrypt mode.
        byte[] desxifrat = cipher.doFinal(xifrat);

        //Retornem el missatge desxifrat
        return new String(desxifrat);
    }

    //Bàsicament. Text 1 en bytes XOR amb IV unic -> Xifratge AES -> Xifrat 1 -> Xifrat1 XOR Text 2 -> seguim el bucle...
    //Bàsicament, comencem fen un XOR, un o l'altre pero no els dos vol dir. I genera un conjunt de bytes que xifra amb AES (CBC) i es converteix en el xifrat del ext 1. L'agafa, i el compara amb xor de nou amb el text 2, i així en bucle.
}
