import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.Random;

/*
Connor Bennett
Section 3
Jotto assignment
2/8/23
 */

public class Jotto {
//    initialize private variables
    private static final int WORD_SIZE = 5;
    private String filename;
    private static final boolean DEBUG = true ;
    private ArrayList<String> playedWords = new ArrayList<>();
    private ArrayList<String> wordList = new ArrayList<>();
    private ArrayList<String> playerGuesses = new ArrayList<>();
    private String currentWord;
    private int score;


//    SETTERS & GETTERS to use for manipulating the variables in each method
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public ArrayList<String> getPlayedWords() {
        return playedWords;
    }

    public void setPlayedWords(ArrayList<String> playedWords) {
        this.playedWords = playedWords;
    }

    public ArrayList<String> getWordList() {
        return wordList;
    }

    public void setWordList(ArrayList<String> wordList) {
        this.wordList = wordList;
    }

    public ArrayList<String> getPlayerGuesses() {
        return playerGuesses;
    }

    public void setPlayerGuesses(ArrayList<String> playerGuesses) {
        this.playerGuesses = playerGuesses;
    }

    public String getCurrentWord() {
        return currentWord;
    }

    public void setCurrentWord(String currentWord) {
        this.currentWord = currentWord;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    /**
     * This is the only constructor used in this program.  It sets the field filename to the value of the passed in parameter filename.
     * It then calls the method readWords();
     * @param filename
     */
    public Jotto(String filename) {
        this.filename = filename;
        readWords(filename);
    }


    /**
     * This method uses the field filename to open a file and read the contents.  The file will have a single word per line and will not have any extraneous whitespace or other punctuation.
     * Use a File object and a Scanner object.
     * This method MUST use try catch blocks when reading the file.
     * If there is an error reading from the file the method should print out "Couldn't open " concatenated with the current filename.
     * E.g.  Couldn't open demo.txt
     * Each word from the opened file should be added to the ArrayList wordList.  Check for duplicates before adding the word to the ArrayList.
     * @param filename
     * @return
     */
    public ArrayList<String> readWords(String filename) {
        try {
            File f = new File(filename);
            Scanner scnr = new Scanner(f);
            while (scnr.hasNextLine()){
                String word = scnr.nextLine();
                if(wordList.contains(word)){
                        word = word;}
                else{
                    word.trim();
                    word.strip();
                    wordList.add(word);}
            }
        } catch (Exception e) {
            System.out.println("couldn't open " + filename);}
        return wordList;}


    /**
     * The user will be able to choose four options or quit.
     * Entering 1 or one,  will call the pickword() method.  If pickword() returns false, call showPlayerGuesses().
     * If pickword() returns true, call the  guess() method.  This will update the field score with the return value of the guess() method.  It will then display the player's updated score.
     * Entering 2 or two will call the showWordList() method
     * Entering 3 or three will call the showPlayedWords() method
     * Entering 4 or four will call the showPlayerGuesses() method
     * If the player enters zz ( ZZ, zZ, orZz) the application will quit.
     * Any other value will display a message stating that the input was unknown (see example run below for correct output)
     * @return
     */
    public boolean play() {
// build menu
        System.out.println("Welcome to the game.\n");
        System.out.println("Current Score: " + score);
        System.out.println("=-=-=-=-=-=-=-=-=-=-=\n");
        System.out.println("Choose one of the following: ");
        System.out.println("1:	 Start the game");
        System.out.println("2:\t See the word list");
        System.out.println("3:\t See the chosen words");
        System.out.println("4:\t Show Player guesses");
        System.out.println("zz to exit");
        System.out.println("=-=-=-=-=-=-=-=-=-=-=\n");
        System.out.println("What is your choice: ");

//  take input
        Scanner scnr = new Scanner(System.in);
        String user_input = scnr.nextLine();
        user_input = user_input.toString();
        user_input = user_input.trim();
        user_input = user_input.toLowerCase();

//  Hopefully this switch works, trying out a more elegant way than if else statements it seems simpler
        while (!user_input.equals("zz")) {
            switch (user_input) {
                case "1", "one" -> {
                    if (!pickWord()) {
                        showPlayerGuesses();
                    } else {
                        guess();
                    }
                }
                case "2", "two" -> showWordList();
                case "3", "three" -> showPlayedWords();
                case "4", "four" -> showPlayerGuesses();
                default -> System.out.println("I don't know what " + user_input + " is.");
            }break;
        }
        return play();
    }

    /**
     * This method uses a Random object to select a word at random from the wordList ArrayList and assign it to the global variable currentWord.
     * If the ArrayList playedWords already contains the value in currentWord and playedWords has the same number of values as wordList, display the message
     * "You've guessed them all!"
     * and return false
     * If playedWords already contains currentWord and playedWords and wordList are NOT the same size, call pickWord() again.
     * Add the current word to the playedWords ArrayList.
     * If DEBUG is true print the current word.
     * Return true
     * @return
     */
    public boolean pickWord() {
//  check if wordlist has values
        if(wordList.size() == 0){
            return false;}
//        generate rand num in range of wordlist
        Random rand = new Random();
        int num = rand.nextInt(wordList.size());
        this.setCurrentWord(wordList.get(num));
        Boolean value = null;

        if (playedWords.contains(currentWord) && playedWords.size() == wordList.size()) {
            System.out.println("you've guessed them all");
            value = false;
            return false;}
        if (playedWords.contains(currentWord) && playedWords.size() != wordList.size()) {
            pickWord();
            playedWords.add(currentWord);
            return false;}
            if (DEBUG) {
                System.out.println(currentWord);
                return true;}
        return value;
    }

    /**
     * This method has one local variable, an int named count.
     * This method checks each letter of wordGuess against each letter in currentWord to see if there is a match.
     * It must also check to see if there are duplicate letters in wordGuess.
     * If the letter is a duplicate then the letter should be ignored.
     * Similarly each letter in currentWord must only be counted once.
     * If there is a match, that is not a duplicate, increment count.
     * Return count.
     * @param wordGuess
     * @return
     */
    public int getLetterCount(String wordGuess) {
//   TODO: this caluclates the score so go back after this and fix the method where you made it caluclate the jotto score on its own (GuessesScores method)
        int count = 0;
        for (int i = 0; i < wordGuess.length(); i++){
            if (currentWord.contains(wordGuess.substring(i))){
                count++;}}
        return count;
    }

    private int score(){
        return this.score;
    }

    /**
     * Prints "Current word list:" and then displays all the words that the computer may select for the player to guess.
     * See below for proper formatting.  Do NOT just print the ArrayList.
     */
    private void showWordList(){
        System.out.println("Current word list ");
        for( int i = 0; i < wordList.size(); i++){
            System.out.println(wordList.get(i) + "\n");}
    }

    /**
     * This method will write the updated list of words to the file that is read at the beginning of the application.
     * This method must use try catch blocks to process any exceptions that may arise.
     * Open the file using a FileWriter object and the filename that is stored in filename.
     * Use a for loop to add all of the words from playerGuess to wordList. Do NOT include duplicates.
     * Write all of the words from wordList to the file that was read to get the initial word list.
     * Close the file.
     */
    private void updateWordList(){
        try {
            FileWriter fw = new FileWriter(getFilename());
            for(int i = 0; i < getPlayerGuesses().size(); i++){
                if(getWordList().contains(getPlayerGuesses().get(i))) {
                    wordList = wordList;}
                else{
                    wordList.add(playerGuesses.get(i));}
                fw.write(wordList.get(i));}
            fw.close();
        }
        catch (Exception e){
            System.out.println("Could not write to file");
        }
    }


    /**
     * Displays the words that have been chosen by the computer.
     * If no words have been played, display the message "No words have been played"
     * Otherwise display "Current list of played words:" and  list all the words from the list, one per line.
     * DO NOT just print the ArrayList.
     */
    private void showPlayedWords(){
        if(getPlayedWords().size() == 0){
            System.out.println("No words have been played");}
        else {
            System.out.println("Current list of played words \n");
            for( int i = 0; i < getPlayedWords().size(); i++){
                System.out.println(getPlayedWords().get(i) + "\n");}}
    }

    /**
     * This method prints all of the words the player has guessed in the current game.
     * Similar to showPlayedWords, this method will print "No guesses yet".
     * If there are words that the player has guessed, print "Current player guesses:" and display each guess on its own line.
     * DO NOT just print the ArrayList.
     * Once the words have been printed, ask the player if they would like to update the word list.  If so, call updateWordList() then call showWordList().
     */
    private void showPlayerGuesses(){
        Scanner scnr = new Scanner(System.in);

        if(getPlayerGuesses().size() == 0){
            System.out.println("NO words have been guessed");}
        else {
            System.out.println("Current player guesses \n");
            for(int i = 0; i < getPlayerGuesses().size(); i++){
                System.out.println(getPlayerGuesses() + " \n");}}

//        Check for updates to the word list and show the list
        System.out.println("Would you like to updated the word list? yes/no \n");
        String ans = scnr.nextLine();

        ans = ans.strip();
        ans = ans.trim();
        ans = ans.toLowerCase();

//  TODO: see  what to return if at all..
        if(ans.equals("yes")){
            updateWordList();
            showWordList();}
    }

    /**
     * If the ArrayList playerGuesses does not already contain wordGuess, add wordGuess to the playerGuesses ArrayList
     * @param wordGuess
     */
    private void addPlayerGuess(String wordGuess){
        if(!getPlayerGuesses().contains(wordGuess)){
            playerGuesses.add(wordGuess);
        }
    }

    /**
     * Display all of the words from the ArrayList parameter guesses and the Jotto score associated with the word.
     * @param guesses
     * @return
     */
    private void playerGuessScores(ArrayList<String> guesses){
        System.out.println("Players Guesses and jotto scores: \n");
        for(int i = 0; i < guesses.size(); i++){
            for( int j = 0; j < guesses.get(i).length(); j++){
                if(getCurrentWord().contains(guesses.get(i))){
                    setScore(getScore());}
                System.out.println(guesses.get(i) + ": " + getScore());
                setScore(0);}}
        }

    /**
     * This method is the main game loop where most of the processing is completed.
     *
     * This method has several local variables as follows:
     * ArrayList<String> currentGuesses = new ArrayList<>();
     * Used to store all the words entered by the user for the current round.
     * Scanner scan = new Scanner(System.in);
     * To read user input and store it in wordGuess
     * int letterCount = 0;
     * The count of letters that wordGuess has in common with the currentWord.
     * int score = WORD_SIZE + 1;
     * The score for the current round
     * String wordGuess
     * The word the user has guessed.
     *
     * The while loop will first display the current score for the round.  The score starts as WORD_SCORE plus one and is decremented
     * for each incorrect guess.  It is possible to earn a negative score.
     *
     * The user is then prompted for input as follows:
     * What is your guess (q to quit):
     *
     * User input is read in, trimmed of whitespace, and converted to lowercase for processing.
     * If the player enters 'q' their score is set to either 0 or their current score, whichever is lower.  Then the while loop is exited.
     *
     * If the user enters a value other than 'q' it is checked to ensure that the word is the correct length.
     * Compare the length of the entered value to WORD_SIZE to determine whether the word is valid.
     * If the word is NOT the length listed in WORD_SIZE display a message telling the user the word must be of the
     * correct length and return to the top of the loop without further processing.
     * The message to the user should state that the supplied word must be the correct length and show the length of the entered word.
     *
     * If a valid word has been entered, call addPlayerGuess() with the supplied word.
     * If the word the player has entered is correct, that is the word they are trying to guess, display a message
     * (see below for the correct message formatting), add the word to the ArrayList currentGuesses and call playerGuessScores(currentGuesses).
     * Return the current value for score.
     *
     * Check if the word that was entered was previously played, if so display a message stating that the word had
     * already been entered and return to the beginning of the while loop.  See the output below for the proper formatting.
     * Add the currently entered guess to the ArrayList currentGuesses.
     */
    public int guess(){
        ArrayList<String> currentGuesses = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        int letterCount = 0;
        int score = WORD_SIZE + 1;
        String wordGuess;

        while (!Objects.equals(scan.nextLine(), "q")){
            System.out.println("Your score: "+ score);
            System.out.println("what is your guess? ");
            wordGuess = scan.nextLine();
            wordGuess.trim();
            wordGuess.toLowerCase();

            if(wordGuess.equals("q")){
                if(getScore() > 0){
                    setScore(0);}
                else {setScore(score);}
                break;
            } else if(wordGuess.length() != WORD_SIZE) {
                System.out.println("word length is incorrect");
                score = score-1;
                guess();}
            else {
                addPlayerGuess(wordGuess);
                if(wordGuess.equals(getCurrentWord())){
                    System.out.println("DINGDINGDING!!! the word was " + getCurrentWord());
                    currentGuesses.add(wordGuess);
                    playerGuessScores(currentGuesses);
                    return score + 1;}
                if(getPlayedWords().contains(wordGuess)){
                    System.out.println("Word has already been guessed ");
                    guess();}}

            currentGuesses.add(wordGuess);
            letterCount = getLetterCount(wordGuess);

            if(letterCount != WORD_SIZE){
                System.out.println("Your guess " + wordGuess + "\n your score: " + score);}
            if(letterCount == WORD_SIZE){
                System.out.println("You chose an anagram. ");}

            playerGuessScores(currentGuesses);
        }
        System.out.println("Your score: ");
        return  score;}


    public static void main(String[] args) throws FileNotFoundException {


            Jotto test = new Jotto("/Users/connorbennett/Downloads/wordList.txt");
            System.out.println(test.play());


        }
}
