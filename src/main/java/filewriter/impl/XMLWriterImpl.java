package filewriter.impl;

import filewriter.XMLWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
    Class which writing statistic in xml file
 */
public class XMLWriterImpl implements XMLWriter {
    private XMLWriterImpl(){}

    private static XMLWriterImpl xmlWriter;

    @Override
    public void generateXML(Map<String, Integer> statistic, String outputPath) {
        try (FileWriter writer = new FileWriter(outputPath)) {
            writer.write("<statistics>\n");
            for (Map.Entry<String, Integer> entry : statistic.entrySet()) {
                writer.write("  <item>\n");
                writer.write("    <value>" + entry.getKey() + "</value>\n");
                writer.write("    <count>" + entry.getValue() + "</count>\n");
                writer.write("  </item>\n");
            }
            writer.write("</statistics>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static XMLWriterImpl getInstance() {
        if (xmlWriter == null){
            xmlWriter = new XMLWriterImpl();
        }
        return xmlWriter;
    }
}
