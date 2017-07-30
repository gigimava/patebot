package patebot.util

import java.net.URL
import java.nio.ByteBuffer
import java.nio.charset.Charset
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import javax.xml.bind.DatatypeConverter

private const val ALGORITHM = "BLOWFISH";

/**
 * Created by Giulio Franco on 08/07/2017.
 */
class Keyring(urlKeyring : URL, urlPassword : URL) {
    private val urlKeyring = urlKeyring
    private val urlPassword = urlPassword

    @Volatile private var map : Map<String, String>? = null
    private var strFileKey = ""

    fun getKeyring() : Map<String, String> {
        if (map==null) { // Double-checked lock should be ok on a volatile
            synchronized(this) {
                if (map==null) {
                    val ring = Properties()
                    ring.load(urlKeyring.openStream())
                    val abKey = urlPassword.openStream().use {
                        it.readBytes(512);
                    }
                    val decryptor = Cipher.getInstance(ALGORITHM)
                    decryptor.init(Cipher.DECRYPT_MODE, SecretKeySpec(abKey, 0, abKey.size, ALGORITHM))
                    val charset = Charset.forName("UTF-8")
                    var mapInternal = HashMap<String, String>();
                    ring.forEach { key, value ->  mapInternal.put(
                            key.toString(),
                            charset.decode(ByteBuffer.wrap(decryptor.doFinal(DatatypeConverter.parseBase64Binary(value.toString())))).toString()
                    ) }
                    map = Collections.unmodifiableMap(mapInternal)
                }
            }
        }
        return map!!
    }

    class Encrypter(strPassword : String) {
        private val cipher : Cipher

        init {
            val abKey = Charset.forName("UTF-8").encode(strPassword).array()
            val key = SecretKeySpec(abKey, 0, abKey.size, ALGORITHM)
            cipher = Cipher.getInstance(ALGORITHM)
            cipher.init(Cipher.ENCRYPT_MODE, key)
        }

        fun encryptKey(strKey : String) : String {
            return DatatypeConverter.printBase64Binary(
                    cipher.doFinal(Charset.forName("UTF-8").encode(strKey).array())
            );
        }
    }
}