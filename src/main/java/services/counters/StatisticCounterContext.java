package services.counters;

import services.counters.impl.CompanyStatisticCounter;
import services.counters.impl.JobStatisticCounter;
import services.counters.impl.NameStatisticCounter;

import java.util.HashMap;
import java.util.Map;

/**
 * Class, that can use the statistic counting algorithms and managing different counting strategies.
 */
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
     * Supported counter names include "jobs", "name", and "companyName".
     * If an unsupported name is provided, a default statistic counter is used.
     *
     * @param counterName the name of the statistic counter strategy
     */
    public void setStatisticCounter(String counterName) {
        switch (counterName) {
            case "jobs" -> statisticCounter = JobStatisticCounter.getInstance();
            case "name" -> statisticCounter = NameStatisticCounter.getInstance();
            case "companyName" -> statisticCounter = CompanyStatisticCounter.getInstance();
            default -> statisticCounter = getDefaultStatisticCounter();
        }
    }

    /**
     * Returns a default statistic counter implementation.
     * This implementation counts occurrences of arbitrary field values.
     *
     * @return the default statistic counter implementation
     */
    private StatisticCounter getDefaultStatisticCounter() {
        return new StatisticCounter() {
            private Map<String, Integer> statisticMap = new HashMap<>();

            @Override
            public Map<String, Integer> getEmployeeStatistic() {
                return statisticMap;
            }

            @Override
            public void putValueInMap(String fieldValue) {
                if (!statisticMap.containsKey(fieldValue)) {
                    statisticMap.put(fieldValue, 1);
                } else {
                    statisticMap.put(fieldValue, statisticMap.get(fieldValue) + 1);
                }

            }

            @Override
            public void cleanMap() {
                statisticMap = new HashMap<>();
            }
        };
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
