package filewriter.impl;

import entities.Statistic;
import filewriter.XMLWriter;
import lombok.extern.log4j.Log4j;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

/**
 * Class which writing statistic in xml file
 * Implementation of the {@link XMLWriter} interface for writing statistics to an XML file.
 */
@Log4j
public class XMLWriterImpl implements XMLWriter {
    private XMLWriterImpl() {
    }

    private static XMLWriterImpl xmlWriter;

    /**
     * Method that writes already counted statistic values from a list of Statistic objects to an XML file.
     *
     * @param statistics the list of Statistic objects to write to the XML file
     * @param outputPath the path of the output XML file
     */
    @Override
    public void generateXML(List<Statistic> statistics, String outputPath) {
        log.info("Started writing output file");
        try (FileWriter writer = new FileWriter(outputPath)) {
            JAXBContext jaxbContext = JAXBContext.newInstance(Statistic.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            writer.write("<statistics>\n");
            for (Statistic statistic : statistics) {
                StringWriter stringWriter = new StringWriter();
                marshaller.marshal(statistic, stringWriter);
                String marshaledXml = stringWriter.toString();
                String indentedXml = addTabulationToXml(marshaledXml);
                writer.write(indentedXml);
            }
            writer.write("</statistics>");
            log.info("File has been successfully written");
        } catch (IOException | JAXBException e) {
            log.error("Error while trying to write file:\n " + e.getMessage());
        }
    }

    /**
     * Method that adds tabulation, in order to format xml file.
     *
     * @param xml the XML content to be formatted
     * @return the formatted XML content with tabulation
     */
    private String addTabulationToXml(String xml) {
        StringBuilder indentedXml = new StringBuilder();
        String[] lines = xml.split("\\n");
        for (String line : lines) {
            indentedXml.append("\t").append(line).append("\n");
        }
        return indentedXml.toString();
    }

    //Singleton Implementation
    public static XMLWriterImpl getInstance() {
        if (xmlWriter == null) {
            xmlWriter = new XMLWriterImpl();
        }
        return xmlWriter;
    }
}
