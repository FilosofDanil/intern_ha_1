package services.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import entities.Employee;

import java.util.List;

public interface EmployeeJsonParser {
    List<Employee> parseJsonFile(String json);
}
