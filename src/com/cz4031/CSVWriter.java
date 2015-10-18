package com.cz4031;

// Singleton Implementation
import com.cz4031.entity.Article;
import com.cz4031.entity.Author;
import com.cz4031.entity.Book;
import com.cz4031.entity.Incollection;
import com.cz4031.entity.Inproceedings;
import com.cz4031.entity.Publication;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.text.WordUtils;

public class CSVWriter {

    private static final String COMMA_DELIMITER = "\t";
    private static final String NEW_LINE_SEPARATOR
            = System.getProperty("line.separator");
    private static final String USER_DIRECTORY = "user.dir";
    private static final String FILE_HEADER_AUTHOR = "id,name";
    private static final String FILE_HEADER_AUTHORED = "authorid,pubid";
    private static final String FILE_HEADER_PUBICATIONS
            = "pubid,pubkey,pubType,title,year,journal,month,"
            + "volume,number,publisher,isbn,booktitle,editor,pages";

    private static CSVWriter csvWriter = new CSVWriter();

    private File authorCSV;
    private File authoredCSV;
    private File publicationCSV;

    private FileWriter authorFileWriter;
    private FileWriter authoredFileWriter;
    private FileWriter publicationFileWriter;

    private CSVWriter() {
        createCSVFiles();
    }

    public static CSVWriter getInstance() {
        return csvWriter;
    }

    private void createCSVFiles() {
        authorCSV = new File(System.getProperty(USER_DIRECTORY) + "/author.csv");
        authoredCSV = new File(System.getProperty(USER_DIRECTORY) + "/authored.csv");
        publicationCSV = new File(System.getProperty(USER_DIRECTORY) + "/publication.csv");

        try {
            authorCSV.createNewFile();
            authoredCSV.createNewFile();
            publicationCSV.createNewFile();

            authorFileWriter = new FileWriter(authorCSV, true);
            authoredFileWriter = new FileWriter(authoredCSV, true);
            publicationFileWriter = new FileWriter(publicationCSV, true);

            authorFileWriter.append(FILE_HEADER_AUTHOR);
            authorFileWriter.append(NEW_LINE_SEPARATOR);

            authoredFileWriter.append(FILE_HEADER_AUTHORED);
            authoredFileWriter.append(NEW_LINE_SEPARATOR);

            publicationFileWriter.append(FILE_HEADER_PUBICATIONS);
            publicationFileWriter.append(NEW_LINE_SEPARATOR);

        } catch (IOException ex) {
            Logger.getLogger(CSVWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void writeToPublication(Publication publication) {

//"pubid,pubkey,pubType,title,year,journal,month,
        //volume,number,publisher,isbn,booktitle,editor,pages";
        try {

            publicationFileWriter.append(String.valueOf(publication.getPubId()));
            publicationFileWriter.append(COMMA_DELIMITER);
            publicationFileWriter.append(publication.getPubKey());
            publicationFileWriter.append(COMMA_DELIMITER);
            publicationFileWriter.append(String.valueOf(publication.getPubType()));
            publicationFileWriter.append(COMMA_DELIMITER);
            publicationFileWriter.append(publication.getTitle());
            publicationFileWriter.append(COMMA_DELIMITER);
            publicationFileWriter.append(String.valueOf(publication.getYear()));
            publicationFileWriter.append(COMMA_DELIMITER);
            publicationFileWriter.append(publication.getJournal());
            publicationFileWriter.append(COMMA_DELIMITER);
            publicationFileWriter.append(publication.getMonth());
            publicationFileWriter.append(COMMA_DELIMITER);
            publicationFileWriter.append(publication.getVolume());
            publicationFileWriter.append(COMMA_DELIMITER);
            publicationFileWriter.append(publication.getNumber());
            publicationFileWriter.append(COMMA_DELIMITER);
            publicationFileWriter.append(publication.getPublisher());
            publicationFileWriter.append(COMMA_DELIMITER);
            publicationFileWriter.append(publication.getIsbn());
            publicationFileWriter.append(COMMA_DELIMITER);
            publicationFileWriter.append(publication.getBooktitle());
            publicationFileWriter.append(COMMA_DELIMITER);
            publicationFileWriter.append(publication.getEditor());
            publicationFileWriter.append(COMMA_DELIMITER);
            publicationFileWriter.append(String.valueOf(publication.getPages()));
            publicationFileWriter.append(NEW_LINE_SEPARATOR);
        } catch (IOException ex) {
            Logger.getLogger(CSVWriter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void writeToAuthor(Author author) {
        try {
            // = "id,name";
            authorFileWriter.write(String.valueOf(author.getId()));
            authorFileWriter.write(COMMA_DELIMITER);
            
            String authorName = author.getName();
            authorName = WordUtils.capitalize(authorName, ',');
            authorName = WordUtils.capitalize(authorName, '.');
            authorName = WordUtils.capitalize(authorName, null);
            authorFileWriter.write(authorName);
            authorFileWriter.write(NEW_LINE_SEPARATOR);
        } 
        catch (IOException ex) {
            Logger.getLogger(CSVWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void writeToAuthored(int authorId, int publicationId) {
        try {
            // = "authorid,pubid";
            authoredFileWriter.write(String.valueOf(authorId));
            authoredFileWriter.write(COMMA_DELIMITER);
            authoredFileWriter.write(String.valueOf(publicationId));
            authoredFileWriter.write(NEW_LINE_SEPARATOR);
        } 
        catch (IOException ex) {
            Logger.getLogger(CSVWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void closeWriters() {
        try {
            authorFileWriter.close();
            authoredFileWriter.close();
            publicationFileWriter.close();
        } 
        catch (IOException ex) {
            Logger.getLogger(CSVWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
