package services.counters;

import java.util.Map;

public interface StatisticCounter {
    Map<String, Integer> getEmployeeStatistic();

    void putValueInMap(String fieldValue);

    void cleanMap();
}
