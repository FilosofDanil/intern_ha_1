import consoleInterface.ConsoleInterface;
import filereader.IFileReader;
import filereader.impl.FileReaderImpl;
import services.counters.StatisticCounterContext;

/**
Entry point class Main
 */
public class Main {
    public static void main(String[]args){
        StatisticCounterContext context = StatisticCounterContext.getInstance();
        ConsoleInterface consoleInterface = new ConsoleInterface(context);
        consoleInterface.start();
    }
}
