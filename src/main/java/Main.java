import java.io.File;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in); 
        while(true) {
            System.out.print("$ ");
            String input = sc.nextLine();
            String [] parts = input.split(" ", 2);
            String command = parts[0];
            String rest = parts.length > 1 ? parts[1].strip() : "";

            if(command.equals("exit")) {
                break;
            } else if(command.equals("echo")) {
                System.out.println(rest);
            } else if(command.equals("type")) {
                if(rest.equals("echo")) {
                    System.out.println("echo is a shell builtin");
                } else if(rest.equals("exit")) {
                    System.out.println("exit is a shell builtin");
                } else if(rest.equals("type")) {
                    System.out.println("type is a shell builtin");
                } else {
                    System.out.println(rest + " is " + findInPath(rest));
                }
            } else {
                String resolvedPath = findInPath(rest);
                if(resolvedPath != null) {
                    System.out.println(resolvedPath);
                } else {
                    System.out.println(input + ": not found");
                }
            }
        }
        sc.close();
    }
    static String findInPath(String command) {
        String pathEnv = System.getenv("PATH");
        if(pathEnv == null) return null;

        String dirs [] = pathEnv.split(":");
        for(String x : dirs) {
            File candidate = new File(x, command);
            if(candidate.isFile() && candidate.canExecute()) {
                return candidate.getAbsolutePath();
            }
        }
        return null;
    }
    static void runExternal(String command, String rest) throws Exception {
        List<String> commandParts = new ArrayList<>();
        commandParts.add(command);
        if(!rest.isEmpty()) {
            commandParts.addAll(Arrays.asList(rest.split(" ")));
        }

        ProcessBuilder pb = new ProcessBuilder(commandParts);
        pb.inheritIO();
        Process process = pb.start();
        process.waitFor();
    }
}

