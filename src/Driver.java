import java.util.Random;
import java.util.Scanner;

public class Driver {

    public static final String[] posResponses = {"y", "yes", "1", "yup", "good", "check", "yeah", "y e s", "praise duarte", "hell yeah", "hell yes"};
    public static final String[] negResponses = {"n", "no", "0", "nada", "bad", "x", "never", "n o", "praise ive", "hell no"};
    public static final String[] attackOps = {"attack", "a", "1"};
    public static final String[] grabOps = {"grab", "g", "2"};
    public static final String[] shieldOps = {"shield", "s", "3"};

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

            // Only go here if the player chose a valid Stage
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
            battleBegin(player2, player1, arena);
        }
    }

    public static void battleBegin(Champion first, Champion second, Stage arena){
        Scanner sc = new Scanner(System.in);
        String inputFirst, inputSecond;
        double knockBack;
        while(!first.isKO() && !second.isKO()){
            println(first.getCharName() + "\t:\t" + first.getChampionName() + "\t:\t%" + first.getPercentDmg());
            println(second.getCharName() + "\t:\t" + second.getChampionName() + "\t:\t%" + second.getPercentDmg());

            print(first.getCharName() + ", what move do you want to make? a, g, s\n> ");
            inputFirst = sc.nextLine();


            print(second.getCharName() + ", what move do you want to make? a, g, s\n> ");
            inputSecond = sc.nextLine();
            // Do the array thing with choices tomorrow

            first.setActionFlag(-1); second.setActionFlag(-1);
            for (String a : attackOps){
                if (inputFirst.toLowerCase().trim().equals(a)){
                    first.setActionFlag(0);
                }
                if (inputSecond.toLowerCase().trim().equals(a)){
                    second.setActionFlag(0);
                }
            }
            for (String g: grabOps){
                if (inputFirst.toLowerCase().trim().equals(g)){
                    first.setActionFlag(1);
                }
                if (inputSecond.toLowerCase().trim().equals(g)){
                    second.setActionFlag(1);
                }
            }
            for (String s: shieldOps){
                if (inputFirst.toLowerCase().trim().equals(s)){
                    first.setActionFlag(2);
                }
                if (inputSecond.toLowerCase().trim().equals(s)){
                    second.setActionFlag(2);
                }
            }

            if (first.getActionFlag() != -1 && second.getActionFlag() != -1){
                // Print result of each attack
                // As a reminder, 0 = attack (rock), 1 = grab (scissors), 2 = shield (paper)

                // TODO: Get knockback for each, use flag to determine who was hit, see if opposing player dies
                int p1Action = first.getActionFlag(), p2Action = second.getActionFlag();
                if (p1Action == 0 && p2Action == 0 || p1Action == 1 && p2Action == 1 || p1Action == 2 && p2Action == 2){
                    println("Same option chosen by " + first.getCharName() + " and " + second.getCharName() + "!\nNo damage taken.");
                }

                if (p2Action == p1Action + 1 || p2Action == p1Action - 2){
                    knockBack = first.attack(second);

                    // Is knockback horizontal?
                    if (first.getStats()[first.getActionFlag()][2] == 0){
                        knockBack = (second.getPercentDmg() * knockBack) * second.getGravity();

                        if (knockBack > arena.getHorizontalLen()){
                            second.toggleKO();
                            println(second.getCharName() + " has been KO'd!");
                        }
                    }

                    // Or is is vertical?
                    else{
                        knockBack = (second.getPercentDmg() * knockBack) / second.getGravity();

                        if (knockBack > arena.getVerticalLen()){
                            second.toggleKO();
                            println(second.getCharName() + " has been KO'd!");
                        }
                    }


                }else{
                    knockBack = second.attack(first);
                    // Is knockback horizontal
                    if (second.getStats()[second.getActionFlag()][2] == 0){
                        knockBack = (first.getPercentDmg() * knockBack) * first.getGravity();

                        if (knockBack > arena.getHorizontalLen()){
                            first.toggleKO();
                            println(first.getCharName() + " has been KO'd!");
                        }
                    }

                    // Or is is vertical?
                    else{
                        knockBack = (first.getPercentDmg() * knockBack) / first.getGravity();

                        if (knockBack > arena.getVerticalLen()){
                            first.toggleKO();
                            println(first.getCharName() + " has been KO'd!");
                        }
                    }

                }

                knockBack = 0;

            }

            // Invalid action entered by one of the two players
            else{
                String misMatch = first.getActionFlag() == -1 ? first.getCharName() : second.getCharName();
                println("Invalid action entered by " + misMatch + " please re-enter commands.");
            }
            // TODO: End battle, possible increment counter, champs may require extra data member to determine player number

        }


    }

    // Copies values from one champion into a temporary Champion to later be returned
    // Only necessary because Java doesn't support dereferencing
    // WHY DON'T YOU SUPPORT DEREFERENCING JAVA
    // SO MUCH DUPLICATE CODE
    // AAAAAAAAAAAAAAAAAAHHUFGHSDPGFUDSILFGBUP:GBPGFUI
    public static Champion clone(Champion ch){
        if (ch.getChampionName().toLowerCase().equals("marth")){
            Champion tmpChamp = new Marth(ch.getCharName());
            return tmpChamp;
        }

        // Default to Marth if there is some impossible logic flaw that allows this to slip through the cracks
        println("Impossible logic flaw. Defaulting to Marth.");
        Champion tmpChamp = new Marth(ch.getCharName());
        return tmpChamp;
    }

    // Makes printing easier and shorter
    public static void println(Object object){
        System.out.println(object);
    }
    public static void print(Object object) { System.out.print(object); }

}