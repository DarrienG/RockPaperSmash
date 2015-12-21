import java.util.Scanner;

public class Driver {

    public static final String[] posResponses = {"y", "yes", "1", "yup", "good", "check", "yeah", "y e s", "praise duarte", "hell yeah", "hell yes"};
    public static final String[] negResponses = {"n", "no", "0", "nada", "bad", "x", "never", "n o", "praise ive", "hell no"};
    public static final Champion[] charList = {new Marth("null")};

    static String p1Name, p2Name;

    public static void main(String args[]){
        // Change to incorporate negative responses, and quit when given negative response
        makeNames();
        while (true){
            getCharacter(p1Name);
            getCharacter(p2Name);
        }
    }

    public static void makeNames(){
        p1Name = getName("Player 1");
        p2Name = getName("Player 2");
    }

    public static String getName(String player){
        String input, response;
        Scanner sc = new Scanner(System.in);
        println("");
        while (true){
            print(player + " please enter your tag: ");
            input = sc.nextLine();
            print("Is \"" + input + "\" good?\n> ");
            response = sc.nextLine();
            response = response.toLowerCase().trim();
            for (String comparator : posResponses){
                if (response.equals(comparator)){
                    return input;
                }
            }
        }
    }

    public static Champion getCharacter(String player){
        String input, response;
        boolean goodPick = false;
        Scanner sc = new Scanner(System.in);
        println("");
        while (!goodPick) {
            print("Character list: ");
            for (Champion ch : charList) {
                print("\"" + ch.getChampionName() + "\" ");
            }
            println("");
            print(player + " please choose your character: ");
            input = sc.nextLine();

            int count = 0;
            // Test validity of player choice for a character
            for (Champion ch : charList) {
                if ((ch.getChampionName().toLowerCase().trim()).equals(input.toLowerCase().trim())) {
                    goodPick = true;
                    break;
                }
                ++count;
            }
            if (!goodPick){
                println("Sorry, I don't recognize that character.");
                println("");
            }


            // Only go here if the player chose a valid Champion
            if (goodPick){
                print("Is \"" + input + "\" good?\n> ");
                response = sc.nextLine();
                response = response.toLowerCase().trim();
                for (String comparator : posResponses) {
                    if (response.equals(comparator)) {
                        Champion ch = charList[count];
                        ch.resetName(player);
                        return ch;
                    }
                }
                goodPick = false;
            }

        }

        // Should never reach here
        // Player becomes a Marth if some impossible flaw in logic causes this to happen
        Champion ch = new Marth(player);
        return ch;
    }

    public static void play(){

    }

    // Makes printing easier and shorter
    public static void println(Object object){
        System.out.println(object);
    }
    public static void print(Object object) { System.out.print(object); }

}