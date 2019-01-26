package RockPaperSmash;

import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

/**
 * Main class. Everything is run here.
 */
public class Driver {

    /**
     * Positive responses that can be used to answer dialogs.
     */
    private static final String[] POS_RESPONSES = {"y", "yes", "1", "yup", "good", "check", "yeah", "y e s", "yee",
        "praise duarte", "hell yeah", "hell yes"};

    // /** Negative responses used to answer dialogs. Not implemented anywhere yet. */
    // public static final String[] negResponses = {"n", "no", "0", "nada", "bad", "x", "never", "n o", "praise ive",
    // "hell no"};

    /**
     * Options for choosing attack option.
     */
    private static final String[] ATTACK_OPS = {"attack", "a", "1"};

    /**
     * Options for choosing grab option.
     */
    private static final String[] GRAB_OPS = {"grab", "g", "2"};

    /**
     * Options for choosing shield option.
     */
    private static final String[] SHIELD_OPS = {"shield", "s", "3"};

    /**
     * Directories for all of the number files.
     */
    private static final FileResource[] NUM_LOC = {
        new FileResource("Assets/Numbers/Zero.txt"),
        new FileResource("Assets/Numbers/One.txt"),
        new FileResource("Assets/Numbers/Two.txt"),
        new FileResource("Assets/Numbers/Three.txt"),
        new FileResource("Assets/Numbers/Four.txt"),
        new FileResource("Assets/Numbers/Five.txt"),
        new FileResource("Assets/Numbers/Six.txt"),
        new FileResource("Assets/Numbers/Seven.txt"),
        new FileResource("Assets/Numbers/Eight.txt"),
        new FileResource("Assets/Numbers/Nine.txt"),
        new FileResource("Assets/Numbers/Mod.txt")
    };

    /**
     * Directories for all action text.
     */
    private static final FileResource[] ACTION_FILES = {
        new FileResource("Assets/Actions/Attack.txt"),
        new FileResource("Assets/Actions/Grab.txt"),
        new FileResource("Assets/Actions/Shield.txt"),
        new FileResource("Assets/Actions/Mid.txt")
    };

    /**
     * Directories for all titles.
     */
    private static final String[] TITLE_LOC = {"Assets/DisplayScreens/Title/Title1.txt",
        "Assets/DisplayScreens/Title/Title2.txt", "Assets/DisplayScreens/Title/Title3.txt"};

    /**
     * Copies of every character for easy selection. Likely to be replaced by strings later.
     */
    private static final Champion[] CHAR_LIST = {
        new Marth("NULL"),
        new Fox("NULL"),
        new Falco("NULL"),
        new CaptainFalcon("NULL")};

    /**
     * Copies of every stage, likely to be replaced later.
     */
    private static final Stage[] STAGE_LIST = {new Battlefield()};

    /**
     * Names of player 1 and player 2.
     */
    private static String mP1Name, mP2Name;

    public static void main(String args[]) {
        try {
            menu();
        } catch (Exception e) {
            println("Program prematurely killed. Cleaning up before exiting.");
            e.printStackTrace();
            cleanDir();
        }
    }

    private static final File TMP_DIR = FileReader.getTmpDir();

    /**
     * Has one player choose the {@link Champion} they would like to play.
     *
     * @param player Name of the player.
     * @return Champion.
     */
    public static Champion getCharacter(String player) {
        String input, response;
        boolean goodPick = false;
        Scanner sc = new Scanner(System.in);
        animate(new FileResource("Assets/DisplayScreens/CharSelect/Select.txt", true));

        while (!goodPick) {
            print("Character list: ");
            for (Champion ch : CHAR_LIST) {
                print("\"" + ch.getChampionName() + "\" ");
            }
            println();
            print(player + " please choose your character: ");
            input = sc.nextLine().toLowerCase().trim();

            int count = 0;
            // Test validity of player choice for a character
            for (Champion ch : CHAR_LIST) {
                if ((ch.getChampionName().toLowerCase().trim()).equals(input)) {
                    goodPick = true;
                    break;
                }
                ++count;
            }
            if (!goodPick) {
                println("Sorry, I don't recognize that character.");
                println();
            }

            // Only go here if the player chose a valid Champion
            if (goodPick) {
                animate(CHAR_LIST[count].getFileNames()[0]);
                print("Is \"" + input + "\" good?\n> ");
                response = sc.nextLine();
                response = response.toLowerCase().trim();
                for (String comparator : POS_RESPONSES) {
                    if (response.equals(comparator)) {
                        Champion ch = CHAR_LIST[count];
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
        return new Marth(player);
    }

    /**
     * Allows choice of a {@link Stage}.
     *
     * @return Chosen stage.
     */
    public static Stage getField() {
        String input, response;
        boolean goodPick = false;
        Scanner sc = new Scanner(System.in);
        println();
        while (!goodPick) {
            print("Stage list: ");
            for (Stage st : STAGE_LIST) {
                print("\"" + st.getStageName() + "\" ");
            }
            println();
            print("What stage would you like to pick?\n> ");
            input = sc.nextLine().toLowerCase().trim();

            int count = 0;
            // Test validity of player choice for a character
            for (Stage st : STAGE_LIST) {
                if ((st.getStageName().toLowerCase()).equals(input)) {
                    goodPick = true;
                    break;
                }
                ++count;
            }
            if (!goodPick) {
                println("Sorry, I don't recognize that stage.");
                println();
            }

            // Only go here if the player chose a valid Stage
            if (goodPick) {
                animate(new FileResource(STAGE_LIST[count].getFileLoc()));
                print("Is \"" + input + "\" good?\n> ");
                response = sc.nextLine();
                response = response.toLowerCase().trim();
                for (String comparator : POS_RESPONSES) {
                    if (response.equals(comparator)) {
                        return STAGE_LIST[count];
                    }
                }
                goodPick = false;
            }
        }
        // Should never reach here
        // Stage becomes Battlefield if some impossible flaw in logic causes this to happen
        println("Fatal choosing error. Defaulting to Battlefield.");
        return new Battlefield();
    }

    /**
     * Decides which player gets to go first based off of a coin flip, then begins the battle.
     *
     * @param player1 Player 1, the first {@link Champion} in the battle.
     * @param player2 Player 2, the second {@link Champion} in the battle.
     * @param arena   The chosen {@link Stage} where the players will be battling.
     */
    public static void battle(Champion player1, Champion player2, Stage arena) {
        Random rand = new Random();
        double coinFlip = rand.nextDouble();
        Scanner sc = new Scanner(System.in);

        clear();

        println("Today on " + arena.getStageName() + " we have \n" + player1.getCharName() + ": " + player1.getChampionName() +
            "\n\tvs.\n" + player2.getCharName() + ": " + player2.getChampionName());


        // Player 1 goes first
        if (coinFlip > .5) {
            println(player1.getCharName() + " (Player 1) goes first!");
            print("<ENTER> to begin");
            sc.nextLine();
            battleBegin(player1, player2, arena);
        }
        // Player 2 goes first
        else {
            println(player2.getCharName() + " (Player 2) goes first!");
            print("\n\n<ENTER> to begin ");
            sc.nextLine();
            battleBegin(player2, player1, arena);
        }
    }

    /**
     * Potential helper method. Can be called after after battle() for randomized start, or called on its own so
     * players get to pick who goes first.
     *
     * @param first  The first {@link Champion} in the battle.
     * @param second The second {@link Champion} in the battle.
     * @param arena  The chosen {@link Stage} where the players will be battling.
     */
    public static void battleBegin(Champion first, Champion second, Stage arena) {
        Scanner sc = new Scanner(System.in);
        String inputFirst, inputSecond;
        FileCat fc = new FileCat(first.getFileNames()[1], second.getFileNames()[2]);
        String partBattle = fc.LateralOp(TMP_DIR.getAbsolutePath() + "/PartBattleFile.txt");

        double knockBack;
        while (!first.isKO() && !second.isKO()) {
            animate(new FileResource(partBattle, false));
            formatNames(first, second);
            animateNoJump(new FileResource(percentageMaker(first, second), false));

            print(first.getCharName() + ", what move do you want to make? attack, grab, shield\n> ");
            inputFirst = sc.nextLine().toLowerCase().trim();

            clear();

            print(second.getCharName() + ", what move do you want to make? attack, grab, shield\n> ");
            inputSecond = sc.nextLine().toLowerCase().trim();
            // Do the array thing with choices tomorrow

            first.setActionFlag(-1);
            second.setActionFlag(-1);
            for (String a : ATTACK_OPS) {
                if (inputFirst.equals(a)) {
                    first.setActionFlag(0);
                }
                if (inputSecond.equals(a)) {
                    second.setActionFlag(0);
                }
            }
            for (String g : GRAB_OPS) {
                if (inputFirst.equals(g)) {
                    first.setActionFlag(1);
                }
                if (inputSecond.equals(g)) {
                    second.setActionFlag(1);
                }
            }
            for (String s : SHIELD_OPS) {
                if (inputFirst.equals(s)) {
                    first.setActionFlag(2);
                }
                if (inputSecond.equals(s)) {
                    second.setActionFlag(2);
                }
            }

            if (first.getActionFlag() != -1 && second.getActionFlag() != -1) {
                // Print result of each attack
                // As a reminder, 0 = attack (rock), 1 = grab (scissors), 2 = shield (paper)

                int p1Action = first.getActionFlag(), p2Action = second.getActionFlag();


                if (p1Action == 0 && p2Action == 0 || p1Action == 1 && p2Action == 1 || p1Action == 2 && p2Action == 2) {
                    println("Same option chosen by " + first.getCharName() + " and " + second.getCharName() + "!\nNo damage taken.");
                } else if (p2Action == p1Action + 1 || p2Action == p1Action - 2) {
                    double initDmg = second.getPercentDmg();
                    knockBack = first.attack(second);

                    if (first.isSpecial()) {
                        animate(first.getFileNames()[3]);
                        try {
                            Thread.sleep(1500);
                        } catch (java.lang.InterruptedException e) {
                            println("Caught " + e + "\nPlease be more careful next time.");
                        }
                    }

                    actionDisplay(first, second);

                    println(second.getCharName() + " takes: ");
                    singlePercDisplay(second.getPercentDmg() - initDmg);

                    // Is knockback horizontal?
                    if (first.getStats()[first.getActionFlag()][2] == 0) {
                        knockBack = (second.getPercentDmg() * knockBack) * second.getGravity();

                        if (knockBack > arena.getHorizontalLen()) {
                            second.toggleKO();
                            println(second.getCharName() + " has been KO'd!");
                        }
                    }

                    // Or is is vertical?
                    else {
                        knockBack = (second.getPercentDmg() * knockBack) / second.getGravity();

                        if (knockBack > arena.getVerticalLen() || knockBack > second.getRecovery()) {
                            second.toggleKO();
                            println(second.getCharName() + " has been KO'd!");
                        }
                    }

                } else {
                    double initDmg = first.getPercentDmg();
                    knockBack = second.attack(first);

                    if (second.isSpecial()) {
                        animate(second.getFileNames()[4]);
                        try {
                            Thread.sleep(1500);
                        } catch (java.lang.InterruptedException e) {
                            println("Caught " + e + "\nPlease be more careful next time.");
                        }
                    }

                    actionDisplay(first, second);

                    println(first.getCharName() + " takes :");
                    singlePercDisplay(first.getPercentDmg() - initDmg);

                    // Is knockback horizontal?
                    if (second.getStats()[second.getActionFlag()][2] == 0) {
                        knockBack = (first.getPercentDmg() * knockBack) * first.getGravity();

                        if (knockBack > arena.getHorizontalLen() || knockBack > first.getRecovery()) {
                            first.toggleKO();
                            println(first.getCharName() + " has been KO'd!");
                        }
                    }

                    // Or is is vertical?
                    else {
                        knockBack = (first.getPercentDmg() * knockBack) / first.getGravity();

                        if (knockBack > arena.getVerticalLen()) {
                            first.toggleKO();
                            println(first.getCharName() + " has been KO'd!");
                        }
                    }
                }
                println("<ENTER> to continue");
                sc.nextLine();
            }

            // Invalid action entered by one of the two players
            else {
                String misMatch = first.getActionFlag() == -1 ? first.getCharName() : second.getCharName();
                println("Invalid action entered by " + misMatch + " please re-enter commands.");
            }
            // TODO: End battle, possible increment counter, champs may require extra data member to determine player
            // number

        }
    }

    /**
     * Takes actions by two players, and outputs them to a unicode art version of the move.
     *
     * @param first  The first player.
     * @param second The second player.
     */
    public static void actionDisplay(Champion first, Champion second) {
        int action1 = first.getActionFlag(), action2 = second.getActionFlag();
        FileCat fc = new FileCat(ACTION_FILES[action1], ACTION_FILES[3]);
        String outFile = fc.LateralOp(TMP_DIR.getAbsolutePath() + "/tmpPerc.txt");

        FileResource resourceOutFile = new FileResource(outFile, false);
        fc = new FileCat(resourceOutFile, ACTION_FILES[action2]);
        outFile = fc.LateralOp(TMP_DIR.getAbsolutePath() + "/PercFin.txt");
        animateNoJump(new FileResource(outFile, false));
    }

    /**
     * Creates text art of damage of player, then outputs it to the screen in percentage form.
     *
     * @param inputDmg The total amount of damage received.
     */
    public static void singlePercDisplay(double inputDmg) {
        Stack<Integer> numStack = new Stack<>();
        String tmpLoc = TMP_DIR.getAbsolutePath() + "/";
        String fileOut;
        int dmg = (int) inputDmg;

        FileCat fc;

        if (dmg > 0) {
            while (dmg > 0) {
                numStack.push(dmg % 10);
                dmg /= 10;
            }

            // Leading zero on percentages less than 10
            if (numStack.size() == 1) {
                fc = new FileCat(NUM_LOC[0], NUM_LOC[numStack.pop()]);
                fileOut = fc.LateralOp(tmpLoc + "TmpDmg.txt");
                fc = new FileCat(new FileResource(fileOut, false), NUM_LOC[10]);
                fileOut = fc.LateralOp(tmpLoc + "UnspacedDmg.txt");
            }

            // Percentage greater than/equal to 9
            else {
                fc = new FileCat(NUM_LOC[numStack.pop()], NUM_LOC[numStack.pop()]);
                fileOut = fc.LateralOp(tmpLoc + "TmpDmg.txt");
                // Percentage greater than 100
                if (numStack.size() > 0) {
                    fc = new FileCat(new FileResource(fileOut, false), NUM_LOC[numStack.pop()]);
                    fileOut = fc.LateralOp(tmpLoc + "TmpDmg2.txt");
                }
                fc = new FileCat(new FileResource(fileOut, false), NUM_LOC[10]);
                fileOut = fc.LateralOp(tmpLoc + "FinDmg.txt");
            }
        } else {
            fc = new FileCat(NUM_LOC[0], NUM_LOC[0]);
            fileOut = fc.LateralOp(tmpLoc + "TmpDmg.txt");
            fc = new FileCat(new FileResource(fileOut, false), NUM_LOC[10]);
            fileOut = fc.LateralOp(tmpLoc + "FinDmg.txt");
        }

        /*fc = new FileCat(fileOut, NUM_LOC[9]);
        fileOut = fc.LateralOp(tmpLoc + "modPlus.txt");*/
        fc = new FileCat(new FileResource(fileOut, false), " ", 8);
        fileOut = fc.LateralOp(tmpLoc + "dmgPlus.txt");
        fc = new FileCat(new FileResource(fileOut, false), new FileResource("Assets/Extras/Damage.txt"));
        animateNoJump(new FileResource(fc.LateralOp(tmpLoc + "outDmg.txt"), false));
    }

    /**
     * Helper method, copies value from one Champion into a temporary Champion to be later returned.
     *
     * @param ch Champion chosen.
     * @return Clean version of Champion chosen.
     */
    public static Champion clone(Champion ch) {
        String selected = ch.getChampionName().toLowerCase();
        if (selected.equals("marth")) {
            return new Marth(ch.getCharName());
        } else if (selected.equals("fox")) {
            return new Fox(ch.getCharName());
        } else if (selected.equals("falco")) {
            return new Falco(ch.getCharName());
        } else if (selected.equals("captain falcon")) {
            return new CaptainFalcon(ch.getCharName());
        }

        // Default to Marth if there is some impossible logic flaw that allows this to slip through the cracks
        println("Impossible logic flaw. Defaulting to Marth.");
        return new Marth(ch.getCharName());
    }

    /**
     * Takes a file name, and outputs the result to the screen, after clearing the screen.
     *
     * @param fileName Name of file to be opened and output.
     */
    public static void animate(FileResource file) {
        String line;
        // The length of my terminal in lines, may or may not correspond to your terminal size
        clear();
        try {
            BufferedReader br = FileReader.loadFile(file);
            while ((line = br.readLine()) != null) {
                println(line);
            }
        } catch (java.io.IOException e) {
            println("Info page not found.");
            e.printStackTrace();
        }
    }

    /**
     * Takes a file name, and outputs the results to the screen.
     *
     * @param fileName Name of file to be opened and output.
     */
    public static void animateNoJump(FileResource file) {
        String line;
        try {
            BufferedReader br = FileReader.loadFile(file);
            while ((line = br.readLine()) != null) {
                println(line);
            }
        } catch (java.io.IOException e) {
            println("Info page not found.");
        }
    }

    // Creates ASCII output of percentages, damage capped at 999%

    /**
     * Creates ASCII art of player percentages, and places them next to each other. Returns newly created file name.
     * Damage display is capped at 999%.
     *
     * @param first  Player 1.
     * @param second Player 2.
     * @return File name of created file.
     */
    public static String percentageMaker(Champion first, Champion second) {
        Stack<Integer> numStack = new Stack<>();
        int firstPercent = (int) first.getPercentDmg();
        int secondPercent = (int) second.getPercentDmg();
        int spacer = first.getSpacer();
        String tmpLoc = TMP_DIR.getAbsolutePath() + "/";
        String firstLoc, secondLoc;
        boolean hundredPlusFlag = false;

        FileCat fc;

        if (firstPercent > 0) {
            while (firstPercent > 0) {
                numStack.push(firstPercent % 10);
                firstPercent /= 10;
            }

            // Leading zero on percentages less than 10
            if (numStack.size() == 1) {
                fc = new FileCat(NUM_LOC[0], NUM_LOC[numStack.pop()]);
                firstLoc = fc.LateralOp(tmpLoc + "tmpFirstPercent.txt");
                fc = new FileCat(new FileResource(firstLoc, false), NUM_LOC[10]);
                firstLoc = fc.LateralOp(tmpLoc + "unspacedFirstPercent.txt");
            }

            // Percentage greater than/equal to 9
            else {
                fc = new FileCat(NUM_LOC[numStack.pop()], NUM_LOC[numStack.pop()]);
                firstLoc = fc.LateralOp(tmpLoc + "tmpFirstPercent.txt");
                // Percentage greater than 100
                if (numStack.size() > 0) {
                    fc = new FileCat(new FileResource(firstLoc, false), NUM_LOC[numStack.pop()]);
                    firstLoc = fc.LateralOp(tmpLoc + "tmpFirstPercent2.txt");
                    hundredPlusFlag = true;
                }
                fc = new FileCat(new FileResource(firstLoc, false), NUM_LOC[10]);
                firstLoc = fc.LateralOp(tmpLoc + "unspacedFirstPercent.txt");
            }
        } else {
            fc = new FileCat(NUM_LOC[0], NUM_LOC[0]);
            firstLoc = fc.LateralOp(tmpLoc + "tmpFirstPercent.txt");
            fc = new FileCat(new FileResource(firstLoc, false), NUM_LOC[10]);
            firstLoc = fc.LateralOp(tmpLoc + "unspacedFirstPercent.txt");
        }

        spacer = hundredPlusFlag ? spacer - 8 : spacer;
        fc = new FileCat(new FileResource(firstLoc, false), " ", spacer);
        firstLoc = fc.LateralOp(tmpLoc + "firstPercent.txt");

        // Second player's percentage
        if (secondPercent > 0) {
            while (secondPercent > 0) {
                numStack.push(secondPercent % 10);
                secondPercent /= 10;
            }

            // Leading zero on percentages less than 10
            if (numStack.size() == 1) {
                fc = new FileCat(NUM_LOC[0], NUM_LOC[numStack.pop()]);
                secondLoc = fc.LateralOp(tmpLoc + "tmpSecondPercent.txt");
                fc = new FileCat(new FileResource(secondLoc, false), NUM_LOC[10]);
                secondLoc = fc.LateralOp(tmpLoc + "secondPercent.txt");
            }

            // Percentage greater than/equal to 9
            else {
                fc = new FileCat(NUM_LOC[numStack.pop()], NUM_LOC[numStack.pop()]);
                secondLoc = fc.LateralOp(tmpLoc + "tmpSecondPercent.txt");
                // Percentage greater than 100
                if (numStack.size() > 0) {
                    fc = new FileCat(new FileResource(secondLoc, false), NUM_LOC[numStack.pop()]);
                    secondLoc = fc.LateralOp(tmpLoc + "tmpSecondPercent2.txt");
                }
                fc = new FileCat(new FileResource(secondLoc, false), NUM_LOC[10]);
                secondLoc = fc.LateralOp(tmpLoc + "secondPercent.txt");
            }
        } else {
            fc = new FileCat(NUM_LOC[0], NUM_LOC[0]);
            secondLoc = fc.LateralOp(tmpLoc + "tmpSecondPercent.txt");
            fc = new FileCat(new FileResource(secondLoc, false), NUM_LOC[10]);
            secondLoc = fc.LateralOp(tmpLoc + "secondPercent.txt");
        }
        fc = new FileCat(new FileResource(firstLoc, false), new FileResource(secondLoc, false));
        return fc.LateralOp(tmpLoc + "finalPercents.txt");
    }

    /**
     * Formats spacing of player names, so they are aligned correctly regardless of character choice.
     *
     * @param first  Player 1.
     * @param second Player 2.
     */
    public static void formatNames(Champion first, Champion second) {
        int trueSpacer = first.getSpacer() + 25 - first.getCharName().length();
        print(first.getCharName());
        if (trueSpacer < 1) {
            trueSpacer = 1;
        }
        for (int i = 0; i < trueSpacer; ++i) {
            print(" ");
        }
        println(second.getCharName());
    }

    // Removes temporary battle files from tmpBattleFiles folder

    /**
     * Removes temporary battle files from tmpBattleFiles folder.
     */
    public static void cleanDir() {
        for (File file : TMP_DIR.listFiles()) {
            file.delete();
        }
    }

    /**
     * Reads intro files, and prints them to the screen with a slight delay in between prints.
     */
    public static void displayTitle() {
        Scanner sc = new Scanner(System.in);
        clear();
        for (String titleScreen : TITLE_LOC) {
            animateNoJump(new FileResource(titleScreen));
            try {
                Thread.sleep(500);
            } catch (java.lang.InterruptedException e) {
                println("Program killed prematurely." + e);
            }
        }

        println("Press Start to begin ");
        sc.nextLine();
    }

    /**
     * Displays the main menu.
     */
    public static void menu() {
        Scanner sc = new Scanner(System.in);
        String input;
        displayTitle();
        int choice = -1;

        while (true) {
            clear();
            println("~~~~~~~~~~~~~~~~~~");
            println("Main menu");
            println("1. Options");
            println("2. Player vs. Player");
            println("3. Show Characters");
            print("4. Quit\n> ");
            // Add i"n when implemented
            // println("3. Player vs. CPU");

            input = sc.nextLine().trim().toLowerCase();

            switch (input) {
                case "options":
                case "1":
                case "1.":
                    choice = 1;
                    options();
                    break;
                case "2":
                case "2.":
                case "pvp":
                case "player vs. player":
                case "player vs player":
                    choice = 2;
                    pvp();
                    break;
                case "3.":
                case "3":
                    showChars();
                    break;
                case "4":
                case "4.":
                case "quit":
                case "q":
                    return;

                default:
                    println("I didn't recognize that input. Please enter input again.\n");
                    break;
            }

            // Does not seem to work right now, will work on fixing later.
            /*if (choice == 2) {
                print("Salty runback?\n> ");
                input = sc.nextLine().toLowerCase().trim();
                for (String s: POS_RESPONSES) {
                    if (s.equals(input)) {
                        battle(p1, p2, st);
                    }
                }
            }*/
        }
    }

    public static void showChars() {
        final Scanner sc = new Scanner(System.in);
        Champion c;
        for (int i = 0; i < CHAR_LIST.length; ++i) {
            c = CHAR_LIST[i];
            animate(c.getFileNames()[0]);
            print(i + 1 + "/" + CHAR_LIST.length + " Enter for next, q to quit\n> ");
            String input = sc.nextLine();
            if (input.equals("q")) {
                return;
            }
        }
    }

    /**
     * Displays the options menu.
     */
    public static void options() {
        Scanner sc = new Scanner(System.in);
        animate(new FileResource("Assets/DisplayScreens/Options/Options.txt", true));
        println("Ladies and gentlemen, this is an empty options menu.");
        println("I'll fill it with good stuff later.");
        println("<ENTER> to return to the main menu");
        sc.nextLine();
    }

    /**
     * Begins a player vs. player battle.
     */
    public static void pvp() {
        println(TMP_DIR.getAbsolutePath());
        makeNames();
        Champion p1 = clone(getCharacter(mP1Name));
        clear();
        Champion p2 = clone(getCharacter(mP2Name));
        clear();
        Stage st = getField();
        battle(p1, p2, st);
        cleanDir();
    }


    /**
     * Helper method, gets the tags of two players by calling getName twice
     */
    private static void makeNames() {
        animate(new FileResource("Assets/DisplayScreens/TagSelect/TagS.txt", true));
        mP1Name = getName("Player 1");
        mP2Name = getName("Player 2");
    }

    /**
     * Helper method, gets player tag, and returns it.
     *
     * @param player Player number choosing their tag.
     * @return The returned tag.
     */
    private static String getName(String player) {
        String input, response;
        Scanner sc = new Scanner(System.in);
        println();
        while (true) {
            print(player + " please enter your tag: ");
            input = sc.nextLine();
            print("Is \"" + input + "\" good?\n> ");
            response = sc.nextLine();
            response = response.toLowerCase().trim();
            for (String comparator : POS_RESPONSES) {
                if (response.equals(comparator)) {
                    return input;
                }
            }
        }
    }

    /**
     * Helper method, clears what is currently on the screen by printing new lines.
     */
    private static void clear() {
        for (int i = 0; i < 125; ++i) {
            println();
        }
    }

    /**
     * Helper method, allows for faster System.out.println().
     *
     * @param object Item to be printed.
     */
    private static void println(Object object) {
        System.out.println(object);
    }

    /**
     * Helper method, allows for newline to be printed quickly.
     */
    private static void println() {
        System.out.println();
    }

    /**
     * Helper method, allows for faster System.out.print().
     *
     * @param object Item to be printed.
     */
    private static void print(Object object) {
        System.out.print(object);
    }
}
