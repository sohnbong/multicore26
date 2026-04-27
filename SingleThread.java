import java.io.*;
import java.net.*;
import java.util.*;

public class SingleThread {

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Usage: java WebPageAnalyzerSingleThread <url_file>");
            return;
        }

        String fileName = args[0];
        List<String> urls = readUrls(fileName);

        int success = 0;
        int fail = 0;
        long totalTime = 0;
        int totalChars = 0;
        int totalJavaCount = 0;

        for (String url : urls) {
            PageResult r = analyzePage(url);
            printResult(r);

            if (r.success) {
                success++;
                totalTime += r.downloadTimeMillis;
                totalChars += r.characterCount;
                totalJavaCount += r.javaCount;
            } else {
                fail++;
            }
        }

        System.out.println("\n=== Summary ===");
        System.out.println("Success: " + success);
        System.out.println("Failed: " + fail);

        if (success > 0) {
            System.out.println("Avg Time: " + (totalTime / (double) success) + " ms");
        }

        System.out.println("Total Characters: " + totalChars);
        System.out.println("Total 'java' count: " + totalJavaCount);
    }

    static List<String> readUrls(String fileName) throws IOException {
        List<String> list = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));

        String line;
        while ((line = br.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                list.add(line.trim());
            }
        }

        br.close();
        return list;
    }

    static PageResult analyzePage(String urlStr) {
        PageResult result = new PageResult();
        result.url = urlStr;

        long start = System.currentTimeMillis();

        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);

            result.statusCode = conn.getResponseCode();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            String line;
            int charCount = 0;
            int lineCount = 0;
            int javaCount = 0;

            while ((line = reader.readLine()) != null) {
                lineCount++;
                charCount += line.length();
                javaCount += countOccurrences(line.toLowerCase(), "java");
            }

            reader.close();

            long end = System.currentTimeMillis();

            result.downloadTimeMillis = end - start;
            result.characterCount = charCount;
            result.lineCount = lineCount;
            result.javaCount = javaCount;
            result.success = true;

        } catch (Exception e) {
            result.success = false;
            result.errorMessage = e.getMessage();
        }

        return result;
    }

    static int countOccurrences(String text, String keyword) {
        int count = 0;
        int index = 0;

        while ((index = text.indexOf(keyword, index)) != -1) {
            count++;
            index += keyword.length();
        }

        return count;
    }

    static void printResult(PageResult r) {
        System.out.println("\nURL: " + r.url);

        if (!r.success) {
            System.out.println("FAILED: " + r.errorMessage);
            return;
        }

        System.out.println("Status: " + r.statusCode);
        System.out.println("Time: " + r.downloadTimeMillis + " ms");
        System.out.println("Characters: " + r.characterCount);
        System.out.println("Lines: " + r.lineCount);
        System.out.println("'java' count: " + r.javaCount);
    }
}

class PageResult {
    String url;
    int statusCode;
    long downloadTimeMillis;
    int characterCount;
    int lineCount;
    int javaCount;
    boolean success;
    String errorMessage;
}
