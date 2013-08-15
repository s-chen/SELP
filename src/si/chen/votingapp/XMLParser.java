package si.chen.votingapp;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.util.Log;

public class XMLParser {
	
	// Get XML content by making HTTP request
	public String getXMLFromURL(String url) {
		
		String xml = null;
		
		
		try {
			
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			xml = EntityUtils.toString(httpEntity);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return xml;
	}
	
	
	// Get DOM element by parsing the XML file
	public Document getDOMElement(String xml) {
		
        Document document = null;
        
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        
       
        try {
 
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
 
            InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(xml));
                document = documentBuilder.parse(is); 
 
            } catch (ParserConfigurationException e) {
                Log.e("Error: ", e.getMessage());
                return null;
            } catch (SAXException e) {
                Log.e("Error: ", e.getMessage());
                return null;
            } catch (IOException e) {
                Log.e("Error: ", e.getMessage());
                return null;
            }
            
        // return DOM element
        return document;
    }
	
	
	
	public String getCandidatePromises(Element item, String str) {
		
		String candidate_promises = "";
		
		/* Get promises tag from xml document */
		NodeList nodeList = item.getElementsByTagName(str);
		
		/* Gets list of promises for a candidate */
		NodeList child_nodeList = nodeList.item(0).getChildNodes();
		
		/* Store the list of promises for a candidate in a string */
		for (int i = 0; i < child_nodeList.getLength(); i++) {
			
			candidate_promises = candidate_promises + child_nodeList.item(i).getTextContent();
		}
		
		return candidate_promises;
	} 
	
	

	// Get each XML child element value by passing element node name
	public String getValue(Element item, String str) {
	    NodeList nodeList = item.getElementsByTagName(str);
	    return this.getElementValue(nodeList.item(0));
	}
	
	
	public final String getElementValue(Node element) {
		
		Node child;
	
		if (element != null) { 
			if (element.hasChildNodes()) {
				for (child = element.getFirstChild(); child != null; child = child.getNextSibling()) {
					if (child.getNodeType() == Node.TEXT_NODE) {
						return child.getNodeValue();
	                }
	            }
	        }
	    }
		return "";
	}
	

	
}
