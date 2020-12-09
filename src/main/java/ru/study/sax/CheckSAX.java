package ru.study.sax;

import org.xml.sax.SAXException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/sax")
public class CheckSAX extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=UTF-8");
        String field = req.getParameter("element");
        PrintWriter printWriter = resp.getWriter();
        try {
            DemoSAX saxParser = new DemoSAX("sample.xml", field);
            printWriter.write(saxParser.parse().toString());
        } catch (ParserConfigurationException | SAXException e) {
            printWriter.write(e.getClass() + ": " + e.getMessage());
        }
        finally {
            printWriter.close();
        }
    }
}
