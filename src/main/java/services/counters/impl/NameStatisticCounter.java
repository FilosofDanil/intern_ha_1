package services.counters.impl;

import services.counters.StatisticCounter;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class that collects statistics based on employee name
 * This class implements the {@link StatisticCounter} interface.
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
     * Method which gets statistic by company name of employees
     *
     * @return a map containing names as keys and the corresponding counts of employees as values
     */
    @Override
    public Map<String, Integer> getEmployeeStatistic() {
        return nameStatisticMap;
    }

    /**
     * Method which makes map empty
     *
     * @param fieldValue the name of the name associated with an employee
     */
    @Override
    public void putValueInMap(String fieldValue) {
        String name = fieldValue.trim();
        if (!nameStatisticMap.containsKey(name)) {
            nameStatisticMap.put(name, 1);
        } else {
            nameStatisticMap.put(name, nameStatisticMap.get(name) + 1);
        }
    }

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
