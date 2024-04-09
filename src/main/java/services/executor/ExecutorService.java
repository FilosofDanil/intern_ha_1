package services.executor;

import entities.Employee;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public interface ExecutorService {
    void execute(BlockingQueue<String> pathQueue, BlockingQueue<List<Employee>> destinationQueue, int threads);
}
