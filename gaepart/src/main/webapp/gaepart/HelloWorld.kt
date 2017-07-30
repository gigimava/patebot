package gaepart;

import patebot.util.Keyring
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class HelloWorld : HttpServlet() {
    private companion object {
        val keyring = Keyring(javaClass.getResource("keyring.properties"), javaClass.getResource("keyring.password"))
    }

    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse?) {
        resp!!.outputStream.println("Hello dear " + keyring.getKeyring()["name"] + "<br />");
        resp.outputStream.println("I'm written in Kotlin");
    }
}