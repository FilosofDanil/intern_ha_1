package services.executor;

import entities.Employee;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public interface IExecutorService {
    void execute(BlockingQueue<String> pathQueue, BlockingQueue<List<Employee>> destinationQueue, int threads);

    boolean isFinished();
}
