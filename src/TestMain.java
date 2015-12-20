import java.util.Scanner;

public class TestMain{

    public static final String[] posResponses = {"y", "yes", "1", "yup", "good", "check", "y e s", "praise duarte", "hell yeah", "hell yes"};

    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        String p1Name, p2Name;

        while (true){
            p1Name = getName("Player 1");
            p2Name = getName("Player 2");
            println(p1Name);
            println(p2Name);
        }
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
            response = response.toLowerCase();
            for (String comparator : posResponses){
                if (response.equals(comparator)){
                    return input;
                }
            }
        }
    }


    // Makes printing easier and shorter
    public static void println(Object object){
        System.out.println(object);
    }
    public static void print(Object object) { System.out.print(object); }

}