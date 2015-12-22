import java.util.Scanner;
import java.util.Random;

public class Driver {

    public static final String[] posResponses = {"y", "yes", "1", "yup", "good", "check", "yeah", "y e s", "praise duarte", "hell yeah", "hell yes"};
    public static final String[] negResponses = {"n", "no", "0", "nada", "bad", "x", "never", "n o", "praise ive", "hell no"};
    public static final Champion[] charList = {new Marth("NULL")};
    public static final Stage[] stageList = {new Battlefield()};

    static String p1Name, p2Name;

    public static void main(String args[]){
        // Change to incorporate negative responses, and quit when given negative response
        makeNames();
        while (true){
            Champion p1 = clone(getCharacter(p1Name));
            Champion p2 = clone(getCharacter(p2Name));
            Stage st = getField();
            battle(p1, p2, st);
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
        println("Fatal choosing error. Defaulting to Marth.");
        Champion ch = new Marth(player);
        return ch;
    }

    public static Stage getField() {
        String input, response;
        boolean goodPick = false;
        Scanner sc = new Scanner(System.in);
        println("");
        while (!goodPick) {
            print("Stage list: ");
            for (Stage st : stageList) {
                print("\"" + st.getStageName() + "\" ");
            }
            println("");
            print("What stage would you like to pick?\n> ");
            input = sc.nextLine();

            int count = 0;
            // Test validity of player choice for a character
            for (Stage st : stageList) {
                if ((st.getStageName().toLowerCase().trim()).equals(input.toLowerCase().trim())) {
                    goodPick = true;
                    break;
                }
                ++count;
            }
            if (!goodPick) {
                println("Sorry, I don't recognize that stage.");
                println("");
            }


            // Only go here if the player chose a valid Champion
            if (goodPick) {
                print("Is \"" + input + "\" good?\n> ");
                response = sc.nextLine();
                response = response.toLowerCase().trim();
                for (String comparator : posResponses) {
                    if (response.equals(comparator)) {
                        Stage st = stageList[count];
                        return st;
                    }
                }
                goodPick = false;
            }
        }
        // Should never reach here
        // Stage becomes Battlefield if some impossible flaw in logic causes this to happen
        println("Fatal choosing error. Defaulting to Battlefield.");
        Stage st = new Battlefield();
        return st;
    }

    // Decides who goes first, then calls the function that begins the battle with players in the corresponding order
    public static void battle(Champion player1, Champion player2, Stage arena){
        Random rand = new Random();
        double coinFlip = rand.nextDouble();

        println("Today on " + arena.getStageName() + " we have \n" + player1.getCharName() + ": " + player1.getChampionName() +
        "\n\tvs.\n" + player2.getCharName() + ": " + player2.getChampionName());

        // Player 1 goes first
        if(coinFlip > .5){
            println(player1.getCharName() + "(Player 1) goes first!");
            battleBegin(player1, player2, arena);
        }
        // Player 2 goes first
        else{
            println(player2.getCharName() + "(Player 2) goes first!");
            battleBegin(player1, player2, arena);
        }
    }

    public static void battleBegin(Champion first, Champion second, Stage arena){
        Scanner sc = new Scanner(System.in);
        String input;
        while(!first.isKO() && !second.isKO()){
            println(first.getCharName() + ", what move do you want to make? a, g, s\n> ");
            input = sc.nextLine();
            // Do the array thing with choices tomorrow
            if (input.equals("a")){
                first.setActionFlag(0);
            }
            if (input.equals("s")){
                first.setActionFlag(1);
            }
            else{
                first.setActionFlag(2);
            }

            println(second.getCharName() + ", what move do you want to make? a, g, s\n> ");
            input = sc.nextLine();
            // Do the array thing with choices tomorrow
            if (input.equals("a")){
                second.setActionFlag(0);
            }
            if (input.equals("s")){
                second.setActionFlag(1);
            }
            else{
                second.setActionFlag(2);
            }

            // Print result of each attack
            // As a reminder, 0 = attack (rock), 1 = grab (scissors), 2 = shield (paper)

            // TODO: Get knockback for each, use flag to determine who was hit, see if opposing player dies
            int p1Action = first.getActionFlag(), p2Action = second.getActionFlag();
            if (p1Action == 0 && p2Action == 0 || p1Action == 1 && p2Action == 1 || p1Action == 2 && p2Action == 2){
                // print out draw
            }

            if (p2Action == p1Action + 1 || p2Action == p1Action - 2){
                first.attack(second);
            }else{
                second.attack(first);
            }

            // TODO: If players survive, print out percentages, do it at the top of the loop though, not here
        }

        // TODO: End battle, possible increment counter, champs may require extra data member to detemine player number
    }

    // Copies values from one champion into a temporary Champion to later be returned
    // Only necessary because Java doesn't support dereferencing
    // WHY DON'T YOU SUPPORT DEREFERENCING JAVA
    // SO MUCH DUPLICATE CODE
    // AAAAAAAAAAAAAAAAAAHHUFGHSDPGFUDSILFGBUP:GBPGFUI
    public static Champion clone(Champion ch){
        if (ch.getChampionName().toLowerCase() == "marth"){
            Champion tmpChamp = new Marth(ch.getCharName());
            return tmpChamp;
        }

        // Default to Marth if there is some impossible logic flaw that allows this to slip through the cracks
        Champion tmpChamp = new Marth(ch.getCharName());
        return tmpChamp;
    }

    // Makes printing easier and shorter
    public static void println(Object object){
        System.out.println(object);
    }
    public static void print(Object object) { System.out.print(object); }

}