import java.io.IOException;
import java.util.*;
import java.io.File;

enum CHECK_TYPE {
    CHECK_DIR,
    NOT_CHECK_DIR
}

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
                        mvCommand(currentDir, str);
                        break;

                    case ("exit"):
                        scanner.close();
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
        currentDir = changePath(currentDir, path, CHECK_TYPE.CHECK_DIR);
        System.out.println(currentDir.getPath());
        return currentDir;
    }
//new File(currentDir.getPath() + "/" + str.split(" ")[1]);
    public static void mvCommand(File currentDir, String str) throws IOException {                  // исправить mv
        File moveFile = changePath(currentDir, str.split(" ")[1].split("/"), CHECK_TYPE.NOT_CHECK_DIR);
        File dirMove = changePath(currentDir, str.split(" ")[2].split("/"), CHECK_TYPE.NOT_CHECK_DIR);
        if(dirMove.isDirectory()) {
            dirMove = new File(dirMove.getPath() + "/" + str.split(" ")[1]);
        }
        boolean success = moveFile.renameTo(dirMove);
        if (success) {
            System.out.println("Файл успешно перемещен.");
        } else {
            System.out.println("Не удалось переместить файл.");
        }
    }

    public static File changePath(File currentDir, String[] path, CHECK_TYPE TYPE) throws IOException {
        for (String part : path) {

            if (!new File(currentDir.getPath() + "/" + part).isDirectory() && TYPE == CHECK_TYPE.CHECK_DIR) {
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
        return currentDir;
    }
}