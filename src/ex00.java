import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class ex00 {
    public static void main(String[] args) {
        String filePath = "signatures.txt";
        Map<Integer, String[]> hm = new HashMap<>();
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
                try {
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
                    FileWriter writer = new FileWriter("result.txt", true);
                    for (Map.Entry<Integer, String[]> entry : hm.entrySet()) {
                        String[] ab = entry.getValue();
                        if (result.contains(ab[1])) {
                            writer.write(ab[0] + "\n");
                            findFlag = true;
                        }
                    }
                    if(findFlag) System.out.println("PROCESSED");
                    else System.out.println("UNDEFINED");
                    writer.close();
                } catch (IOException e) {
                    System.out.println("UNDEFINED");
                }
            }
        } catch (IOException e) {
            System.out.println("Конфига нет");
        }
    }
}