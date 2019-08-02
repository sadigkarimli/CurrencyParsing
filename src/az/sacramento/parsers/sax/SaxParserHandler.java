package az.sacramento.parsers.sax;

import az.sacramento.domain.Currency;
import az.sacramento.domain.CurrencyTable;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SaxParserHandler extends DefaultHandler {
    private CurrencyTable currencyTable;
    private Currency currency;
    private String currencyType;
    private boolean isCurrencyCourse;
    private boolean isCurrency;
    private boolean isCurrencyType;
    private boolean isCurrencyNominal;
    private boolean isCurrencyName;
    private boolean isCurrencyValue;

    public SaxParserHandler() {
        this.currencyType = "";
        this.currencyTable = new CurrencyTable();
        this.isCurrency = false;
        this.isCurrencyType = false;
        this.isCurrencyNominal = false;
        this.isCurrencyName = false;
        this.isCurrencyValue = false;
    }

    public CurrencyTable getCurrencyTable() {
        return currencyTable;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        if (qName.equals("ValCurs")) {
            isCurrencyCourse = true;
            String date = attributes.getValue("Date");
            currencyTable.setDate(LocalDate.parse(date, formatter));
            currencyTable.setName(attributes.getValue("Name"));
            currencyTable.setDescription(attributes.getValue("Description"));
        }

        if (isCurrencyCourse && qName.equals("ValType")) {
            isCurrencyType = true;
            currencyType = attributes.getValue("Type");
        }

        if (isCurrencyType && qName.equals("Valute")) {
            isCurrency = true;
            currency = new Currency();
            currency.setCurrencyCode(attributes.getValue("Code"));
            currency.setCurrencyType(currencyType);
        }

        if (isCurrency && qName.equals("Nominal")) {
            isCurrencyNominal = true;
        } else if (isCurrency && qName.equals("Name")) {
            isCurrencyName = true;
        } else if (isCurrency && qName.equals("Value")) {
            isCurrencyValue = true;
        }
    }

    @Override
    public void characters(char[] content, int start, int length) throws SAXException {
        String value = new String(content, start, length);

        if (isCurrency && isCurrencyNominal) {
            currency.setCurrencyNominal(value);
        } else if (isCurrency && isCurrencyName) {
            currency.setCurrencyName(value);
        } else if (isCurrency && isCurrencyValue) {
            currency.setCurrencyValue(new BigDecimal(value));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("ValCurs")) {
            isCurrencyCourse = false;
        }

        if (isCurrency && qName.equals("ValType")) {
            isCurrencyType = false;
        }

        if (isCurrencyType && qName.equals("Valute")) {
            isCurrency = false;
            currencyTable.getCurrencies().add(currency);
            currency = null;
        }

        if (isCurrency && qName.equals("Nominal")) {
            isCurrencyNominal = false;
        } else if (isCurrency && qName.equals("Name")) {
            isCurrencyName = false;
        } else if (isCurrency && qName.equals("Value")) {
            isCurrencyValue = false;
        }
    }
}
