package me.hotkey.sayputinbot;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



public class ParamXML {

    String[] nic;
    int nicCount;
    String[] coup;
    int coupCount;

    public ParamXML() {
        try {
            this.nic = getArrayFromXML("/root/nicknames/nic/text()");
            this.nicCount = nic.length;
            this.coup = getArrayFromXML("/root/couplets/coup/text()");
            this.coupCount = coup.length;
        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException ex) {
            Logger.getLogger(ParamXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String[] getArrayFromXML(String tagname) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {

        URL url = new URL("http://fortest.site/temp/sayputin.xml");
        File fXmlFile = new File ("sayputin.xml");
        FileUtils.copyURLToFile(url, fXmlFile);
//        File fXmlFile = new File("http://fortest.site/temp/sayputin.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);

        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();

        XPathExpression expr = xpath.compile(tagname);
        NodeList result = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

        NodeList nodes = (NodeList) result;
        String[] returnArray = new String[nodes.getLength()];
        System.out.println(nodes.getLength());
        for (int i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).getTextContent());
            returnArray[i] = nodes.item(i).getTextContent();
            System.out.println("Print tag: " + nodes.item(i).getNodeName() + " = " + returnArray[i]);
        }
        return returnArray;
    }

}
