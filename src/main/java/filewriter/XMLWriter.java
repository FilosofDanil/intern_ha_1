package filewriter;

import entities.Statistic;

import java.util.List;

public interface XMLWriter {
   void generateXML(List<Statistic> statistic, String outputPath);
}
