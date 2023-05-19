package src.interfaces;

import java.io.IOException;

/**
 * Interface for writing for file
 */
public interface Writer {
    /**
     * Makes a file at given output path
     *
     * @param path          path of file destination
     * @throws IOException  path of file does not exist
     */
    public void writeFile(String path) throws IOException;
}
