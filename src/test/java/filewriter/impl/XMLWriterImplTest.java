package filewriter.impl;

import entities.Statistic;
import filewriter.XMLWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class XMLWriterImplTest {

    private XMLWriter xmlWriter;

    private final List<Statistic> statistics = new ArrayList<>();

    @BeforeEach
    void setUp(){
        xmlWriter = XMLWriterImpl.getInstance();
        statistics.add(new Statistic("value1", 10));
        statistics.add(new Statistic("value2", 5));
        statistics.add(new Statistic("value3", 8));
    }

    @Test
    void generateXML(@TempDir File outputDir) {
        // Mock the file writer
        try (FileWriter writer = spy(new FileWriter(outputDir.getPath() + "/output.xml"))){
            doNothing().when(writer).write(anyString());
            xmlWriter.generateXML(statistics, outputDir.getPath() + "/output.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(outputDir.getPath() + "/output.xml"))) {
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String content = stringBuilder.toString();
        assert content.contains("<statistics>");
        assert content.contains("<count>10</count>");
        assert content.contains("<statistic>\n" +
                "\t    <count>5</count>\n" +
                "\t    <value>value2</value>\n" +
                "\t</statistic>");

    }
}