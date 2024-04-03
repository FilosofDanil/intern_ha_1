package services.parser;

import entities.Employee;

import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
/*
Class which
 */
public class EmployeeParser {
    //Make private constructor, in order to ensure no creation access from other classes, just to utilize class
    private EmployeeParser(){}

    //temporary this method is static, however it's implementation will be changed
    public static List<Employee> getEmployeesFromJson(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, new TypeReference<List<Employee>>() {});
    }
}
