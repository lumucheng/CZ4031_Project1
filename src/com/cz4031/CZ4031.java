package com.cz4031;

import com.cz4031.entity.Author;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.apache.xerces.util.SecurityManager;
import java.util.Map;
import java.util.Set;

public class CZ4031 {

    private static final String USER_DIRECTORY = "user.dir";
    private static final String XML_FILENAME = "dblp.xml";

    public static void main(String[] args) {

        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        System.out.println("CSV Files will be saved to " + System.getProperty(USER_DIRECTORY));
        System.out.println("Starting XML parsing now...");
        
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            SecurityManager mgr = new SecurityManager();
            mgr.setEntityExpansionLimit(100000000);
            saxParser.setProperty("http://apache.org/xml/properties/security-manager", mgr);

            SAXHandler handler = new SAXHandler();
            saxParser.parse(new File(
                    System.getProperty(USER_DIRECTORY) + "/" + XML_FILENAME), handler);

            try {
                AuthorList instance = AuthorList.getInstance();
                Map<Author, Set<Integer>> authorList = instance.getAuthorList();
                CSVWriter csvWriter = CSVWriter.getInstance();
                
                for (Map.Entry<Author, Set<Integer>> entry : authorList.entrySet()) {

                    Author author = entry.getKey();
                    csvWriter.writeToAuthor(author);
                    
                    for (Integer publicationId : authorList.get(author)) {
                        csvWriter.writeToAuthored(author.getId(), publicationId);
                    }
                }

                csvWriter.closeWriters();
                System.out.println("Parse ended.");
                
            } catch (Exception e) {
                System.out.println("Error in CSV FileWriter!");
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Parse exception!");
            System.out.println("Please make sure that dblp.xml and dblp.dtd is "
                    + "placed in the project's root folder");
        }
    }
}
