package filereader.impl;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import entities.Employee;
import filereader.IFileReader;
import services.parser.EmployeeJsonParser;
import services.parser.impl.EmployeeJsonParserImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that reads files, using BufferedReader
 */
public class FileReaderImpl implements IFileReader {
    private final EmployeeJsonParser employeeJsonParser = EmployeeJsonParserImpl.getInstance();

    /**
     * Method which reads file by its path
     */
    @Override
    public List<Employee> read(String fileName) {
        List<Employee> employees = new ArrayList<>();
        JsonFactory factory = new JsonFactory();
        File file = new File(fileName);
        try (JsonParser parser = factory.createParser(file)) {
            Employee employee = new Employee();
            while (parser.nextToken() != JsonToken.END_ARRAY) {
                String fieldName = parser.getCurrentName();
                if (parser.getCurrentToken() == JsonToken.FIELD_NAME) {
                    parser.nextToken();
                    String field = parser.getText();
                    if (fieldName.equals("name")) {
                        employee.setName(field);
                    } else if (fieldName.equals("surname")) {
                        employee.setSurname(field);
                    } else if (fieldName.equals("jobs")) {
                        employee.setJobs(field);
                    } else if (fieldName.equals("companyName")) {
                        employee.setCompanyName(field);
                    } else if (fieldName.equals("hiringDate")) {
                        employee.setHiringDate(field);
                    } else if (fieldName.equals("salary")) {
                        employee.setSalary(Integer.parseInt(field));
                    }
                    if (employee.getName() != null && employee.getSurname()!=null
                            && employee.getCompanyName() != null && employee.getJobs() != null
                            && employee.getHiringDate() != null && employee.getSalary() != null ) {
                        employees.add(employee);
                        employee = new Employee();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employees;
//        StringBuilder stringBuilder = new StringBuilder();
//        try (BufferedReader br = java.nio.file.Files.newBufferedReader(Paths.get(fileName))) {
//            String line = br.readLine();
//            while (line != null) {
//                stringBuilder.append(line);
//                line = br.readLine();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return employeeJsonParser.parseJsonFile(stringBuilder.toString());
    }


}
