package services.counters;

import entities.Employee;

import java.util.List;
import java.util.Map;

public interface StatisticCounter {
    Map<String, Integer> getEmployeeStatistic(List<Employee> employees);

    void cleanMap();
}
