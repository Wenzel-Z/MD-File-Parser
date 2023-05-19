package src;

import java.util.List;
import java.util.Scanner;


/**
 * Class for handling the logic and console display for a study session
 *
 * @author Zach
 */
public class FlashCardsDriver {
    private int easyToHard = 0;
    private int hardToEasy = 0;
    private int questionCount = 0;

    /**
     * Driver for the study session, reads inputs and displays output
     */
    public void startStudySession() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome!\nHow many cards would you like to study?");

        int amount = Integer.parseInt(scanner.nextLine());
        List<Card> studySessionCards = CardList.getRandomListOfCards(amount);

        for (Card card : studySessionCards) {
            System.out.println(card.getQuestion());

            System.out.println("\n\n1: Mark easy, 2: Mark hard, 3: Show answer, 4: Exit");
            int selection = Integer.parseInt(scanner.nextLine());

            if (selection == 4) {
                break;
            } else {
                handleInput(selection, card);
                questionCount++;
            }
        }
        summarizeSession();
    }

    /**
     * Handles logic and updates cards based on given input
     *
     * @param selection     input the user has given for the menu
     * @param card          card to be updated
     */
    private void handleInput(int selection, Card card) {
        if (selection == 1 && card.getDifficulty() > 0) {
            card.setDifficulty(0);
            hardToEasy++;
        } else if (selection == 2 && card.getDifficulty() == 0) {
            card.setDifficulty(1);
            easyToHard++;
        } else if (selection == 3) {
            if (card.getDifficulty() > 0) {
                card.setDifficulty(1);
                easyToHard++;
            }
            System.out.println(card.getAnswer() + "\n");
        }
    }

    /**
     * Summarizes the study session at the end of input
     */
    private void summarizeSession() {
        System.out.println("\nYou answered " + questionCount + " questions");
        System.out.println("There are " + easyToHard + " questions that went from easy to hard, and");
        System.out.println(hardToEasy + " questions that went from hard to easy");
        System.out.println("There are currently " + CardList.getHardCount() + " hard questions and "
                + CardList.getEasyCount() + " easy questions in the question bank");

    }
}
