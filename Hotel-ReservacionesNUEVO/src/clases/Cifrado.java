package clases;

import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Cifrado {

    private SecretKeySpec llave;
    private Cipher oCifrado;    // Encriptador
    private Cipher oDescifrado; // Desencriptador

    public Cifrado(String pClave, byte[] iv) {
        try {
            // Derivar clave usando PBKDF2 con HmacSHA256
            byte[] salt = new byte[16];
            SecureRandom random = new SecureRandom();
            random.nextBytes(salt); // Generar salt aleatorio

            KeySpec spec = new PBEKeySpec(pClave.toCharArray(), salt, 65536, 256);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            SecretKey tmp = factory.generateSecret(spec);
            this.llave = new SecretKeySpec(tmp.getEncoded(), "AES");

            // Inicializar cifrado y descifrado con el IV proporcionado
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            this.oCifrado = Cipher.getInstance("AES/CBC/PKCS5Padding");
            this.oCifrado.init(Cipher.ENCRYPT_MODE, this.llave, ivParameterSpec);

            this.oDescifrado = Cipher.getInstance("AES/CBC/PKCS5Padding");
            this.oDescifrado.init(Cipher.DECRYPT_MODE, this.llave, ivParameterSpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String encriptar(String pCadena) throws Exception {
        byte[] aBytes = pCadena.getBytes("UTF-8");
        byte[] aBytesEnc = this.oCifrado.doFinal(aBytes);
        return Base64.getEncoder().encodeToString(aBytesEnc);
    }

    public String desencriptar(String pCadena) throws Exception {
        byte[] aBytes = Base64.getDecoder().decode(pCadena);
        byte[] aBytesDec = this.oDescifrado.doFinal(aBytes);
        return new String(aBytesDec, "UTF-8");
    }

    public static byte[] generarIV() {
        byte[] iv = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        return iv;
    }
}
