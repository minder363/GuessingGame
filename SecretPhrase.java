import javax.swing.JOptionPane;
import java.lang.StringBuilder;
import java.util.Scanner;
import java.io.File;

public class SecretPhrase {

    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to Guessing Game!\n The computer will randomly selects a phrase from text file.\n You have to guess the phrase.\n Best of luck!");
        mainCopy();
    }

    public static void mainCopy() throws Exception {
        final String[] SCORES = new String[5];
        for (int i = 0; i < 5; ++i) {// plays game for 5 rounds
            File file = new File("Phrases.txt");
            Scanner scan = new Scanner(file);
            int random = (int) (Math.random() * (lineCount(file, scan) + 1));// random number between 0 and length of
                                                                             // phrases array //
            String outAns = getAnswer(random);
            String stars = asterisk(outAns);// gets string with letters replaced by *'s
            SCORES[i] = roundPlay(stars, outAns);
        }
        printFinal(SCORES);
    }

    public static String roundPlay(String stars, String outAns) {// method plays one round of game
        double score = 0;
        double count = 0;
        String answer = outAns.toUpperCase();
        while (true) {// infinite loop until answer is shown
            String input = JOptionPane.showInputDialog(null,
                    "Play our game - guess the phrase \nEnter one letter\n" + stars);
            count += 1;
            for (int i = 0; i < input.length(); ++i) {
                Character letter = input.toUpperCase().charAt(i);
                if (occurence(answer, letter) == 0) {// if letter input not presents in answer
                    input = JOptionPane.showInputDialog(null,
                            "Sorry - not in the phrase:" + letter + "\n" + "\nPress any letter to continue");
                }
                stars = stringReplace(answer, stars, letter, occurence(answer, letter));// stars replaced by letters
            }

            if (stars.equals(answer) == true) {
                score = answer.length() / count;
                JOptionPane.showMessageDialog(null,
                        "Congratulations!\nThe phrase is \"" + outAns + "\"\nYour Score is " + score);
                break;
            }
        }
        return score + "-" + outAns;
    }

    public static void printFinal(String[] scores) {// this method prints final scores of 5 rounds and average
        double score = 0;
        System.out.println("Round  Target Phrase                          Score");
        for (int i = 0; i < 5; ++i) {
            int index = scores[i].indexOf("-");
            System.out.printf(" %-5s %-36s %5.2f \n", (i + 1), scores[i].substring(index + 1),
                    Double.parseDouble(scores[i].substring(0, index)));
            score += Double.parseDouble(scores[i].substring(0, index));
        }
        System.out.printf("The average score is %.2f", score / 5);
    }

    public static int lineCount(File file, Scanner scan) throws Exception {// this method finds the file and count
                                                                           // number lines
        int lines = 0;
        while (scan.hasNextLine()) {
            lines += 1;
            scan.nextLine();
        }
        return lines;
    }

    public static String getAnswer(int num) throws Exception {// this method randomly picks up line from file for guess
                                                              // game
        File file = new File("Phrases.txt");
        Scanner scan = new Scanner(file);
        String str = "";
        int x = 0;
        while (true) {
            str = scan.nextLine();
            if (x == num) {
                break;
            }
            x++;
        }
        scan.close();
        return str;
    }

    public static String asterisk(String answer) {// this method returns string with all *'s in the places of letters
        String guess = "";
        for (int i = 0; i < answer.length(); ++i) {
            if ((answer.charAt(i) >= 'A' && answer.charAt(i) <= 'Z')
                    || (answer.charAt(i) >= 'a' && answer.charAt(i) <= 'z')) {
                guess += "*";
            } else {
                guess += answer.charAt(i);
            }
        }
        return guess;
    }

    public static int occurence(String answer, Character input) {// this method checks and returns how many occurences
                                                                 // of a letter in the answer
        int count = 0;
        for (int i = 0; i < answer.length(); ++i) {
            if (answer.charAt(i) == input) {
                count++;
            }
        }
        return count;
    }

    // stringReplace method replaces letters with stars for each correct guess and
    // returns the string
    public static String stringReplace(String answer, String guess, Character input, int occurence) {
        int next = 0;
        String output = guess;
        int index;
        StringBuilder x = new StringBuilder(output);
        for (int i = 0; i < occurence; ++i) {
            index = answer.indexOf(input);
            x.setCharAt(index + next, input);
            answer = answer.substring(index + 1);
            next += index + 1;
        }
        return x.toString();
    }
}
