import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;



public class ex01 {
    public static void main(String[] args) {
        String[][] texts = new String[args.length][];
        Set<String> hash_Set = parse(args, texts);

        int[][] textsCounts = countMatches(texts, hash_Set);

        System.out.println(hash_Set);
        System.out.println(Arrays.deepToString(textsCounts));

        int numer = Numerator(textsCounts);
        double n1 = Denominator(textsCounts[0]);
        double n2 = Denominator(textsCounts[1]);

        System.out.println(numer);
        System.out.println(n1 * n2);
        System.out.println(numer / (n1 * n2));
    }

    public static int[][] countMatches(String[][] texts, Set<String> hash_Set) {
        int[][] ret = new int[texts.length][];
        int sz = 0;
        for (int i = 0; i < texts.length; ++i) {
            int sizeC = 0;
            int[] count = new int[hash_Set.size()];
            for (String el : hash_Set) {
                for (int j = 0; j < texts[i].length; ++j) {
                    if (el.equals(texts[i][j])) ++sz;
                }
                count[sizeC++] = sz;
                sz = 0;
            }
            ret[i] = count;
        }
        return ret;
    }

    public static Set<String> parse(String[] args, String[][] texts) {
        Set<String> hash_Set = new HashSet<>();
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
        return hash_Set;
    }

    public static int Numerator(int[][] textsCounts) {
        int res = 0;
        for (int i = 0; i < textsCounts[0].length; ++i) {
            res += textsCounts[0][i] * textsCounts[1][i];
        }
        return res;
    }

    public static double Denominator(int[] textCounts) {
        double res = 0;
        for (int textCount : textCounts) {
            res += textCount * textCount;
        }
        return Math.sqrt(res);
    }
}