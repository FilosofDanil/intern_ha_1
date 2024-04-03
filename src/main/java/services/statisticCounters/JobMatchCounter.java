package services.statisticCounters;

import entities.Employee;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/*
Service layer class, which counts Jobs matches in employee list
 */
public class JobMatchCounter {
    private static final Map<String,Integer> jobStatisticMap = new HashMap<>();
    //Make private constructor, in order to ensure no creation access from other classes, just to utilize class
    private JobMatchCounter() {
    }

    public static String getJobsStatistic(List<Employee> employees){
        employees.forEach(employee -> {
            String[] jobs = employee.getJobs().split(",");
            for(String job: jobs){
                String trimJob = job.trim();
                if(!jobStatisticMap.containsKey(trimJob)){
                    jobStatisticMap.put(trimJob, 1);
                } else{
                    jobStatisticMap.put(trimJob, jobStatisticMap.get(trimJob) + 1);
                }
            }

        });
        return jobStatisticMap.toString();
    }
}
