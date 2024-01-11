import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class ex00 {
    public static void main(String[] args) {
        String filePath = "src/signal.txt";
        Map<Integer, String[]> hm = new HashMap<Integer, String[]>();
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            Scanner scanner = new Scanner(fileInputStream);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.replace(" ", "").split(",");
                hm.put(parts[1].length(), parts);
            }
            scanner = new Scanner(System.in);
            String filep;
            while(!(filep = scanner.nextLine()).equals("42")) {
                fileInputStream = new FileInputStream(filep);
                byte[] buffer = new byte[50];
                String result = "";
                int bytesRead = fileInputStream.read(buffer);
                if (bytesRead != 0x0A) {
                    for (int i = 0; i < bytesRead; i++) {
                        result += String.format("%02X", buffer[i] & 0xFF);
                    }
                }

                boolean findFlag = false;
                FileWriter writer = new FileWriter("src/result.txt", true);
                for (Map.Entry<Integer, String[]> entry : hm.entrySet()) {
                    String[] ab = entry.getValue();
                    if(result.contains(ab[1])) {
                        writer.write(ab[0]+ "\n");
                        writer.close();
                        findFlag = true;
                    }
                }
                if(!findFlag) writer.write("UNDEFINED");
                System.out.println("PROCESSED");
            }
        } catch (FileNotFoundException e) {
            System.err.println("Файл не найден: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }
}