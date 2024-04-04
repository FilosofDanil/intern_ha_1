package services.counters.impl;

import entities.Employee;
import services.counters.StatisticCounter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompanyStatisticCounter implements StatisticCounter {
    private Map<String, Integer> companyStatisticMap;

    private static CompanyStatisticCounter companyStatisticCounter;

    private CompanyStatisticCounter(){}

    //Init map
    {
        companyStatisticMap = new HashMap<>();
        //May be extended...
    }

    //Method which gets statistic by company of employees
    @Override
    public Map<String, Integer> getEmployeeStatistic(List<Employee> employees) {
        employees.forEach(employee -> {
            String company = employee.getCompanyName().trim();
            if (!companyStatisticMap.containsKey(company)) {
                companyStatisticMap.put(company, 1);
            } else {
                companyStatisticMap.put(company, companyStatisticMap.get(company) + 1);
            }
        });
        return companyStatisticMap;
    }

    //Method which makes map empty
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
