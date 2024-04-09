package services.executor.impl;

import entities.Employee;
import filereader.IFileReader;
import filereader.impl.FileReaderImpl;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import processors.JSONProcessor;
import services.executor.ExecutorService;
import services.parser.EmployeeJsonParser;
import services.parser.impl.EmployeeJsonParserImpl;

import java.util.List;
import java.util.concurrent.BlockingQueue;

@Log4j
public class ExecutorServiceImpl implements ExecutorService {
    @Override
    public void execute(BlockingQueue<String> pathQueue, BlockingQueue<List<Employee>> destinationQueue, int threads) {
        EmployeeJsonParser jsonParser = EmployeeJsonParserImpl.getInstance();
        if (threads <= 0 || threads > 8){
            log.warn("Invalid thread count given, it should be more than 0 but no more than 8");
            return;
        }
        while (threads > 0){
            IFileReader reader = new FileReaderImpl();
            JSONProcessor processor = new JSONProcessor(jsonParser, reader, pathQueue, destinationQueue);
            threads--;
            processor.start();
        }
    }
}
