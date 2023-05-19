package src;

import src.interfaces.Writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Class for handling of parsing md files and writing a summary based on the files
 *
 * @author Zach
 */
public class SummaryWriter implements Writer {
    String text;
    String outputPath;
    /**
     * Transforms text in files to string, which is passed to writeText
     * 
     * @param files         ArrayList of md files to be summarized
     * @param outputPath    absolute or relative path to where the summary should be written
     */
    protected void writeSummary(@NotNull ArrayList<File> files, @NotNull String outputPath) {
        ArrayList<String> fullMdFile = new ArrayList<>();

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
            
            text = String.join(" ", fullMdFile);
            try {
                this.writeFile(outputPath);
            } catch (IOException e) {
                System.err.println("ERROR: Output path is not a valid path");
            }
        }
    }

    /**
     * Uses regex to parse relevant text, which is written to a file
     *
     * @param outputPath        path to where the summary file should be written
     *
     * @throws IOException      if outputPath cannot be found
     */
    @Override
    public void writeFile(String outputPath) throws IOException {
        String fileName = outputPath + "/summary.md";
        FileWriter fileWriter = new FileWriter(fileName);

        // Matcher for summary file
        Pattern summaryPattern = Pattern.compile("\\[\\[(((?!:::).)*?)]]|\\[\\[(.*?)(:::)(.*?)]]|((#.*?) -)");
        Matcher summaryMatcher = summaryPattern.matcher(text);
        String question = null;
        while (summaryMatcher.find()) {
            if (summaryMatcher.group(1) != null) {
                fileWriter.write(" - " + summaryMatcher.group(1) + "\n");
            } else if (summaryMatcher.group(7) != null) {
                fileWriter.write("\n" + summaryMatcher.group(7) + "\n");
            } else if (summaryMatcher.group(3) != null && summaryMatcher.group(5) != null) {
                CardList.addCard(summaryMatcher.group(3), summaryMatcher.group(5));
            }
        }
        fileWriter.close();
    }
}