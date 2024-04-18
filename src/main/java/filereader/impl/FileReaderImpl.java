package filereader.impl;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import entities.Employee;
import filereader.IFileReader;
import services.counters.StatisticCounter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Class that reads JSON files
 * Implementation of the {@link IFileReader} interface that reads JSON files using a {@link BufferedReader}.
 * The read method extracts data from the JSON file and updates a {@link StatisticCounter}.
 */
public class FileReaderImpl implements IFileReader {

    private final StatisticCounter counter;

    public FileReaderImpl(StatisticCounter statisticCounter) {
        this.counter = statisticCounter;
    }

    /**
     * Method which reads json file and put data in the counter
     *
     * @param fileName  the path of the JSON file to read
     * @param fieldName the name of the field in the JSON object to extract data from
     * @throws IOException          if an I/O error occurs while reading the file
     * @throws NoSuchFieldException if the specified field name does not exist in the JSON object
     * @throws JsonParseException   if the file is empty, does not exist, or does not match the JSON format
     */
    @Override
    public void read(String fileName, String fieldName) throws IOException, NoSuchFieldException {
        checkField(fieldName);
        JsonFactory factory = new JsonFactory();
        File file = new File(fileName);
        if (!file.exists()) {
            throw new FileNotFoundException("The system cannot find the path");
        } else if (file.length() == 0) {
            throw new JsonParseException("Empty file specified!");
        }
        try (JsonParser parser = factory.createParser(file)) {
            if (parser.nextToken() != JsonToken.START_ARRAY) {
                throw new JsonParseException("The file does not match the JSON format.");
            }
            while (parser.nextToken() != JsonToken.END_ARRAY) {
                String fieldTaken = parser.getCurrentName();
                if (parser.getCurrentToken() == JsonToken.FIELD_NAME) {
                    parser.nextToken();
                    String field = parser.getText();
                    if (fieldTaken.equals(fieldName)) {
                        counter.putValueInMap(field);
                    }
                }
            }
        } catch (JsonParseException e) {
            throw new JsonParseException("The file does not match the JSON format.");
        }
    }

    private void checkField(String fieldName) throws NoSuchFieldException {
        Class<?> employeeClass = Employee.class;
        employeeClass.getDeclaredField(fieldName);
    }


}
