import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URISyntaxException;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class Transformers {

	public static void main(String[] args) throws IOException, URISyntaxException, TransformerException {

		String folder_path = "C:\\Users\\anilm\\Documents\\drive\\workspace\\TestImpl\\src\\";
		File outputFileSource = new File(folder_path + "output.xml");

		// Your html input will be something like this.
		String htmlInputString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<catalog>" + "<cd>"
				+ "<title>Empire Burlesque</title>" + "<artist>Bob Dylan</artist>" + "<country>USA</country>"
				+ "<company>Columbia</company>" + "<price>10.90</price>" + "<year>1985</year>" + "<div>12</div>"
				+ "</cd>" + "</catalog>";
		// You create a reader object like this.
		StringReader htmlStringReader = new StringReader(htmlInputString);

		// Same way you will have to create an transform xsl for your HTML string.
		// I have created the variable 'clsDate' to put your date in the html string.
		
		// Change the tags accordingly : This is what you have to change. If you notice in the string below. 
		// In line number 40 there is match="catalog/cd/div" (The back slashes are escape sequences..ignore those)
		// You have to change that to match your xpath on the html to locate where you want to insert the date.
		
		// Also in the line 42 and 44 there is <div> tags. These are to be replaced with the appropriate tag from your HTML
		
		String transformXSLString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "<xsl:stylesheet version=\"1.0\" "
				+ "xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">" + 
				"<xsl:template match=\"catalog/cd/div\">"+
				"<xsl:param name=\"clsDate\" />"
				+ "<div>" +
				"<xsl:value-of select=\"$clsDate\" />" + 
				"</div>"
				+ "</xsl:template>" + "<xsl:template match=\"@*|node()\">" + "<xsl:param name=\"clsDate\" />"
				+ "<xsl:copy>" + "<xsl:apply-templates select=\"@*|node()\">"
				+ "<xsl:with-param name=\"clsDate\" select=\'$clsDate\'></xsl:with-param>" + "</xsl:apply-templates>"
				+ "</xsl:copy>" + "</xsl:template>" + "</xsl:stylesheet>";
		// You create a StringReader object just like above for the transform string.
		StringReader transformStringReader = new StringReader(transformXSLString);
		
		
		TransformerFactory factory = TransformerFactory.newInstance();

		Source xsltSource = new StreamSource(transformStringReader);
		Transformer transformer = factory.newTransformer(xsltSource);

		transformer.setParameter("clsDate", "Anil");

		Source input = new StreamSource(htmlStringReader);
		
		// This is your output String writer.
		StringWriter outputStringWriter = new StringWriter();
		transformer.transform(input, new StreamResult(outputStringWriter));
		
		//This is your outputString. Use toString and send it away
		System.out.println(outputStringWriter.toString());
	}

}
