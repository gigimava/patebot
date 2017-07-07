package gaepart;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class HelloWorld : HttpServlet() {
    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse?) {
        resp!!.outputStream.println("Hello dear world<br />");
        resp.outputStream.println("I'm written in Kotlin");
    }
}