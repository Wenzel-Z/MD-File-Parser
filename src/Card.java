package src;

/**
 * Card class for storage of flash card data
 *
 * @author Zach
 */
public class Card extends CardList {

    private final String question;
    private final String answer;
    private int difficulty = 1;

    /**
     * Constructor for the card class, includes the question to be asked and answer to the question
     *
     * @param question      question on card
     * @param answer        answer to given question
     */
    public Card(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    /**
     * Constructor for the card class, includes the question to be asked, the answer to the question, and the
     * difficulty of the card
     *
     * @param question      question on card
     * @param answer        answer to given question
     * @param difficulty    difficulty of the card
     */
    public Card(String question, String answer, int difficulty) {
        this.question = question;
        this.answer = answer;
        this.difficulty = difficulty;
    }

    /**
     * Sets the difficulty for this card
     *
     * @param difficulty    number to set the difficulty to
     */
    public void setDifficulty(int difficulty) {
        if (!(this.difficulty == difficulty)) {
            if (this.difficulty == 0) {
                decrementEasyCount();
                incrementHardCount();
            } else {
                incrementEasyCount();
                decrementHardCount();
            }
            this.difficulty = difficulty;
        }
    }

    /**
     * Gets the question from the card
     *
     * @return  the card's question
     */
    public String getQuestion() { return this.question; }

    /**
     * Gets the answer from the card
     *
     * @return  the card's answer
     */
    public String getAnswer() { return this.answer; }

    /**
     * Gets the difficulty for the card
     *
     * @return  difficulty of card
     */
    public int getDifficulty() { return this.difficulty; }
    /**
     * Gets the difficulty as a string
     *
     * @return  difficulty field as string
     */
    public String getDifficultyAsString() {
        if (difficulty == 0) {
            return "Easy";
        } else if (difficulty == 1) {
            return "Hard";
        } else {
            return "Good luck";
        }
    }

    /**
     * Gets card as a string
     *
     * @return      the question and answer as a string, with the difficulty
     */
    public String toStringWithDifficulty() {
        return this.question + ":::" + this.answer +":::" + this.difficulty;
    }

    //TODO fix use of this method in ensuring no duplicate cards are created
    public boolean equals(Card card) {
        return this.toString().equals(card.toString());
    }

    /**
     * gets question and answer from card
     *
     * @return      a string with the question and answer
     */
    public String toString() {
        return this.question + " " + this.answer;
    }
}

