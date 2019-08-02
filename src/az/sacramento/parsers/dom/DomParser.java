package az.sacramento.parsers.dom;

import az.sacramento.domain.Currency;
import az.sacramento.domain.CurrencyTable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DomParser {
    public static CurrencyTable parse(String xmlUrl) {
        CurrencyTable currencyTable = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlUrl);

            currencyTable = new CurrencyTable();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

            Element currencyCourse = (Element) document.getElementsByTagName("ValCurs").item(0);
            String date = currencyCourse.getAttribute("Date");
            currencyTable.setDate(LocalDate.parse(date, formatter));
            currencyTable.setName(currencyCourse.getAttribute("Name"));
            currencyTable.setDescription(currencyCourse.getAttribute("Description"));

            NodeList currencyType =  document.getElementsByTagName("ValType");

            for (int i = 0; i < currencyType.getLength(); i++) {
                Element currencyTypeElement = (Element) currencyType.item(i);
                String type = currencyTypeElement.getAttribute("Type");
                NodeList currencies = currencyTypeElement.getElementsByTagName("Valute");

                for (int j = 0; j < currencies.getLength(); j++) {
                    Currency currency = new Currency();
                    Element currencyElement = (Element) currencies.item(j);

                    currency.setCurrencyType(type);
                    currency.setCurrencyCode(currencyElement.getAttribute("Code"));
                    currency.setCurrencyName(currencyElement.getElementsByTagName("Name").item(0).getTextContent());
                    currency.setCurrencyNominal((currencyElement.getElementsByTagName("Nominal").item(0).getTextContent()));

                    String currencyValue = currencyElement.getElementsByTagName("Value").item(0).getTextContent();
                    currency.setCurrencyValue(new BigDecimal(currencyValue));
                    currencyTable.getCurrencies().add(currency);
                }
            }
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
