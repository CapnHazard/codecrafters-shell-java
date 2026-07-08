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
            } else if(command != "exit" && !command.startsWith("echo") && !command.startsWith("type")) {
                System.out.print(command+": command not found\n");
            }
            if(command.startsWith("echo")) {
                System.out.println(command.substring(4, command.length()).strip());
            }
            if(command.startsWith("type")) {
                if(command.startsWith("echo", 5)) {
                    System.out.println("echo is a shell built-in");
                } else if(command.startsWith("exit", 5)) {
                    System.out.println("exit is a shell built-in");
                } else if(command.startsWith("type")) {
                    System.out.println("type is a shell built-in");
                } else {
                    System.out.println("<" + command.substring(5) + ">: not found");
                }
            }
        }
        sc.close();
    }
}

