package ru.study.sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ru.study.StringUtils;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class DemoSAX {
    private final StringBuilder builder;
    private final String filePath;
    private final String elementName;

    public DemoSAX(String filePath, String elementName) {
        this.filePath = filePath;
        this.elementName = elementName;
        builder = new StringBuilder();
    }

    public StringBuilder parse() throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        SearchingXMLHandler handler = new SearchingXMLHandler(elementName);
        parser.parse(new File(filePath), handler);

        if (StringUtils.isEmptyOrNull(builder.toString())) {
            throw new SAXException("Element " + elementName + " has not been found.");
        }
        return builder;
    }

    private class SearchingXMLHandler extends DefaultHandler {
        private final String element;
        private boolean keepOnSearching = false;

        public SearchingXMLHandler(String element) {
            this.element = element;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            if (keepOnSearching) {
                builder.append("Inner element: ").append(qName);
                printAttributes(attributes);
            }

            if (qName.equalsIgnoreCase(element)) {
                keepOnSearching = true;
                builder.append("Found element: ").append(qName);
                printAttributes(attributes);
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            if (qName.equalsIgnoreCase(element))
                keepOnSearching = false;
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            if (keepOnSearching && !StringUtils.isEmptyOrNull(new String(ch, start, length)))
                builder.append(" with value: ").append(new String(ch, start, length)).append("</br>");
        }

        private void printAttributes(Attributes attributes) {
            int length = attributes.getLength();
            if (length > 0) builder.append(" with attributes: ").append("{");
            for (int i = 0; i < length; i++)
                builder.append("{")
                        .append(attributes.getQName(i))
                        .append("=")
                        .append(attributes.getValue(i))
                        .append("}");
            if (length > 0) builder.append("}").append("</br>");
        }
    }
}