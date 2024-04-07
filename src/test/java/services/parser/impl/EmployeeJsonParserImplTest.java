package services.parser.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import entities.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import services.parser.EmployeeJsonParser;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeJsonParserImplTest {

    @Test
    void parseJsonFile() {
        //given
        String validJson = "[\n" +
                "  {\n" +
                "    \"name\": \"Lana\",\n" +
                "    \"surname\": \"Holcomb\",\n" +
                "    \"salary\": 45466,\n" +
                "    \"companyName\": \"Minga\",\n" +
                "    \"hiringDate\": \"2013-03-03\",\n" +
                "    \"jobs\": \"Frontend-Developer\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"name\": \"Fitzpatrick\",\n" +
                "    \"surname\": \"Hickman\",\n" +
                "    \"salary\": 56414,\n" +
                "    \"companyName\": \"Duoflex\",\n" +
                "    \"hiringDate\": \"2010-08-31\",\n" +
                "    \"jobs\": \"Team-Lead\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"name\": \"Stephenson\",\n" +
                "    \"surname\": \"Waller\",\n" +
                "    \"salary\": 77697,\n" +
                "    \"companyName\": \"Sultraxin\",\n" +
                "    \"hiringDate\": \"2014-03-02\",\n" +
                "    \"jobs\": \"SQL-Developer\"\n" +
                "  }\n" +
                "]";
        String invalidJson = "[\n" +
                "  {\n" +
                "    \"name\": \"Pamela\",\n" +
                "    \"surname\": \"Anthony\",\n" +
                "    \"salary\": 76996,\n" +
                "    \"companyName\": \"Housedown\",\n" +
                "    \"hiringDate\": \"2017-01-11\",\n" +
                "]";
        EmployeeJsonParser parser = EmployeeJsonParserImpl.getInstance();
        //when
        List<Employee> employees = parser.parseJsonFile(validJson);
        List<Employee> emptyEmployees = parser.parseJsonFile(invalidJson);
        //then
        assertEquals(3, employees.size());
        assertEquals(0, emptyEmployees.size());
    }
}