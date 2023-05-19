package src;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyException;
import java.util.ArrayList;

/**
 * 
 * Driver for summarizing md files and generating flashcards
 *
 * @author Zach
 */
public class Driver {

    /**
     * Main class, takes in 3 args: the input path, flag for sorting , and the output path
     * if no args are given, starts a study session
     *
     * @param args      inputPath, flag, and outputPath
     * @param args[0]   path to input directory
     * @param args[1]   flag for sorting summary files
     * @param args[2]   path to output directory
     */
    public static void main(String[] args) {
        String defaultDir = "C:/Users/Zach/Desktop/Personal Projects/Java/NoteSummary";
        CardList cardList = new CardList();
        if (args.length == 3) {
            String inputPath = args[0];
            String outputPath = args[1];
            String flag = args[2]; // OPTIONS: filename, created, modified

            SummaryWriter fileWriter = new SummaryWriter();
            try {
                ArrayList<File> sortedFiles = FileReader.getAllFiles(inputPath, flag);
                fileWriter.writeSummary(sortedFiles, outputPath);
                cardList.writeFile(outputPath);
            } catch (FileNotFoundException e) {
                System.err.println("Invalid file path");
            } catch (KeyException e) {
                System.err.println("Invalid flag");
            } catch (IOException e) {
                System.err.println("Invalid path for list of cards");
            }

            System.out.println("Summary written in: " + outputPath);
            System.exit(0);
        }

        CardList.addCardsFromFile(defaultDir + "/cards.sr");

        FlashCardsDriver flashCardsDriver = new FlashCardsDriver();
        flashCardsDriver.startStudySession();
        cardList.setAppend(false);

        try {
            cardList.writeFile(defaultDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
