package com.scansee.batch.common;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DomConfig {

	private static Logger LOG = LoggerFactory.getLogger(DBConnection.class);

	public static DocumentBuilder getDocumentConfig() throws Exception {

		DocumentBuilder domBuild = null;
		DocumentBuilderFactory domFcry = DocumentBuilderFactory.newInstance();

		try {
			domFcry.setCoalescing(true);
			domBuild = domFcry.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			LOG.error("Inside DomConfig :" + e.getMessage());
		}

		return domBuild;
	}

}
