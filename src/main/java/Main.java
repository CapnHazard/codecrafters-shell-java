import java.io.File;
import java.util.*;
import java.nio.file.Path;


public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in); 
        String currentDirectory = System.getProperty("user.dir");

        while(true) {
            System.out.print("$ ");
            String input = sc.nextLine();
            String [] parts = input.split(" ", 2);
            String command = parts[0];
            String rest = parts.length > 1 ? parts[1].strip() : "";

            //vars for quoting implementation
            StringBuilder token = new StringBuilder();
            boolean singleQuotes = false;
            List <String> tokens = new ArrayList<>();
            
            for(char x : rest.toCharArray()) {
                if(x == '\'') {
                    singleQuotes = !singleQuotes; //turns true when inside '' and false when outside ''
                    continue; //to avoid printing single quote char itself
                } 
                if(x != ' ' || singleQuotes) {
                    token.append(x);
                } else {
                    if(token.length() > 0) {
                        tokens.add(token.toString());
                        token.setLength(0);
                    }
                }
            }
            if(token.length() > 0) {
                tokens.add(token.toString());
            }

            if(rest.startsWith("~")) {
                rest = System.getenv("HOME") + rest.substring(1);
            }
            if(command.equals("exit")) {
                break;
            } else if(command.equals("echo")) {
                System.out.println(String.join(" ", tokens));
            } else if(command.equals("type")) {
                if(rest.equals("echo")) {
                    System.out.println("echo is a shell builtin");
                } else if(rest.equals("exit")) {
                    System.out.println("exit is a shell builtin");
                } else if(rest.equals("type")) {
                    System.out.println("type is a shell builtin");
                } else if(rest.equals("pwd")) {
                    System.out.println("pwd is a shell builtin");
                } else if(rest.equals("cd")) {
                    System.out.println("cd is a shell builtin");
                } else {
                    String resolvedPath = findInPath(rest);
                    if(resolvedPath != null) {
                        System.out.println(rest + " is " + resolvedPath);
                    } else {
                        System.out.println(rest + ": not found");
                    }
                }
            } else if (command.equals("pwd")) {
                System.out.println(currentDirectory);
            } else if(command.equals("cd")) {
                if(rest.isEmpty()) {
                    currentDirectory = System.getenv("HOME");
                } else {
                    File dir = null;
                    if(new File(rest).isAbsolute()) {
                        dir = new File(rest);
                    } else {
                        dir = new File(currentDirectory, rest);
                    }
                    Path x = dir.toPath().normalize();
                    if(dir.isDirectory()) {
                        currentDirectory = x.toString();
                    } else {
                        System.out.println("cd: " + dir + ": No such file or directory" );
                    }
                }
            } else {
                String resolvedPath = findInPath(command);
                if(resolvedPath != null) {
                    runExternal(command, rest, resolvedPath);
                } else {
                    System.out.println(command + ": not found");
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
    static void runExternal(String command, String rest, String resolvedPath) throws Exception {
        List<String> commandParts = new ArrayList<>();
        commandParts.add(0, command);
        if(!rest.isEmpty()) {
            commandParts.addAll(Arrays.asList(rest.split(" ")));
        }

        ProcessBuilder pb = new ProcessBuilder(commandParts);
        pb.inheritIO();
        Process process = pb.start();
        process.waitFor();
    }
}
