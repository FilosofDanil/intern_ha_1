package services.counters.impl;

import entities.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.counters.StatisticCounter;
import services.counters.StatisticCounterContext;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnonymStatisticCounterTest {

    private StatisticCounter counter;

    private StatisticCounterContext context;

    private List<Employee> employees;

    @BeforeEach
    void setUp() {
        context = StatisticCounterContext.getInstance();
        employees = List.of(
                new Employee("John", "Doe", 100, "Company", "XXXX", "Manager"),
                new Employee("Jane", "Smith", 100, "Company2", "XXXX", "Sales, Java-Developer"),
                new Employee("Michael", "Johnson", 100, "Company", "XXXX", "HR, Sales")
        );
    }

    @Test
    void getEmployeeStatisticBySurname() {
        //given
        context.setStatisticCounter("surname");
        counter = context.getStatisticCounter();
        //when
        for (Employee employee : employees) {
            counter.putValueInMap(employee.getSurname());
        }
        Map<String, Integer> resultMap = counter.getEmployeeStatistic();
        //then
        assertEquals(1, resultMap.get("Doe"));
        assertEquals(1, resultMap.get("Smith"));
        assertEquals(1, resultMap.get("Johnson"));
    }

    @Test
    void getEmployeeStatisticBySalary() {
        //given
        context.setStatisticCounter("salary");
        counter = context.getStatisticCounter();
        //when
        for (Employee employee : employees) {
            counter.putValueInMap(employee.getSalary().toString());
        }
        Map<String, Integer> resultMap = counter.getEmployeeStatistic();
        //then
        assertEquals(3, resultMap.get("100"));
    }

    @Test
    void getEmployeeStatisticByHiringDate() {
        //given
        context.setStatisticCounter("hiringDate");
        counter = context.getStatisticCounter();
        //when
        for (Employee employee : employees) {
            counter.putValueInMap(employee.getHiringDate());
        }
        Map<String, Integer> resultMap = counter.getEmployeeStatistic();
        //then
        assertEquals(3, resultMap.get("XXXX"));
    }
}