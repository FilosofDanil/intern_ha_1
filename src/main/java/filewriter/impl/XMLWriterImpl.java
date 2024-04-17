package filewriter.impl;

import entities.Statistic;
import filewriter.XMLWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

/**
 * Class which writing statistic in xml file
 */
public class XMLWriterImpl implements XMLWriter {
    private XMLWriterImpl(){}

    private static XMLWriterImpl xmlWriter;
    /**
     * Method that writes already counted statistic values from a list of Statistic objects to an XML file.
     */
    @Override
    public void generateXML(List<Statistic> statistics, String outputPath) {
        try (FileWriter writer = new FileWriter(outputPath)) {
            JAXBContext jaxbContext = JAXBContext.newInstance(Statistic.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true); // Exclude XML declaration
            writer.write("<statistics>\n");
            for (Statistic statistic : statistics) {
                StringWriter stringWriter = new StringWriter();
                marshaller.marshal(statistic, stringWriter);
                String marshaledXml = stringWriter.toString();
                String indentedXml = addTabulationToXml(marshaledXml);
                writer.write(indentedXml);
            }
            writer.write("</statistics>");
        } catch (IOException | JAXBException e) {
            e.printStackTrace();
        }
    }
    private String addTabulationToXml(String xml) {
        StringBuilder indentedXml = new StringBuilder();
        String[] lines = xml.split("\\n");
        for (String line : lines) {
            indentedXml.append("\t").append(line).append("\n");
        }
        return indentedXml.toString();
    }


    public static XMLWriterImpl getInstance() {
        if (xmlWriter == null) {
            xmlWriter = new XMLWriterImpl();
        }
        return xmlWriter;
    }
}
