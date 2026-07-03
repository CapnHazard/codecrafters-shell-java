import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("$ ");
        String command = sc.nextLine();
        if(command != null) {
            System.out.print(command+": command not found");
        }
        sc.close();
    }
}
