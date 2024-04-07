package services.counters.impl;

import entities.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.counters.StatisticCounter;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class NameStatisticCounterTest {


    private StatisticCounter counter;

    @BeforeEach
    void setUp() {
        counter = NameStatisticCounter.getInstance();
    }

    @Test
    void getEmployeeStatistic() {
        //given
        List<Employee> employees = List.of(
                new Employee("John", "Doe", 100,"Company", "XXXX", "Manager"),
                new Employee("Jane", "Smith", 100,"Company2", "XXXX", "Sales, Java-Developer"),
                new Employee("Michael", "Johnson",100,"Company", "XXXX", "HR, Sales")
        );
        //when
        counter.cleanMap();
        Map<String, Integer> resultMap = counter.getEmployeeStatistic(employees);
        //then
        assertEquals(1, resultMap.get("John"));
        assertEquals(1, resultMap.get("Jane"));
        assertEquals(1, resultMap.get("Michael"));
    }

    @Test
    void cleanMap() {
        //given
        List<Employee> firstList = List.of(
                new Employee("John", "Doe", 100,"Company", "XXXX", "Manager"),
                new Employee("Jane", "Smith", 100,"Company2", "XXXX", "Sales, Java-Developer"),
                new Employee("Michael", "Johnson",100,"Company", "XXXX", "HR, Sales")
        );
        //when
        counter.cleanMap();
        counter.getEmployeeStatistic(firstList);
        Map<String, Integer> resultMapBeforeClean = counter.getEmployeeStatistic(firstList);
        counter.cleanMap();
        Map<String, Integer> resultMapAfterClean = counter.getEmployeeStatistic(firstList);
        //then
        assertEquals(1, resultMapAfterClean.get("John"));
        assertEquals(1, resultMapAfterClean.get("Jane"));
        assertEquals(1, resultMapAfterClean.get("Michael"));
        assertEquals(2, resultMapBeforeClean.get("John"));
        assertEquals(2, resultMapBeforeClean.get("Jane"));
        assertEquals(2, resultMapBeforeClean.get("Michael"));
    }
}