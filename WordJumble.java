import java.util.ArrayList;
import java.util.Random;

class WordJumble {
    Random myRand=new Random();
    private char[] vowels = new char[] {'a','e','i','o','u'};
    private char[] consonants= new char[] {'b','c','d','f','g','h','j','k','l','m','n','p','q','r','s','t','v','w','x','y','z'};

    private char[] letterArray=new char[16];

    private ArrayList<String> guessedWords = new ArrayList<String>();
    private int score=0;

    char[] pickLetters() {
        char[] retLetters=new char[16];
        for(int i=0;i<4;i++) {
            char randVowel=vowels[myRand.nextInt(5)];
            retLetters[i]=randVowel;
        }
        for(int i=4;i<16;i++) {
            char randVowel=consonants[myRand.nextInt(21)];
            retLetters[i]=randVowel;
        }
        return retLetters;
    }

    public WordJumble() {
        letterArray=pickLetters();
    }

    public WordJumble(String letters,ArrayList<String> guesses) {
        String[] charString=letters.split(" ",16);
        for(int i=0;i<16;i++) {
            letterArray[i]=charString[i].charAt(0);
        }
        for(String word : guesses) {
            scoreWord(word.length());
            guessedWords.add(word);
        }
    }

    @Override
    public String toString() {
        return getLetters();
    }

    public String getLetters() {
        String retString="";
        for(int i=0;i<16;i++) {
            retString+=letterArray[i]+" ";
        }
        return retString;
    }

    public boolean hasLetters(String word) {
        ArrayList<Integer> usedIndex = new ArrayList<Integer>();

        char[] letters=word.toCharArray();
        for(char nextLetter : letters) {
            boolean foundLetter=false;
            for(int i=0;i<letterArray.length;i++) {
                if((letterArray[i]==nextLetter) && (! usedIndex.contains(i))) {
                    usedIndex.add(i);
                    foundLetter=true;
                    break;
                }
            }
            if(foundLetter==false) {
                System.out.println("You don't have enough "+nextLetter+"'s to use.");
                return false;
            }
        }
        return true;
    }

    public int scoreWord(int size) {
        int wordScore=0;
        if(size<3) {
            wordScore=0;
        }
        else if(size<=4) {
            wordScore=1;
        }
        else if(size==5) {
            wordScore=2;
        }
        else if(size==6) {
            wordScore=3;
        }
        else if(size==7) {
            wordScore=5;
        }
        else {
            wordScore=11;
        }
        score+=wordScore;
        return wordScore;
    }

    public void saveGuess(String guess) {
        guessedWords.add(guess);
    }

    public boolean alreadyGuessed(String guess) {
        return guessedWords.contains(guess);
    }

    public String getGuessedWordsAsString() {
        String ret="";
        for(String x : guessedWords) {
            ret+=x+" ";
        }
        return ret;
    }

    public ArrayList<String> getGuessedWords() {
        return guessedWords;
    }

    public int getScore() {
        return score;
    }
}