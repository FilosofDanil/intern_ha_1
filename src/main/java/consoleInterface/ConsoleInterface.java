package consoleInterface;

import entities.Employee;
import filereader.impl.FileReaderImpl;
import filewriter.XMLWriter;
import filewriter.impl.XMLWriterImpl;
import lombok.extern.log4j.Log4j;
import processors.JSONProcessor;
import services.counters.StatisticCounterContext;
import services.executor.impl.ExecutorServiceImpl;
import services.parser.EmployeeJsonParser;
import services.parser.impl.EmployeeJsonParserImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Class that maintain client interface
 */
@Log4j
public class ConsoleInterface extends Thread {
    private final StatisticCounterContext statisticContext;

    public ConsoleInterface(StatisticCounterContext statisticContext) {
        this.statisticContext = statisticContext;
    }

    private static final String menu = """
            Choose menu option using following numbers:
            (1) Start reading
            (2) Change statistic strategy
            (3) Tutorial
            (4) Shutdown""";

    private static final String statisticStrategiesOptionMenu = """
            Choose statistic counting strategy option using following numbers: 
            (1) By Jobs
            (2) By Names
            (3) By Companies            
            """;

    private Scanner scanner;

    @Override
    public void run() {
        log.info("Started client menu.");
        while (true) {
            scanner = new Scanner(System.in);
            System.out.println(menu);
            String request = scanner.nextLine();
            System.out.println(checkInput(request));
            System.out.print("Press any key to continue...");
            scanner.nextLine();
        }
    }

    /**
     * Method that checks input from the console and involving relevant function
     */
    private String checkInput(String input) {
        if (input.equals("1")) {
            long start = System.currentTimeMillis();
            launchReading();
            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;
            log.info("Time elapsed: " + timeElapsed + " ms");
            return "Files have been successfully read and statistic has been counted!";
        } else if (input.equals("2")) {
            System.out.println(statisticStrategiesOptionMenu);
            String request = scanner.nextLine();
            if (request.equals("1")) {
                statisticContext.setStatisticCounter("jobs");
            } else if (request.equals("2")) {
                statisticContext.setStatisticCounter("name");
            } else if (request.equals("3")) {
                statisticContext.setStatisticCounter("company");
            } else {
                return "No such variant present";
            }
            return "Congratulations! You have successfully changed counting strategy, " +
                    "now you're ready to read some data and form the statistic," +
                    " performing chosen strategy.";
        } else if (input.equals("3")) {
            return getTutorial();
        } else if (input.equals("4")) {
            return shutdown();
        } else {
            return "No such variant present";
        }
    }

    /**
     * Method that starts reading files
     */
    private void launchReading() {
        BlockingQueue<String> pathQueue = new LinkedBlockingQueue<>(getAllJsonFiles());
        BlockingQueue<List<Employee>> destinationQueue = new LinkedBlockingQueue<>();
        ExecutorServiceImpl executorService = new ExecutorServiceImpl();
        executorService.execute(pathQueue, destinationQueue, 1);
        try {
            Map<String, Integer> statistic;
            while (true){
                List<Employee> loadedData = destinationQueue.take();
                System.out.println(loadedData.size());
                statistic = statisticContext.getStatisticCounter().getEmployeeStatistic(loadedData);
                if (pathQueue.isEmpty() && destinationQueue.isEmpty()){
                    break;
                }
            }
            XMLWriter xmlWriter = XMLWriterImpl.getInstance();
            xmlWriter.generateXML(statistic, "stats.xml");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that gets all json files available from input folder
     */
    private List<String> getAllJsonFiles(){
        List<String> fileList = new ArrayList<>();
        try {
            Path projectPath = Paths.get("input");
            Files.walk(projectPath)
                    .filter(Files::isRegularFile)
                    .filter(file -> file.toString().endsWith(".json"))
                    .forEach(file -> fileList.add(file.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileList;

    }


    /**
     * Method which shutdown current process
     */
    private String shutdown() {
        this.stop();
        return "Finished the process";
    }

    /**
     * Returns tutorial
     */
    private String getTutorial() {
        //TODO Write Tutorial
        return "Not ready yet!";
    }
}