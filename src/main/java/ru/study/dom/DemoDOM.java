package ru.study.dom;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import ru.study.StringUtils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class DemoDOM {
    private final String filePath;
    private final String element;
    private final StringBuilder stringBuilder;

    public DemoDOM(String filePath, String element) {
        this.element = element;
        this.filePath = filePath;
        stringBuilder = new StringBuilder();
    }

    public StringBuilder parse() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(filePath));

        NodeList nodeList = document.getElementsByTagName(element);
        parseRecursively(nodeList);

        if (StringUtils.isEmptyOrNull(stringBuilder.toString()))
            throw new SAXException("Element " + element + " has not been found.");

        return stringBuilder;
    }

    private void parseRecursively(NodeList nodes) {
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (!"#text".equals(node.getNodeName())) {
                stringBuilder.append("Node: ").append(node.getNodeName());
                printTextContent(node);
                printAttributes(node);
                stringBuilder.append("</br>");

                parseRecursively(node.getChildNodes());
            }
        }
    }

    private void printTextContent(Node node) {
        String textContent = node.getTextContent();
        if (!StringUtils.isEmptyOrNull(textContent))
            stringBuilder.append(" - with text content: ").append(textContent);
    }

    private void printAttributes(Node node) {
        NamedNodeMap attrs = node.getAttributes();
        if (attrs.getLength() == 0) return;

        stringBuilder.append(" - with attributes: ");
        for (int i = 0; i < attrs.getLength(); i++) {
            Attr attribute = (Attr) attrs.item(i);
            stringBuilder.append("{").append(attribute.getName()).append(" = ").append(attribute.getValue()).append("}");
        }
    }
}
