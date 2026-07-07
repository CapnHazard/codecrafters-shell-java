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
            } else if(command != "exit" && !command.startsWith("echo")) {
                System.out.print(command+": command not found\n");
            }
            if(command.startsWith("echo")) {
                System.out.println(command.substring(4, command.length()).strip());
                
            }
        }
        sc.close();
    }
}

