package processors;

import filereader.IFileReader;
import lombok.extern.log4j.Log4j;

import java.util.concurrent.BlockingQueue;

/**
 * Сlass representing a thread for processing JSON files concurrently.
 * This class reads JSON files using an {@link IFileReader} implementation and operates on a blocking queue of file paths.
 */
@Log4j
public class JSONProcessor extends Thread {

    private final IFileReader fileReader;

    private final BlockingQueue<String> sourceQueue;

    private final String fieldName;

    public JSONProcessor(IFileReader fileReader, BlockingQueue<String> sourceQueue, String fieldName) {
        this.fileReader = fileReader;
        this.sourceQueue = sourceQueue;
        this.fieldName = fieldName;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String path = sourceQueue.take();
                fileReader.read(path, fieldName);
                if (sourceQueue.isEmpty()) {
                    break;
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }
}
