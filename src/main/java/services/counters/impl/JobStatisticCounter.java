package services.counters.impl;

import entities.Employee;
import services.counters.StatisticCounter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //Method which gets statistic by job of employees
    @Override
    public Map<String, Integer> getEmployeeStatistic(List<Employee> employees) {
        employees.forEach(employee -> {
            List<String> jobs = Arrays.stream(employee.getJobs().split(","))
                    .map(String::trim).toList();
            for (String job : jobs) {
                if (!jobStatisticMap.containsKey(job)) {
                    jobStatisticMap.put(job, 1);
                } else {
                    jobStatisticMap.put(job, jobStatisticMap.get(job) + 1);
                }
            }

        });
        return jobStatisticMap;
    }

    //Singleton implementation
    public static JobStatisticCounter getInstance() {
        if (jobStatisticCounter == null) {
            jobStatisticCounter = new JobStatisticCounter();
        }
        return jobStatisticCounter;
    }

    //Method which makes map empty
    @Override
    public void cleanMap(){
        jobStatisticMap = new HashMap<>();
    }
}
