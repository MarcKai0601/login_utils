import java.util.LinkedList;
import java.util.List;

public class test {

    public static List<String> commonChars(String[] words) {
        List<String> finish = new LinkedList<>();

        // 邊界情況
        if (words == null || words.length == 0 || words[0] == null) {
            return finish;
        }

        // 將後面每個字變成可變的 StringBuilder，好在找到一次後刪掉一次
        StringBuilder[] rest = new StringBuilder[words.length - 1];
        for (int i = 1; i < words.length; i++) {
            rest[i - 1] = new StringBuilder(words[i] == null ? "" : words[i]);
        }

        // 依序拿 words[0] 的每個字母去比對
        String word0 = words[0];
        for (int i = 0; i < word0.length(); i++) {
            char c = word0.charAt(i);
            boolean allFound = true;

            // 在每個後續單字中找一次並刪掉一次
            for (int j = 0; j < rest.length; j++) {
                int idx = rest[j].indexOf(String.valueOf(c));
                if (idx == -1) {        // 這個字母在某個字裡找不到
                    allFound = false;
                    break;
                }
                rest[j].deleteCharAt(idx); // 找到了就刪掉一次（處理重複次數）
            }

            if (allFound) {
                finish.add(String.valueOf(c));
            }
        }

        return finish;
    }

    public static void main(String[] args) {

        long start = System.nanoTime();

        String[] word = {"bella", "label", "roller"};

        long end = System.nanoTime();

        System.out.println(commonChars(word));
        System.out.println("執行時間：" + (end - start) / 1_000_000.0 + " ms");

    }
//test
}
