package services.counters.impl;

import services.counters.StatisticCounter;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class that collects statistics for company by name
 * This class implements the {@link StatisticCounter} interface.
 */
public class CompanyStatisticCounter implements StatisticCounter {
    private Map<String, Integer> companyStatisticMap;

    private static CompanyStatisticCounter companyStatisticCounter;

    private CompanyStatisticCounter() {
    }

    //Init map
    {
        companyStatisticMap = new HashMap<>();
        //May be extended...
    }

    /**
     * Method which gets statistic by company name of employees
     *
     * @return a map containing company names as keys and the corresponding counts of employees as values
     */
    @Override
    public Map<String, Integer> getEmployeeStatistic() {
        return companyStatisticMap;
    }

    /**
     * Method which makes map empty
     *
     * @param fieldValue the name of the company associated with an employee
     */
    @Override
    public void putValueInMap(String fieldValue) {
        String company = fieldValue.trim();
        if (!companyStatisticMap.containsKey(company)) {
            companyStatisticMap.put(company, 1);
        } else {
            companyStatisticMap.put(company, companyStatisticMap.get(company) + 1);
        }
    }


    @Override
    public void cleanMap() {
        companyStatisticMap = new HashMap<>();
    }

    //Singleton implementation
    public static CompanyStatisticCounter getInstance() {
        if (companyStatisticCounter == null) {
            companyStatisticCounter = new CompanyStatisticCounter();
        }
        return companyStatisticCounter;
    }


}
