package az.sacramento.parsers.sax;

import az.sacramento.domain.CurrencyTable;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class SaxParser {

    public static CurrencyTable parse (String xmlUrl) {
        CurrencyTable currencyTable = null;
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SaxParserHandler saxParserHandler = new SaxParserHandler();
            SAXParser parser = factory.newSAXParser();
            parser.parse(xmlUrl, saxParserHandler);
            currencyTable = saxParserHandler.getCurrencyTable();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currencyTable;
    }
}
