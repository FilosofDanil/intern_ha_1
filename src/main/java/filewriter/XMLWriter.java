package filewriter;

import java.util.Map;

public interface XMLWriter {
   void generateXML(Map<String, Integer> statistic, String outputPath);
}
