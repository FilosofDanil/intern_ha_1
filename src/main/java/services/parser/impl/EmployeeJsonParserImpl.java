package services.parser.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Employee;
import lombok.extern.log4j.Log4j;
import services.parser.EmployeeJsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 Class which parse JSON into List of objects
 */
@Log4j
public class EmployeeJsonParserImpl implements EmployeeJsonParser {
    private static EmployeeJsonParserImpl employeeJsonParser;

    private EmployeeJsonParserImpl(){}

    @Override
    public List<Employee> parseJsonFile(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Employee> parsedList = new ArrayList<>();
        try {
            parsedList.addAll(objectMapper.readValue(json, new TypeReference<List<Employee>>() {
            }));
        } catch (JsonProcessingException e) {
            log.error("Error occurred, while trying to parse JSON. " +
                    "Please provide proper input value format! Returned an empty list.", e);
        }
        return parsedList;
    }

    public static EmployeeJsonParserImpl getInstance() {
        if(employeeJsonParser == null){
            employeeJsonParser = new EmployeeJsonParserImpl();
        }
        return employeeJsonParser;
    }
}
