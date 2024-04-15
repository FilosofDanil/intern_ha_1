package services.counters.impl;

import entities.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.counters.StatisticCounter;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompanyStatisticCounterTest {

    private StatisticCounter counter;

    @BeforeEach
    void setUp() {
        counter = CompanyStatisticCounter.getInstance();
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
        assertEquals(2, resultMap.get("Company"));
        assertEquals(1, resultMap.get("Company2"));
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
        assertEquals(2, resultMapAfterClean.get("Company"));
        assertEquals(1, resultMapAfterClean.get("Company2"));
        assertEquals(4, resultMapBeforeClean.get("Company"));
        assertEquals(2, resultMapBeforeClean.get("Company2"));
    }
}