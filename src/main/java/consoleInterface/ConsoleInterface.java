package consoleInterface;

import entities.Employee;
import filereader.impl.FileReaderImpl;
import filewriter.XMLWriter;
import filewriter.impl.XMLWriterImpl;
import processors.JSONProcessor;
import services.counters.StatisticCounterContext;
import services.parser.EmployeeJsonParser;
import services.parser.impl.EmployeeJsonParserImpl;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Class that maintain client interface
 */
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
            (4) Refresh data
            (5) Shutdown""";

    private static final String statisticStrategiesOptionMenu = """
            Choose statistic counting strategy option using following numbers: 
            (1) By Jobs
            (2) By Names
            (3) By Companies            
            """;

    private Scanner scanner;

    @Override
    public void run() {
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
            //TODO Implement Reading
            EmployeeJsonParser jsonParser = EmployeeJsonParserImpl.getInstance();
            BlockingQueue<String> pathQueue = new LinkedBlockingQueue<>(List.of("generated.json", "large_data.json"));
            BlockingQueue<List<Employee>> destinationQueue = new LinkedBlockingQueue<>();
            JSONProcessor jsonProcessor = new JSONProcessor(jsonParser, new FileReaderImpl(), pathQueue, destinationQueue);
            jsonProcessor.start();
            try {
                XMLWriter xmlWriter = XMLWriterImpl.getInstance();
                var statistic = statisticContext.getStatisticCounter().getEmployeeStatistic(destinationQueue.take());
                xmlWriter.generateXML(statistic, "new_stat.xml");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Not ready yet!";
        } else if (input.equals("2")) {
            System.out.println(statisticStrategiesOptionMenu);
            String request = scanner.nextLine();
            if(request.equals("1")){
                statisticContext.setStatisticCounter("jobs");
            }else if (request.equals("2")) {
                statisticContext.setStatisticCounter("name");
            }else if (request.equals("3")) {
                statisticContext.setStatisticCounter("company");
            } else {
                return "No such variant present";
            }
            return "Congratulations! You have successfully changed counting strategy, " +
                    "now you're ready to read some data and form the statistic," +
                    " performing chosen strategy.";
        } else if (input.equals("3")) {
            //TODO Write Tutorial
            return "Not ready yet!";
        } else if (input.equals("4")) {
            //TODO Write Refresh data implementation
            return "Not ready yet!";
        } else if (input.equals("5")) {
            this.stop();
            return "Finished the process";
        } else {
            return "No such variant present";
        }
    }
}
