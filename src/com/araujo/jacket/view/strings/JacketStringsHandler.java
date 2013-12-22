package com.araujo.jacket.view.strings;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.araujo.jacket.message.Messages;
import com.araujo.jacket.message.Printer;
import com.araujo.jacket.message.Severity;

public class JacketStringsHandler implements StringsHandler {

	private static final String TAG_NAME = "value";
	private static final String TAG_PROPERTY = "name";
	
	@Override
	public HashMap<String, String> getStrings(File xml) {
		HashMap<String, String> map = new HashMap<String, String>();

		Document doc = null;
		DocumentBuilder db;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			db = dbf.newDocumentBuilder();
			doc = db.parse(xml.getPath());
		} catch (SAXException | IOException | ParserConfigurationException e) {
			Printer.print(Severity.ERR, Messages.XML_CONFIG_FILE_CANNOT_READ);
			e.printStackTrace();
		}

		Element docEle = doc.getDocumentElement();
		NodeList valueList = docEle.getElementsByTagName(TAG_NAME);

		if (valueList != null && valueList.getLength() > 0) {
			for (int i = 0; i < valueList.getLength(); i++) {
				Node node = valueList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) node;
					String key = e.getAttribute(TAG_PROPERTY);
					String value = valueList.item(i).getChildNodes().item(0)
							.getNodeValue();
					map.put(key, value);
				}
			}
		}

		return map;
	}

}
