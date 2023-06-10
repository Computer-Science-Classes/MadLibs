import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A program to play the game of Mad Libs
 */
public class MadLibs {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("Welcome to the game of Mad Libs.");
        while (running) {
            System.out.print("(C)reate mad-lib, (V)iew mad-lib, (Q)uit? ");
            String choice = scanner.nextLine().toUpperCase();

            switch (choice) {
                case "C":
                    createMadLib(scanner);
                    break;
                case "V":
                    viewMadLib(scanner);
                    break;
                case "Q":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }

        System.out.println("Goodbye!");
    }

    /**
     * Creates a new Mad Lib based on user input
     *
     * @param scanner the scanner to read user input
     */
    private static void createMadLib(Scanner scanner) {
        while (true) {
            System.out.print("Input file name: ");
            String inputFileName = scanner.nextLine();

            File inputFile = getTemplateFile(inputFileName);
            if (inputFile == null) {
                System.out.println("File not found. Please try again.");
            } else {
                System.out.println();
                System.out.print("Output file name: ");
                String outputFileName = scanner.nextLine();

                File outputFolder = new File("MadLibOutput");
                outputFolder.mkdirs(); // Create the output folder if it doesn't exist

                File outputFile = new File(outputFolder, outputFileName);

                try (Stream<String> lines = getLines(inputFile);
                        PrintWriter writer = new PrintWriter(outputFile)) {

                    System.out.println();
                    lines.map(line -> processLine(line, scanner))
                            .forEach(writer::println);

                    System.out.println();
                    System.out.println("Your mad-lib has been created!");
                    System.out.println();
                    break;
                } catch (FileNotFoundException e) {
                    System.out.println("Error: Output file not found. Please try again.");
                    System.out.println();
                }
            }
        }
    }

    /**
     * Reads and returns the lines of a file
     *
     * @param file the file to read
     * @return a stream of lines from the file
     */
    private static Stream<String> getLines(File file) {
        try {
            return Files.lines(file.toPath());
        } catch (FileNotFoundException e) {
            return Stream.empty();
        } catch (Exception e) {
            e.printStackTrace();
            return Stream.empty();
        }
    }

    /**
     * Processes a line of the Mad Lib template,
     * replacing placeholders with user input
     *
     * @param line    the line to process
     * @param scanner the scanner to read user input
     * @return the processed line
     */
    private static String processLine(String line, Scanner scanner) {
        return Stream.of(line.split(" "))
                .map(token -> token.startsWith("<") && token.endsWith(">")
                        ? getResponse(token, scanner)
                        : token)
                .collect(Collectors.joining(" "));
    }

    /**
     * Prompts the user to provide a response for a placeholder
     *
     * @param token   the placeholder token
     * @param scanner the scanner to read user input
     * @return the user's response
     */
    private static String getResponse(String token, Scanner scanner) {
        String placeholder = token.substring(1, token.length() - 1);
        String prompt = getPrompt(placeholder);
        System.out.print(prompt);
        return scanner.nextLine();
    }

    /**
     * Displays the content of a Mad Lib template
     *
     * @param scanner the scanner to read user input
     */
    private static void viewMadLib(Scanner scanner) {
        System.out.print("Input file name: ");
        String inputFileName = scanner.nextLine();

        File inputFile = getTemplateFile(inputFileName);
        if (inputFile == null) {
            File outputFolder = new File("MadLibOutput");
            if (outputFolder.exists() && outputFolder.isDirectory()) {
                File[] files = outputFolder.listFiles();
                boolean found = false;
                for (File file : files) {
                    if (file.getName().equalsIgnoreCase(inputFileName)) {
                        found = true;
                        System.out.println();
                        getLines(file).forEach(System.out::println);
                        System.out.println();
                        break;
                    }
                }
                if (!found) {
                    System.out.print("File not found. Please try again. Input file name: ");
                }
            } else {
                System.out.print("File not found. Please try again. Input file name: ");
            }
        } else {
            System.out.println();
            getLines(inputFile).forEach(System.out::println);
            System.out.println();
        }
    }

    /**
     * Retrieves the Mad Lib template file
     *
     * @param fileName the name of the template file
     * @return the template file
     */
    private static File getTemplateFile(String fileName) {
        String templateFolderPath = "src/MadLibTemplates";
        String fileExtension = ".txt";

        // Check if the file name has an extension
        if (!fileName.contains(".")) {
            fileName += fileExtension;
        }

        // Check if the file exists in the specified folder
        File templateFolder = new File(templateFolderPath);
        File templateFile = new File(templateFolder, fileName);
        if (templateFile.exists()) {
            return templateFile;
        }

        // If the file does not exist, try adding the extension if it was missing
        if (!fileName.endsWith(fileExtension)) {
            templateFile = new File(templateFolder, fileName + fileExtension);
            if (templateFile.exists()) {
                return templateFile;
            }
        }

        // File not found
        return null;
    }

    /**
     * Generates the prompt for a placeholder
     *
     * @param placeholder the placeholder name
     * @return the prompt string
     */
    private static String getPrompt(String placeholder) {
        String article = isVowel(placeholder.charAt(0)) ? "an" : "a";
        return "Please type " + article + " " + placeholder.toLowerCase() + ": ";
    }

    /**
     * Checks if a character is a vowel
     *
     * @param c the character to check
     * @return true if the character is a vowel, false otherwise
     */
    private static boolean isVowel(char c) {
        return "AEIOUaeiou".indexOf(c) != -1;
    }
}
