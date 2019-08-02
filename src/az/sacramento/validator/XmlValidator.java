package az.sacramento.validator;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class XmlValidator {
    public static boolean validate(String xmlUrl, String xsdUrl) {
        boolean isValid;
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdUrl));

            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlUrl));
            isValid = true;

        } catch (SAXException | IOException e) {
            isValid = false;
            e.printStackTrace();
            //throw new RuntimeException("Xml file is not valid according to xsd file: " + xsdUrl, e);
        }

        return isValid;
    }
}
