package processors;

import entities.Employee;
import filereader.IFileReader;
import lombok.extern.log4j.Log4j;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;

@Log4j
public class JSONProcessor extends Thread{

    private final IFileReader fileReader;

    private final BlockingQueue<String> sourceQueue;

    private final BlockingQueue<List<Employee>> destinationQueue;

    public JSONProcessor(IFileReader fileReader, BlockingQueue<String> sourceQueue, BlockingQueue<List<Employee>> destinationQueue) {
        this.fileReader = fileReader;
        this.sourceQueue = sourceQueue;
        this.destinationQueue = destinationQueue;
    }

    @Override
    public void run() {
        while(true){
            try {
                String path = sourceQueue.take();
                List<Employee> result = fileReader.read(path);
                destinationQueue.put(result);
                if (sourceQueue.isEmpty()){
                    break;
                }
            } catch (InterruptedException | IOException e) {
               log.error(e.getMessage());
            }
        }
    }
}
