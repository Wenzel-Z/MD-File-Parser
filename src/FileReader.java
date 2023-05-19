package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Queue;
import java.util.HashMap;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.KeyException;

/**
 * Class used for reading and sorting md files present in the directory tree
 *
 * @author Zach
 */
public class FileReader {

    /**
     * Returns an ArrayList of File spanning the directory tree given a root dir
     * sorted by the flag given
     * 
     * @param   baseDirectory  the base directory of the file tree to search
     * @param   flag           the flag to indicate the sorting of the ArrayList
     * @return                 ArrayList of md file objects
     * @throws  FileNotFoundException   when file is unable to be found
     */
    protected static ArrayList<File> getAllFiles(String baseDirectory, String flag) throws FileNotFoundException, KeyException {
        Queue<String> dirFiles= new ArrayDeque<String>();
        ArrayList<File> files = new ArrayList<File>();

        // Hashmap for selecting sort
        HashMap<String, Runnable> flags = new HashMap<>();
        flags.put("filename", () -> files.sort((file1, file2) -> file1.getName().compareTo(file2.getName())));
        flags.put("created", () -> sortByAttr(files, true));
        flags.put("modified", () -> sortByAttr(files, false));

        dirFiles.add(baseDirectory);
        while(dirFiles.peek() != null) {
            File dirFile = new File(dirFiles.remove());

            // Retrieve all directories and add their paths to the queue
            File[] newFiles = dirFile.listFiles(File::isDirectory);
            if (newFiles != null) {
                for (File file : newFiles) {
                    dirFiles.add(file.getAbsolutePath());
                }
            }

            // Retrieve all files in directory and add them to files if they are a markdown file
            File[] mdFiles = dirFile.listFiles(File::isFile);
            if (mdFiles != null) {
                for (File file : mdFiles) {
                    if(file.getName().endsWith(".md")) {
                        files.add(file);
                    }
                }
            }
        }
        if (flags.containsKey(flag)) {
            flags.get(flag).run();
        } else {
            throw new KeyException();
        }

        return files;
    }

    /**
     * Sorts a given arraylist of files by the creation or last modified date
     * Better with MDFile class and comparator
     * 
     * @param files             ArrayList of files to be sorted
     * @param sortByCreation    Boolean for sorting by creation date or last modified time
     */
    private static void sortByAttr(ArrayList<File> files, Boolean sortByCreation) {
        ArrayList<BasicFileAttributes> attributes = createAttributes(files);

        // Use attributes to sort file ArrayList
        for (int i = 0; i < files.size() - 1; i++) {
            for (int j = 0; j < files.size()- i - 1; j++) {
                BasicFileAttributes file1Attr = attributes.get(j);
                BasicFileAttributes file2Attr = attributes.get(j+1);
                if (sortByCreation) {
                    if (file1Attr.creationTime().compareTo(file2Attr.creationTime()) > 0) {
                        swap(files, attributes, file1Attr, file2Attr, j);
                    }
                } else {
                    if (file1Attr.lastModifiedTime().compareTo(file2Attr.lastModifiedTime()) > 0) {
                        swap(files, attributes, file1Attr, file2Attr, j);
                    }
                }
            }
        }
    }

    /**
     * Creates arraylist of attributes from an arraylist of files
     *
     * @param files     files to get attributes from
     * @return          arraylist of attributes
     */
    private static ArrayList<BasicFileAttributes> createAttributes(ArrayList<File> files) {
        ArrayList<BasicFileAttributes> attributes = new ArrayList<>();

        for (File file : files) {
            Path path = Path.of(file.getAbsolutePath());
            try {
                BasicFileAttributes attribute = Files.readAttributes(path, BasicFileAttributes.class);
                attributes.add(attribute);
            } catch (IOException e) {
                System.err.println("ERROR: Unable to read attributes of files");
            }
        }
        return attributes;
    }

    /**
     * Helper function for sort, used to switch elements in both arrays
     * 
     * @param files         arraylist of files being sorted      
     * @param attributes    arraylist of attributes used in being sorted
     * @param file1Attr     attribute being sorted used in comparison
     * @param file2Attr     attribute being sorted used in comparison
     * @param j             int of index
     */
    private static void swap(ArrayList<File> files, ArrayList<BasicFileAttributes> attributes,
                            BasicFileAttributes file1Attr, BasicFileAttributes file2Attr, int j) {
        File file1 = files.get(j);
        File file2 = files.get(j+1);

        files.set(j, file2);
        files.set(j+1, file1);

        attributes.set(j, file2Attr);
        attributes.set(j+1, file1Attr);
    }
}
