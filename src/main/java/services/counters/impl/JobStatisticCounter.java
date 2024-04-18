package services.counters.impl;

import services.counters.StatisticCounter;

import java.util.*;

/**
 * Class that collects hiring statistics
 * This class implements the {@link StatisticCounter} interface.
 */
public class JobStatisticCounter implements StatisticCounter {
    private Map<String, Integer> jobStatisticMap;

    private static JobStatisticCounter jobStatisticCounter;

    private JobStatisticCounter() {
    }

    //Init map
    {
        jobStatisticMap = new HashMap<>();
        //May be extended...
    }

    /**
     * Method which gets statistic by company name of employees
     *
     * @return a map containing jobs as keys and the corresponding counts of employees as values
     */
    @Override
    public Map<String, Integer> getEmployeeStatistic() {
        return jobStatisticMap;
    }

    /**
     * Method which makes map empty
     *
     * @param fieldValue the name of the job associated with an employee
     */
    @Override
    public void putValueInMap(String fieldValue) {
        List<String> jobs = Arrays.stream(fieldValue.split(","))
                .map(String::trim)
                .toList();
        for (String job : jobs) {
            if (!jobStatisticMap.containsKey(job)) {
                jobStatisticMap.put(job, 1);
            } else {
                jobStatisticMap.put(job, jobStatisticMap.get(job) + 1);
            }
        }
    }

    //Singleton implementation
    public static JobStatisticCounter getInstance() {
        if (jobStatisticCounter == null) {
            jobStatisticCounter = new JobStatisticCounter();
        }
        return jobStatisticCounter;
    }

    /**
     * Method which makes map empty
     */
    @Override
    public void cleanMap() {
        jobStatisticMap = new HashMap<>();
    }
}
