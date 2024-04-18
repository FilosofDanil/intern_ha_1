package mappers.impl;

import entities.Statistic;
import mappers.StatisticMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StatisticMapperImplTest {
    private Map<String, Integer> mockMap;

    private StatisticMapper statisticMapper;

    @BeforeEach
    void setUp(){
        mockMap = new HashMap<>();
        statisticMapper = StatisticMapperImpl.getInstance();
    }

    @Test
    void mapToStatistic() {
        //given
        mockMap.put("Peter", 10);
        mockMap.put("Jack", 5);
        mockMap.put("Daniel", 8);
        mockMap.put("Alice", 7);
        //when
        List<Statistic> statistics = statisticMapper.mapToStatistic(mockMap);
        //then
        assertTrue(isSorted(statistics));
    }

    private boolean isSorted(List<Statistic> statistics) {
        for (int i = 1; i < statistics.size(); i++) {
            if (statistics.get(i - 1).getCount() < statistics.get(i).getCount()) {
                return false;
            }
        }
        return true;
    }
}