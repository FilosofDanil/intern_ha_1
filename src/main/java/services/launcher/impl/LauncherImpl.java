package services.launcher.impl;

import entities.Employee;
import filereader.IFileReader;
import filereader.impl.FileReaderImpl;
import filewriter.XMLWriter;
import filewriter.impl.XMLWriterImpl;
import lombok.extern.log4j.Log4j;
import mappers.StatisticMapper;
import mappers.impl.StatisticMapperImpl;
import processors.JSONProcessor;
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
        statisticContext.setStatisticCounter(parameter);
        BlockingQueue<String> pathQueue = new LinkedBlockingQueue<>(getAllJsonFiles(path));
        BlockingQueue<List<Employee>> destinationQueue = new LinkedBlockingQueue<>();
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        while (threads > 0){
            IFileReader reader = new FileReaderImpl();
            JSONProcessor processor = new JSONProcessor(reader, pathQueue, destinationQueue);
            executor.execute(processor);
            threads--;
        }
        executor.shutdown();
        try {
            Map<String, Integer> statistic;
            do{
                List<Employee> loadedData = destinationQueue.take();
                statisticContext.getStatisticCounter().cleanMap();
                statistic = statisticContext.getStatisticCounter().getEmployeeStatistic(loadedData);
            }
            while (!executor.isTerminated());
            XMLWriter xmlWriter = XMLWriterImpl.getInstance();
            StatisticMapper statisticMapper = StatisticMapperImpl.getInstance();
            xmlWriter.generateXML(statisticMapper.mapToStatistic(statistic),
                    path + "/statistic_by_" + parameter + ".xml");
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * Method that sets count of threads enforced
     */
    @Override
    public void setThreadCount(int threads) {
        this.threads = threads;
    }

    /**
     * Method that gets all json files available from input folder
     */
    private List<String> getAllJsonFiles(String path) {
        List<String> fileList = new ArrayList<>();
        try {
            Path projectPath = Paths.get(path);
            Files.walk(projectPath)
                    .filter(Files::isRegularFile)
                    .filter(file -> file.toString().endsWith(".json"))
                    .forEach(file -> fileList.add(file.toString()));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return fileList;

    }

    public static LauncherImpl getInstance() {
        if(launcher==null){
            launcher = new LauncherImpl();
        }
        return launcher;
    }
}
