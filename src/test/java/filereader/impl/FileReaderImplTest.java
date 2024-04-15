package filereader.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileReaderImplTest {

    @Test
    void read() {
        String json = "[  {    \"name\": \"John\",    \"surname\": \"Doe\",    \"salary\": 50000,    \"companyName\": \"XYZ Corporation\",    \"hiringDate\": \"2023-05-15\",    \"jobs\": \"Manager\"  },  {    \"name\": \"Jane\",    \"surname\": \"Smith\",    \"salary\": 60000,    \"companyName\": \"ABC Industries\",    \"hiringDate\": \"2022-11-20\",    \"jobs\": \"Sales, Java-Developer\"  },  {    \"name\": \"Michael\",    \"surname\": \"Johnson\",    \"salary\": 55000,    \"companyName\": \"LMN Enterprises\",    \"hiringDate\": \"2024-02-10\",    \"jobs\": \"HR, Sales\"  }]";

        assertEquals(json, new FileReaderImpl().read("test.json"));
    }
}