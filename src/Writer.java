package src;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * Class for handling of parsing md files and writing a summary based on the files
 * 
 */
public class Writer {
    /**
     * Transforms text in files to string, which is passed to writeText for it to be parsed and written
     * 
     * @param files         ArrayList of md files to be summarized
     * @param outputPath    absolute or relative path to where the summary should be written
     */
    protected static void writeSummary(@org.jetbrains.annotations.NotNull ArrayList<File> files,
                                       @org.jetbrains.annotations.NotNull String outputPath) {
        ArrayList<String> fullMdFile = new ArrayList<String>();

        // Read in all text from every file
        for (File file : files) {
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String data = scanner.nextLine();
                    fullMdFile.add(data);
                }
            } catch (Exception e) {
                System.out.println("ERROR: Unexpected scanner error occurred");
                e.printStackTrace();
            }
            
            String fullText = String.join(" ", fullMdFile);  
            try {
                parseText(fullText, outputPath + "/summary.md");
            } catch (IOException e) {
                System.err.println("ERROR: Output path is not a valid path");
            }
        }
    }

    /**
     * Uses regex to parse relevant text, which is written to a file
     * 
     * @param text              amalgam of file text to be parsed
     * @param outputPath        path to where the summary file should be written
     * @throws IOException      if outputPath cannot be found
     */
    private static void parseText(String text, String outputPath) throws IOException {
        FileWriter writeFile = new FileWriter(outputPath);
        Pattern pattern = Pattern.compile("(?:\\[\\[(.*?)\\]\\])|(?:((#.*?) -))");
        Matcher matcher = pattern.matcher(text);
        while(matcher.find()) {
            String result;
            if (matcher.group(3) == null) {
                result = " - " + matcher.group(1);
            } else {
                result = "\n" + matcher.group(3);
            }
            writeFile.write(result + "\n");
        }
        writeFile.close();
    }
}