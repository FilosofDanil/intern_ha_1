package mappers.impl;

import entities.Statistic;
import mappers.StatisticMapper;

import java.util.*;

/**
 * Class which converts Map into Statistic class.
 */
public class StatisticMapperImpl implements StatisticMapper {

    private static StatisticMapperImpl statisticMapper;

    private StatisticMapperImpl() {
    }


    /**
     * Method which converts map to Collection of Statistic objects and sort it by value
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
