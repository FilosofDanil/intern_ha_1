package filereader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Paths;

/*
    Reader class, which is on DAO layer(we're accessing data from JSON)
 */
public class FileReader {
    //Make private constructor, in order to ensure no creation access from other classes, just to utilize class
    private FileReader(){}
    //temporary this method is static, however it's implementation will be changed
    public static String read(String fileName){
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader br = java.nio.file.Files.newBufferedReader(Paths.get(fileName))) {
           String line = br.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
