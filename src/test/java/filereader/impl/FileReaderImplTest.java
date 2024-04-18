package filereader.impl;

import com.fasterxml.jackson.core.JsonParseException;
import filereader.IFileReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.counters.StatisticCounter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FileReaderImplTest {

    private StatisticCounter mockDataCounter;

    @BeforeEach
    void setUp() {
        mockDataCounter = new StatisticCounter() {
            private Map<String, Integer> statisticMap = new HashMap<>();

            @Override
            public Map<String, Integer> getEmployeeStatistic() {
                return statisticMap;
            }

            @Override
            public void putValueInMap(String fieldValue) {
                if (!statisticMap.containsKey(fieldValue)) {
                    statisticMap.put(fieldValue, 1);
                } else {
                    statisticMap.put(fieldValue, statisticMap.get(fieldValue) + 1);
                }

            }

            @Override
            public void cleanMap() {
                statisticMap = new HashMap<>();
            }
        };
    }

    @AfterEach
    void cleanData() {
        mockDataCounter.cleanMap();
    }

    @Test
    void read() {
        //given
        IFileReader fileReader = new FileReaderImpl(mockDataCounter);
        //when
        try {
            fileReader.read("src/test/resources/test.json", "name");
        } catch (IOException | NoSuchFieldException ignored) {
            //What are you doing? Me? Nothing, just hanging around....
        }
        //then
        Map<String, Integer> statisticMap = mockDataCounter.getEmployeeStatistic();
        assert !statisticMap.isEmpty();
        assertEquals(statisticMap.get("John"), 1);
        assertEquals(statisticMap.get("Jane"), 1);
        assertEquals(statisticMap.get("Michael"), 1);
    }

    @Test
    void readWrongPath() {
        //given
        IFileReader fileReader = new FileReaderImpl(mockDataCounter);
        //when
        Exception exception = assertThrows(FileNotFoundException.class, ()
                -> fileReader.read("failure/test.json", "name"));
        //then
        String expectedMessage = "The system cannot find the path";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void readWrongParameter() {
        //given
        IFileReader fileReader = new FileReaderImpl(mockDataCounter);
        //when
        Exception exception = assertThrows(NoSuchFieldException.class, ()
                -> fileReader.read("src/test/resources/test.json", "wrong"));
        //then
        String expectedMessage = "";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void readInvalidJsonFile() {
        //given
        IFileReader fileReader = new FileReaderImpl(mockDataCounter);
        //when
        Exception exception = assertThrows(JsonParseException.class, ()
                -> fileReader.read("src/test/resources/failed.json", "name"));
        //then
        String expectedMessage = "The file does not match the JSON format.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void readEmptyJsonFile() {
        //given
        IFileReader fileReader = new FileReaderImpl(mockDataCounter);
        //when
        Exception exception = assertThrows(JsonParseException.class, ()
                -> fileReader.read("src/test/resources/fake.json", "name"));
        //then
        String expectedMessage = "Empty file specified!";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}