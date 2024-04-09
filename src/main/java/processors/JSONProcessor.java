package processors;

import entities.Employee;
import filereader.IFileReader;
import services.parser.EmployeeJsonParser;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class JSONProcessor extends Thread{
    private final EmployeeJsonParser jsonParser;

    private final IFileReader fileReader;

    private final BlockingQueue<String> sourceQueue;

    private final BlockingQueue<List<Employee>> destinationQueue;

    public JSONProcessor(EmployeeJsonParser jsonParser, IFileReader fileReader, BlockingQueue<String> sourceQueue, BlockingQueue<List<Employee>> destinationQueue) {
        this.jsonParser = jsonParser;
        this.fileReader = fileReader;
        this.sourceQueue = sourceQueue;
        this.destinationQueue = destinationQueue;
    }

    @Override
    public void run() {
        while(true){
            try {
                String path = sourceQueue.take();
                List<Employee> result = jsonParser.parseJsonFile(fileReader.read(path));
                destinationQueue.put(result);
                if (sourceQueue.isEmpty()){
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
