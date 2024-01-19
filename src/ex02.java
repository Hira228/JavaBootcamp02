import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.io.File;

public class ex02 {

    public static void main(String[] args) {
        File currentDir = new File(args[0]);
        Scanner scanner = new Scanner(System.in);
        ChoiceCommands(scanner, currentDir);


    }

    public static void ChoiceCommands(Scanner scanner, File currentDir) {
        while (scanner.hasNextLine()) {
            String str = scanner.nextLine();
            try {
                switch (str.split(" ")[0]) {
                    case ("ls"):
                        lsCommand(currentDir);
                        break;

                    case ("cd"):
                        currentDir = cdCommand(currentDir, str);
                        break;

                    case ("mv"):
                        System.out.println("это mv");
                        break;

                    case ("exit"):
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Давай заново");
                        ChoiceCommands(scanner, currentDir);
                        break;
                }
            } catch (IOException error) {
                System.out.println(error.getMessage());
                ChoiceCommands(scanner, currentDir);
            }
        }
    }

    public static void lsCommand(File currentDir) {
        for (String file : Objects.requireNonNull(currentDir.list())) {
            File curFile = new File(currentDir + "/" + file);
            if (curFile.isDirectory()) {
                long sum = 0;
                File[] curFiles = curFile.listFiles();
                for (File f : Objects.requireNonNull(curFiles)) {
                    sum += f.length() / 1024;
                }
                System.out.println(file + " " + sum + " KB");
            } else
                System.out.println(file + " " + (new File(currentDir + "/" + file).length()) / 1024 + " KB");
        }
    }

    public static File cdCommand(File currentDir, String str) throws IOException {
        String[] path = str.split(" ")[1].split("/");
        for (String part : path) {

            if (!new File(currentDir.getPath() + "/" + part).isDirectory()) {
                throw new IOException("Это файл, а не директория");
            }

            switch (part) {
                    case (".."):
                        currentDir = new File(currentDir.getParent());
                        break;
                    default:
                        currentDir = new File(currentDir.getPath() + "/" + part);
                        break;
            }
        }
        System.out.println(currentDir.getPath());
        return currentDir;
    }
}