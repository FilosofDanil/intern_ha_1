package filereader;

import entities.Employee;

import java.io.IOException;
import java.util.List;

public interface IFileReader {
    List<Employee> read(String fileName) throws IOException;
}
