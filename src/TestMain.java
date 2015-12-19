public class TestMain{
    public static void main(String args[]){
        debugChar();
    }

    public static void println(Object object){
        System.out.println(object);
    }

    // For debugging characters based on stats


    public static void debugChar(){
    Champion ch = new Champion("test1.txt");
    for (int i = 0; i < ch.getFileNames().length; ++i){
        println(ch.getFileNames()[i]);
    }

    System.out.println("Atk stats");
    println(ch.getAtkDmg());
    println(ch.getAtkKB());
    println(ch.getAtkDir());

    println("Grab stats");
    println(ch.getGrabDmg());
    println(ch.getGrabKB());
    println(ch.getGrabDir());

    println("Shield stats");
    println(ch.getShieldDmg());
    println(ch.getShieldKB());
    println(ch.getShieldDir());

    println("Weight: " + ch.getWeight());
    }
}