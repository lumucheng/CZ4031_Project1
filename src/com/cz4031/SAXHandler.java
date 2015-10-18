/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cz4031;

import com.cz4031.entity.Author;
import com.cz4031.entity.Publication;
import com.cz4031.entity.PublicationType;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Mucheng
 */
public class SAXHandler extends DefaultHandler {

    private final StringBuilder stringBuilder = new StringBuilder();
    private int authorID = 0;
    private int publicationID = 0;
    private Publication publication;

    public int count = 1;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {

        if (count < 765364) {
            if (qName.equalsIgnoreCase("article")) {

                String pubKey = attributes.getValue("key");

                int publicationType = PublicationType.ARTICLE.getValue();
                publication = new Publication();
                publication.setPubKey(pubKey);
                publication.setPubId(++publicationID);
                publication.setPubType(publicationType);
                count++;

            } else if (qName.equalsIgnoreCase("book")) {

                String pubKey = attributes.getValue("key");

                int publicationType = PublicationType.BOOK.getValue();
                publication = new Publication();
                publication.setPubKey(pubKey);
                publication.setPubId(++publicationID);
                publication.setPubType(publicationType);
                count++;

            } else if (qName.equalsIgnoreCase("incollection")) {

                String pubKey = attributes.getValue("key");
                int publicationType = PublicationType.INCOLLECTION.getValue();
                publication = new Publication();
                publication.setPubKey(pubKey);
                publication.setPubId(++publicationID);
                publication.setPubType(publicationType);
                count++;
            } else if (qName.equalsIgnoreCase("inproceedings")) {

                String pubKey = attributes.getValue("key");
                int publicationType = PublicationType.INPROCEEDINGS.getValue();
                publication = new Publication();
                publication.setPubKey(pubKey);
                publication.setPubId(++publicationID);
                publication.setPubType(publicationType);
                count++;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        String tempValue = stringBuilder.toString();
        String tempAccent = StringUtils.stripAccents(tempValue);
        String value = StringUtils.trimToEmpty(tempAccent);
        stringBuilder.setLength(0);

        if (publication != null) {

            if (qName.equalsIgnoreCase("author")) {

                String authorName = value.toLowerCase();
                AuthorList instance = AuthorList.getInstance();
                HashMap<Author, Set<Integer>> authorList
                        = instance.getAuthorList();

                Author author = new Author();
                author.setName(authorName);

                if (authorList.containsKey(author)) {
                    Set<Integer> publicationList = authorList.get(author);
                    publicationList.add(publication.getPubId());
                } else {
                    author.setId(++authorID);
                    Set<Integer> publicationList = new HashSet<>();
                    publicationList.add(publication.getPubId());
                    authorList.put(author, publicationList);
                }
            } else if (qName.equalsIgnoreCase("article")
                    || qName.equalsIgnoreCase("book")
                    || qName.equalsIgnoreCase("incollection")
                    || qName.equalsIgnoreCase("inproceedings")) {

                CSVWriter csvWriter = CSVWriter.getInstance();
                csvWriter.writeToPublication(publication);
                publication = null;
            } else if (qName.equalsIgnoreCase("title")) {
                publication.setTitle(value);
            } else if (qName.equalsIgnoreCase("year")) {
                int year = Integer.parseInt(value);
                publication.setYear(year);
            } else if (qName.equalsIgnoreCase("journal")) {
                publication.setJournal(value);
            } else if (qName.equalsIgnoreCase("volume")) {
                publication.setVolume(value);
            } else if (qName.equalsIgnoreCase("number")) {
                publication.setNumber(value);
            } else if (qName.equalsIgnoreCase("publisher")) {
                publication.setPublisher(value);
            } else if (qName.equalsIgnoreCase("isbn")) {
                publication.setIsbn(value);
            } else if (qName.equalsIgnoreCase("booktitle")) {
                publication.setBooktitle(value);
            } else if (qName.equalsIgnoreCase("editor")) {
                publication.setEditor(value);
            } else if (qName.equalsIgnoreCase("pages")) {
                String pageStr = value;
                parsePages(pageStr);
            }
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        stringBuilder.append(new String(ch, start, length));
    }

    private void parsePages(String pageStr) {
        int index = pageStr.indexOf("-");

        if (index > 0) {

            String pageFromStr = pageStr.substring(0, index);
            String pageToStr = pageStr.substring(index + 1, pageStr.length());

            if (pageFromStr.length() > 0 && pageToStr.length() > 0) {

                if (StringUtils.isNumeric(pageFromStr)
                        && StringUtils.isNumeric(pageToStr)) {
                    int pageFrom = Integer.parseInt(pageFromStr);
                    int pageTo = Integer.parseInt(pageToStr);
                    int pages = pageTo - pageFrom;
                    pages = Math.abs(pages);
                    if (pages > 0) {
                        pages += 1;
                    } else {
                        pages = 0;
                    }
                    publication.setPages(pages);
                } else {
                    int pageFrom = romanToDecimal(pageFromStr);
                    int pageTo = romanToDecimal(pageToStr);
                    int pages = pageTo - pageFrom;
                    pages = Math.abs(pages);
                    if (pages > 0) {
                        pages += 1;
                    } else {
                        pages = 0;
                    }
                    publication.setPages(pages);
                }
            }
        } else {
            if (StringUtils.isNumeric(pageStr)) {
                int pages = 1;
                publication.setPages(pages);
            } else {
                int pages = 1;
                publication.setPages(pages);
            }
        }
    }

    private int romanToDecimal(String romanNumber) {
        int decimal = 0;
        int lastNumber = 0;
        String romanNumeral = romanNumber.toUpperCase();
        for (int x = romanNumeral.length() - 1; x >= 0; x--) {
            char convertToDecimal = romanNumeral.charAt(x);

            switch (convertToDecimal) {
                case 'M':
                    decimal = checkRoman(1000, lastNumber, decimal);
                    lastNumber = 1000;
                    break;
                case 'D':
                    decimal = checkRoman(500, lastNumber, decimal);
                    lastNumber = 500;
                    break;
                case 'C':
                    decimal = checkRoman(100, lastNumber, decimal);
                    lastNumber = 100;
                    break;
                case 'L':
                    decimal = checkRoman(50, lastNumber, decimal);
                    lastNumber = 50;
                    break;
                case 'X':
                    decimal = checkRoman(10, lastNumber, decimal);
                    lastNumber = 10;
                    break;
                case 'V':
                    decimal = checkRoman(5, lastNumber, decimal);
                    lastNumber = 5;
                    break;
                case 'I':
                    decimal = checkRoman(1, lastNumber, decimal);
                    lastNumber = 1;
                    break;
            }
        }
        return decimal;
    }

    private int checkRoman(int TotalDecimal, int LastRomanLetter, int LastDecimal) {
        if (LastRomanLetter > TotalDecimal) {
            return LastDecimal - TotalDecimal;
        } else {
            return LastDecimal + TotalDecimal;
        }
    }
}
