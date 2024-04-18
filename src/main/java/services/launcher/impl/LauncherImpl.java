package services.launcher.impl;

import entities.Statistic;
import filereader.IFileReader;
import filereader.impl.FileReaderImpl;
import filewriter.XMLWriter;
import filewriter.impl.XMLWriterImpl;
import lombok.extern.log4j.Log4j;
import mappers.StatisticMapper;
import mappers.impl.StatisticMapperImpl;
import processors.JSONProcessor;
import services.counters.StatisticCounter;
import services.counters.StatisticCounterContext;
import services.launcher.Launcher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Boundary class between client and system, which starts running reading of JSON files,
 * contained in path specified and getting statistic by certain field and put it into .xml file
 * in the same directory.
 */
@Log4j
public class LauncherImpl implements Launcher {
    private int threads = 4;

    private final StatisticCounterContext statisticContext = StatisticCounterContext.getInstance();

    private static LauncherImpl launcher;

    /**
     * Method that starts reading files
     */
    @Override
    public void launchReading(String path, String parameter) {
        log.info("Launched program instance");
        statisticContext.setStatisticCounter(parameter);
        StatisticCounter statisticCounter = statisticContext.getStatisticCounter();
        statisticCounter.cleanMap();
        List<String> files = getAllJsonFiles(path);
        if (files.isEmpty()) {
            log.warn("There are no json files in directory!");
            return;
        }
        BlockingQueue<String> pathQueue = new LinkedBlockingQueue<>(files);
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        while (threads > 0) {
            IFileReader reader = new FileReaderImpl(statisticCounter);
            JSONProcessor processor = new JSONProcessor(reader, pathQueue, parameter);
            executor.execute(processor);
            threads--;
        }
        executor.shutdown();
        Map<String, Integer> statistic;
        do {
            statistic = statisticCounter.getEmployeeStatistic();
        }
        while (!executor.isTerminated());
        XMLWriter xmlWriter = XMLWriterImpl.getInstance();
        StatisticMapper statisticMapper = StatisticMapperImpl.getInstance();
        xmlWriter.generateXML(statisticMapper.mapToStatistic(statistic),
                path + "/statistic_by_" + parameter + ".xml");

    }

    /**
     * Method that sets count of threads enforced
     */
    @Override
    public void setThreadCount(String threads) {
        this.threads = Integer.parseInt(threads);
    }

    /**
     * Method that gets all json files available from input folder
     */
    private List<String> getAllJsonFiles(String path) {
        log.info("Launched file reading");
        List<String> fileList = new ArrayList<>();
        try {
            Path projectPath = Paths.get(path);
            Files.walk(projectPath)
                    .filter(Files::isRegularFile)
                    .filter(file -> file.toString().endsWith(".json"))
                    .forEach(file -> fileList.add(file.toString()));
            log.info("Successfully read directory. " + fileList.size() +" files detected.");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return fileList;
    }

    public static LauncherImpl getInstance() {
        if (launcher == null) {
            launcher = new LauncherImpl();
        }
        return launcher;
    }
}
