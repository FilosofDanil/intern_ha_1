package services.counters.impl;

import services.counters.StatisticCounter;

import java.util.*;

/**
 * Class that collects hiring statistics
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
     * Method which gets statistic by job of employees
     */
    @Override
    public Map<String, Integer> getEmployeeStatistic() {
        return jobStatisticMap;
    }

    /**
     * Method which puts value from field in the map
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
    public void cleanMap(){
        jobStatisticMap = new HashMap<>();
    }
}
