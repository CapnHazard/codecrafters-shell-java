import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        boolean run = true;
        Scanner sc = new Scanner(System.in); 
        while(run == true) {
            System.out.print("$ ");
            String command = sc.nextLine();
            if(command.equals("exit")) {
                break;
            } else if(command != null) {
                System.out.print(command+": command not found\n");
            }
        }
        sc.close();
    }
}

/*

            if(command != "exit") {
                System.out.print(command+": command not found\n");
            } else if(command.equals("exit")) {
                break;
            }
*/