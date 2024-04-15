package services.executor.impl;

import entities.Employee;
import filereader.IFileReader;
import filereader.impl.FileReaderImpl;
import lombok.extern.log4j.Log4j;
import processors.JSONProcessor;
import services.executor.IExecutorService;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Log4j
public class ExecutorServiceImpl implements IExecutorService {
    private final ExecutorService executor = Executors.newFixedThreadPool(8);
    @Override
    public void execute(BlockingQueue<String> pathQueue, BlockingQueue<List<Employee>> destinationQueue, int threads) {
        if (threads <= 0 || threads > 8){
            log.warn("Invalid thread count given, it should be more than 0 but no more than 8");
            return;
        }
        while (threads > 0){
            IFileReader reader = new FileReaderImpl();
            JSONProcessor processor = new JSONProcessor(reader, pathQueue, destinationQueue);
            executor.execute(processor);
            threads--;
        }
        executor.shutdown();
    }

    @Override
    public boolean isFinished() {
        return executor.isTerminated();
    }
}
