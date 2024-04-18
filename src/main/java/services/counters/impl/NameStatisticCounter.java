package services.counters.impl;

import services.counters.StatisticCounter;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class that collects statistics based on employee name
 */
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

    /**
     * Method which gets statistic by name of employees
     */
    @Override
    public Map<String, Integer> getEmployeeStatistic() {
        return nameStatisticMap;
    }

    @Override
    public void putValueInMap(String fieldValue) {
        String name = fieldValue.trim();
        if (!nameStatisticMap.containsKey(name)) {
            nameStatisticMap.put(name, 1);
        } else {
            nameStatisticMap.put(name, nameStatisticMap.get(name) + 1);
        }
    }

    /**
     * Method which makes map empty
     */
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
