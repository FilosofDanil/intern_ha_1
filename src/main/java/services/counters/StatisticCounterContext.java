package services.counters;

import lombok.extern.log4j.Log4j;
import services.counters.impl.CompanyStatisticCounter;
import services.counters.impl.JobStatisticCounter;
import services.counters.impl.NameStatisticCounter;

/**
 * Class, that can use the statistic counting algorithms and managing different counting strategies.
 */
@Log4j
public class StatisticCounterContext {
    private StatisticCounter statisticCounter;

    private static StatisticCounterContext statisticCounterContext;

    private StatisticCounterContext() {
    }

    public StatisticCounter getStatisticCounter() {
        return statisticCounter;
    }

    /**
     * Method for choosing of count strategy
     */
    public void setStatisticCounter(String counterName) {
        if (counterName.equals("jobs")) {
            statisticCounter = JobStatisticCounter.getInstance();
        } else if (counterName.equals("name")) {
            statisticCounter = NameStatisticCounter.getInstance();
        } else if (counterName.equals("company")) {
            statisticCounter = CompanyStatisticCounter.getInstance();
        } else {
            log.warn("No such counting strategy found!");
        }
    }

    //Singleton implementation
    public static StatisticCounterContext getInstance() {
        if (statisticCounterContext == null) {
            statisticCounterContext = new StatisticCounterContext();
            //Set jobs counter by default
            statisticCounterContext.setStatisticCounter("jobs");
        }
        return statisticCounterContext;
    }
}
