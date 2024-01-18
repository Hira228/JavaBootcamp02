import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;


public class ex00 {
    public static void main(String[] args) {
        Set<String> hash_Set = new HashSet<>();
        String[][] texts = new String[2][];

        for(int i = 0; i < args.length; ++i) {
            try {
                FileInputStream fileInputStream = new FileInputStream("src/"+ args[i]);
                Scanner fileScanner = new Scanner(fileInputStream);
                List<String> wordsList = new ArrayList<>();
                while(fileScanner.hasNext()) {
                    String[] words = fileScanner.nextLine().split(" ");
                    hash_Set.addAll(Arrays.asList(words));
                    wordsList.addAll(Arrays.asList(words));
                }
                texts[i] = wordsList.toArray(new String[0]);
            } catch (IOException e) {
                System.out.println("пиздец");
            }
        }
        System.out.println(hash_Set);

        int []A = new int[hash_Set.size()];
        int szA = 0;
        int countA = 0;
        int countB = 0;
        int szB = 0;
        int []B = new int[hash_Set.size()];

        for(String el : hash_Set) {
            for(int i = 0; i < texts[0].length; ++i) {      // A
                if(el.equals(texts[0][i])) ++szA;
            }
            A[countA++] = szA;
            szA = 0;
        }

        for(String el : hash_Set) {
            for(int i = 0; i < texts[1].length; ++i) {      // B
                if(el.equals(texts[1][i])) ++szB;
            }
            B[countB++] = szB;
            szB = 0;
        }

        System.out.println(Arrays.toString(A));
        System.out.println(Arrays.toString(B));
    }
}