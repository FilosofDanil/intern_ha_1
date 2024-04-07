package filereader.impl;

import filereader.IFileReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Paths;

public class FileReaderImpl implements IFileReader {
    @Override
    public String read(String fileName){
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
