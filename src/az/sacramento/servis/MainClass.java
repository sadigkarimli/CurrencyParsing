package az.sacramento.servis;

import az.sacramento.database.Database;
import az.sacramento.domain.Currency;
import az.sacramento.domain.CurrencyTable;
import az.sacramento.parsers.dom.DomParser;
import az.sacramento.parsers.sax.SaxParser;
import az.sacramento.parsers.stax.StaxParser;
import az.sacramento.validator.XmlValidator;


public class MainClass {
    public static void main(String[] args) {

        // currency xml file url for 01/08/2019 date of Central Bank of The Republic of Azerbaijan
        String xmlUrl = "https://www.cbar.az/currencies/01.08.2019.xml";
        String xsdUrl = "xsd/currency.xsd";

        boolean validate = XmlValidator.validate(xmlUrl, xsdUrl);
        if (validate) {
            Database database = new Database();

            CurrencyTable currencyTable = StaxParser.parse(xmlUrl);
            //CurrencyTable currencyTable = SaxParser.parse(xmlUrl);
            //CurrencyTable currencyTable = DomParser.parse(xmlUrl);
            System.out.println(currencyTable);

            currencyTable.getCurrencies().forEach(System.out::println);

            /*
            This line insert data to database.See az.sacramento.database package
            */
            //database.insertCurrency(currencyTable.getCurrencies());

        } else {
            System.out.println("Xml at xmlUrl is not valid!");
        }



    }
}
