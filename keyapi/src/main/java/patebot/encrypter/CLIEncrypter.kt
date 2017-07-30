package patebot.encrypter

import patebot.util.Keyring
import java.security.Provider
import java.security.Security
import javax.crypto.Cipher


fun main(args : Array<String>) {
    if (args[0].equals("listproviders")) {
        Security.getProviders().forEach { p -> System.out.println(p) }
        return;
    } else if (args[0].equals("listalgorithms")) {
        Security.getAlgorithms("Cipher").forEach { a -> System.out.println(a) }
        return;
    }
    val enc = Keyring.Encrypter(args[0])
    System.out.println(enc.encryptKey(args.get(1)))
}
