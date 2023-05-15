package src;
import java.io.File;
import java.io.FileNotFoundException;
import java.security.KeyException;
import java.util.ArrayList;

/**
 * 
 * Driver for summarizing md files
 * 
 */
public class Driver {
    /**
     * Main class, takes in 3 args: inputPath, flag, and outputPath
     * inputPath: path to root dir to be scraped
     * flag: determines how the final file should be sorted
     * outputPath: path to where the file should be written
     * 
     * @param args  inputPath, flag, and outputPath
     */
    public static void main(String[] args) {
        String inputPath = "C:/Users/Zach/Desktop/Personal Projects/Java/NoteSummary/mdfiles";
        String outputPath = "C:/Users/Zach/Desktop/Personal Projects/Java/NoteSummary";
        String flag = "filename"; // OPTIONS: filename, created, modified

        try {
            ArrayList<File> sortedFiles = FileReader.getAllFiles(inputPath, flag);
            Writer.writeSummary(sortedFiles, outputPath);
        } catch (FileNotFoundException e) {
            System.err.println("Unable to find file path");
        } catch (KeyException e) {
            System.err.println("Invalid flag");
        }

        System.out.println("Summary written in: " + outputPath);
    }
}