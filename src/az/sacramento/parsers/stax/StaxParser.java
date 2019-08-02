package az.sacramento.parsers.stax;

import az.sacramento.domain.Currency;
import az.sacramento.domain.CurrencyTable;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.stream.StreamSource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StaxParser {

     public static CurrencyTable parse(String xmlUrl) {
     CurrencyTable currencyTable = new CurrencyTable();
     Currency currency = null;
     String currencyType = "";
     boolean isCurrencyCourse = false;
     boolean isCurrency = false;
     boolean isCurrencyType = false;
     boolean isCurrencyNominal = false;
     boolean isCurrencyName = false;
     boolean isCurrencyValue = false;


        try {
            XMLInputFactory factory = XMLInputFactory.newFactory();
            XMLEventReader reader = factory.createXMLEventReader(new StreamSource(xmlUrl));

            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();

                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT : {
                        StartElement element = event.asStartElement();
                        String name = element.getName().toString();

                        if (name.equals("ValCurs")) {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                            isCurrencyCourse = true;
                            String date = element.getAttributeByName(QName.valueOf("Date")).getValue();
                            currencyTable.setName(element.getAttributeByName(QName.valueOf("Name")).getValue());
                            currencyTable.setDate(LocalDate.parse(date, formatter));
                            currencyTable.setDescription(element.getAttributeByName(QName.valueOf("Description")).getValue());
                        }

                        if (isCurrencyCourse && name.equals("ValType")) {
                            isCurrencyType = true;
                            currencyType = element.getAttributeByName(QName.valueOf("Type")).getValue();
                        }

                        if (isCurrencyType && name.equals("Valute")) {
                            isCurrency = true;
                            currency = new Currency();
                            currency.setCurrencyType(currencyType);
                            currency.setCurrencyCode(element.getAttributeByName(QName.valueOf("Code")).getValue());
                        }

                        if (isCurrency && name.equals("Nominal")) {
                            isCurrencyNominal = true;
                        } else if (isCurrency && name.equals("Name")) {
                            isCurrencyName = true;
                        } else if (isCurrency && name.equals("Value")) {
                            isCurrencyValue = true;
                        }
                        break;
                    }

                    case XMLStreamConstants.CHARACTERS: {
                        String value = event.asCharacters().getData();

                        if (isCurrency && isCurrencyNominal) {
                            currency.setCurrencyNominal(value);
                        } else if (isCurrency && isCurrencyName) {
                            currency.setCurrencyName(value);
                        } else if (isCurrency && isCurrencyValue) {
                            currency.setCurrencyValue(new BigDecimal(value));
                        }

                        break;
                    }

                    case XMLStreamConstants.END_ELEMENT: {
                        EndElement element = event.asEndElement();
                        String name = element.getName().toString();

                        if (name.equals("ValCurs")) {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                            isCurrencyCourse = false;

                        }

                        if (isCurrencyCourse && name.equals("ValType")) {
                            isCurrencyType = false;
                        }

                        if (isCurrencyType && name.equals("Valute")) {
                            isCurrency = false;
                            currencyTable.getCurrencies().add(currency);
                            currency = null;
                        }

                        if (isCurrency && name.equals("Nominal")) {
                            isCurrencyNominal = false;
                        } else if (isCurrency && name.equals("Name")) {
                            isCurrencyName = false;
                        } else if (isCurrency && name.equals("Value")) {
                            isCurrencyValue = false;
                        }
                        break;
                    }
                }
            }

        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return currencyTable;
    }
}
