package filereader.impl;

import entities.Employee;
import filereader.IFileReader;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileReaderImplTest {

    @Test
    void read() {
        //given
        IFileReader fileReader = new FileReaderImpl();
        //when
        List<Employee> employees = null;
        try {
            employees = fileReader.read("src/test/resources/test.json");
        } catch (IOException ignored) {
        }
        //then
        assert !employees.isEmpty();;
        assertEquals(employees.get(0).getName(), "John");
        assertEquals(employees.get(0).getSurname(), "Doe");
        assertEquals(employees.get(0).getSalary(), 50000);
        assertEquals(employees.get(0).getCompanyName(), "XYZ Corporation");
        assertEquals(employees.get(0).getJobs(), "Manager");
        assertEquals(employees.get(0).getHiringDate(), "2023-05-15");
    }

    @Test
    void failedRead() {
        //given
        IFileReader fileReader = new FileReaderImpl();
        //when
        Exception exception = assertThrows(FileNotFoundException.class, ()
                -> fileReader.read("failure/test.json"));
        //then
        String expectedMessage = "The system cannot find the path";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}