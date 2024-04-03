package filereader;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class StatisticWriter {
    public static void generateXML(Map<String, Integer> statistic, String outputPath) {
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
}
