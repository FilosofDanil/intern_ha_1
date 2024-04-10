import consoleInterface.ConsoleInterface;
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
