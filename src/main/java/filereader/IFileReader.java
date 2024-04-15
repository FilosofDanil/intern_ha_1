package filereader;

import entities.Employee;

import java.util.List;

public interface IFileReader {
    List<Employee> read(String fileName);
}
