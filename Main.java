import java.io.*;
import java.util.*;

public class Main {

    public static Boolean checkWord(String word)
    {
        try{
            String words = "";
            File myFile = new File("/Users/macbook/Downloads/WordJumble_and_2of12inf/2of12inf.txt");
            Scanner scan = new Scanner(myFile);
            while(scan.hasNextLine())
            {
                words = scan.nextLine();
                if (words.equalsIgnoreCase(word))
                {
                    return true;
                }
            }
            scan.close();
        }
        catch (IOException ioex)
        {
            System.out.println("Error: " + ioex.getMessage());
        }
        return false;
    }

    public static void playGame (WordJumble wordJumble) {
        Scanner sc = new Scanner(System.in);

        String guess = "";

        while (!guess.equalsIgnoreCase("QUIT")) {


            System.out.println("Letters: " + wordJumble.getLetters());
            System.out.println("Found Words: " + wordJumble.getGuessedWordsAsString());
            System.out.println("Current Score: " + wordJumble.getScore());
            System.out.println("Enter a word using those letters or QUIT to stop, SAVE to save progress");
            guess = sc.nextLine();
            String guessLower = guess.toLowerCase();

            if (guess.equals("SAVE")) {
                saveGame(wordJumble.getLetters(), wordJumble.getGuessedWords());
                break;
            }


            boolean hasLetters = wordJumble.hasLetters(guessLower);


            //Checking if the word guessed can be made with the letters they were given
            if (!hasLetters) {
                System.out.println("You used letters you don't have");
            } else {
                //checking if the word guessed is a word in the dictionary
                boolean isValid = checkWord(guessLower);
                if (!isValid) {
                    System.out.println("Not a valid word");
                } else {
                    //checking if the word has already been guessed
                    boolean alreadyGuessed = wordJumble.alreadyGuessed(guessLower);
                    if (alreadyGuessed) {
                        System.out.println("Already Guessed");
                    } else {
                        int score = wordJumble.scoreWord(guessLower.length());
                        wordJumble.saveGuess(guessLower);
                        System.out.println("Valid Word. You scored " + score);

                    }
                }
            }
        }
    }

    public static void saveGame (String letters, ArrayList <String> guesses)
    {
        Scanner sc = new Scanner (System.in);
        System.out.println("Name your session");
        String name = sc.nextLine();
        FileWriter fileWriter = null;
        try{
            File myFile = new File ("/Users/macbook/Downloads/" + name);
            if (myFile.createNewFile())
            {
                System.out.println("File created: " + myFile.getName());
            }
            else {
                System.out.println("File already exists");
            }


                fileWriter = new FileWriter("/Users/macbook/Downloads/" + name);
                fileWriter.write(letters + "\n");

                for (String s : guesses) {
                    fileWriter.write(s + "\n");
                }
            fileWriter.close();

        }
        catch (IOException ioe)
        {
            System.out.println("Error: Unable to save state" );
        }
    }

    public static WordJumble restoreGame() {
        Scanner sc = new Scanner(System.in);

        System.out.println("What Game do you want to restore?");
        String name = sc.nextLine();
        ArrayList<String> guesses = new ArrayList<String>();
        String letters = "";
        try {
            File myFile = new File("/Users/macbook/Downloads/" + name);
            Scanner scan = new Scanner(myFile);
            letters = scan.nextLine();

            while (scan.hasNext()) {
                guesses.add(scan.nextLine());

            }

            scan.close();
        } catch (IOException ioe) {
            System.out.println("Error: " + ioe.getMessage());
        }
        return new WordJumble(letters, guesses);

    }

    public static void main (String [] args)
    {
        Scanner sc = new Scanner (System.in);
        System.out.println("Type NEW for a new game, or CONTINUE to restore an old game?");
        String game = sc.nextLine();
        if (game.equalsIgnoreCase("NEW"))
        {
            WordJumble wordJumble = new WordJumble();
            playGame(wordJumble);
        }
        else if (game.equalsIgnoreCase("CONTINUE"))
        {
           WordJumble restoreGame = restoreGame();
           playGame(restoreGame);
        }


    }
}


