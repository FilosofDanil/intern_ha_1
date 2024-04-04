package services.counters.impl;

import entities.Employee;
import services.counters.StatisticCounter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NameStatisticCounter implements StatisticCounter {
    private Map<String, Integer> nameStatisticMap;

    private static NameStatisticCounter nameStatisticCounter;

    private NameStatisticCounter() {
    }

    //Init map
    {
        nameStatisticMap = new HashMap<>();
        //May be extended...
    }

    //Method which gets statistic by name of employees
    @Override
    public Map<String, Integer> getEmployeeStatistic(List<Employee> employees) {
        employees.forEach(employee -> {
            String name = employee.getName().trim();
            if (!nameStatisticMap.containsKey(name)) {
                nameStatisticMap.put(name, 1);
            } else {
                nameStatisticMap.put(name, nameStatisticMap.get(name) + 1);
            }
        });
        return nameStatisticMap;
    }

    //Method which makes map empty
    @Override
    public void cleanMap() {
        nameStatisticMap = new HashMap<>();
    }

    //Singleton implementation
    public static NameStatisticCounter getInstance() {
        if (nameStatisticCounter == null) {
            nameStatisticCounter = new NameStatisticCounter();
        }
        return nameStatisticCounter;
    }
}
