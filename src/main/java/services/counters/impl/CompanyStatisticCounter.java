package services.counters.impl;

import services.counters.StatisticCounter;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class that collects statistics for company by name
 */
public class CompanyStatisticCounter implements StatisticCounter {
    private Map<String, Integer> companyStatisticMap;

    private static CompanyStatisticCounter companyStatisticCounter;

    private CompanyStatisticCounter(){}

    //Init map
    {
        companyStatisticMap = new HashMap<>();
        //May be extended...
    }

    /**
     * Method which gets statistic by company name of employees
     */
    @Override
    public Map<String, Integer> getEmployeeStatistic() {
        return companyStatisticMap;
    }

    @Override
    public void putValueInMap(String fieldValue) {
        String company = fieldValue.trim();
        if (!companyStatisticMap.containsKey(company)) {
            companyStatisticMap.put(company, 1);
        } else {
            companyStatisticMap.put(company, companyStatisticMap.get(company) + 1);
        }
    }

    /**
     * Method which makes map empty
     */
    @Override
    public void cleanMap() {
        companyStatisticMap = new HashMap<>();
    }

    //Singleton implementation
    public static CompanyStatisticCounter getInstance() {
        if (companyStatisticCounter == null){
            companyStatisticCounter = new CompanyStatisticCounter();
        }
        return companyStatisticCounter;
    }


}
