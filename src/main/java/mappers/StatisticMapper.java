package mappers;

import entities.Statistic;

import java.util.List;
import java.util.Map;

public interface StatisticMapper {
    List<Statistic> mapToStatistic(Map<String, Integer> statisticMap);
}
