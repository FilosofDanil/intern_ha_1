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
        counter.cleanMap();
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
        for(Employee employee: employees){
            counter.putValueInMap(employee.getCompanyName());
        }
        Map<String, Integer> resultMap = counter.getEmployeeStatistic();
        //then
        assertEquals(2, resultMap.get("Company"));
        assertEquals(1, resultMap.get("Company2"));
    }
}