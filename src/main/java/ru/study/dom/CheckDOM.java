package ru.study.dom;

import org.xml.sax.SAXException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/dom")
public class CheckDOM extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=UTF-8");
        String field = req.getParameter("element");
        PrintWriter printWriter = resp.getWriter();
        try {
            DemoDOM domParser = new DemoDOM("sample.xml", field);
            printWriter.write(domParser.parse().toString());
        } catch (ParserConfigurationException | SAXException e) {
            printWriter.write(e.getClass() + ": " + e.getMessage());
        } finally {
            printWriter.close();
        }
    }
}
