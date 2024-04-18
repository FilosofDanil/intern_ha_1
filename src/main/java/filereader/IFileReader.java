package filereader;

import java.io.IOException;

public interface IFileReader {
    void read(String fileName, String fieldName) throws IOException, NoSuchFieldException;
}
