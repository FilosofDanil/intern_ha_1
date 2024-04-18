package mappers.impl;

import entities.Statistic;
import mappers.StatisticMapper;

import java.util.*;

/**
 * Implementation of the {@link StatisticMapper} interface for converting a Map into a Collection of Statistic objects.
 * Class which converts Map into Statistic class.
 */
public class StatisticMapperImpl implements StatisticMapper {

    private static StatisticMapperImpl statisticMapper;

    private StatisticMapperImpl() {
    }


    /**
     * Converts a Map into a Collection of Statistic objects and sorts them by value in descending order.
     *
     * @param statisticMap the Map containing key-value pairs to be converted into Statistic objects
     * @return a List of Statistic objects sorted by value in descending order
     */
    @Override
    public List<Statistic> mapToStatistic(Map<String, Integer> statisticMap) {
        List<Statistic> statistics = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : statisticMap.entrySet()) {
            Statistic statistic = new Statistic(entry.getKey(), entry.getValue());
            statistics.add(statistic);
        }
        statistics.sort(Comparator.comparingInt(Statistic::getCount).reversed());
        return statistics;
    }

    public static StatisticMapperImpl getInstance() {
        if (statisticMapper == null) {
            statisticMapper = new StatisticMapperImpl();
        }
        return statisticMapper;
    }

}
