import java.util.*;
import java.io.*;
import java.nio.file.*;

public class SortComparison {

    static int cardCompare(String card1, String card2){
        String suit1 = card1.substring(card1.length() - 1);
        int number1 = Integer.parseInt(card1.substring(0, card1.length() - 1));

        String suit2 = card2.substring(card2.length() - 1);
        int number2 = Integer.parseInt(card2.substring(0, card2.length() - 1));

        int suitValue1 = switch(suit1) {
            case "H" -> 1;
            case "C" -> 2;
            case "D" -> 3;
            case "S" -> 4;
            default -> 0;
        };

        int suitValue2 = switch(suit2) {
            case "H" -> 1;
            case "C" -> 2;
            case "D" -> 3;
            case "S" -> 4;
            default -> 0;
        };

        if (suitValue1 != suitValue2) return suitValue1 - suitValue2;
        return number1 - number2;
    }

    static ArrayList<String> bubbleSort(ArrayList<String> array){
        for (int i = 0; i < array.size() - 1; i++) {
            for (int j = 0; j < array.size() - 1 - i; j++) {
                if (cardCompare(array.get(j), array.get(j + 1)) > 0) {
                    String temp = array.get(j);
                    array.set(j, array.get(j + 1));
                    array.set(j + 1, temp);
                }
            }
        }
        return array;
    }

    static ArrayList<String> mergeSort(ArrayList<String> array){
        if (array.size() <= 1) return array;

        int mid = array.size() / 2;
        ArrayList<String> left = new ArrayList<>(array.subList(0, mid));
        ArrayList<String> right = new ArrayList<>(array.subList(mid, array.size()));

        left = mergeSort(left);
        right = mergeSort(right);

        ArrayList<String> merged = new ArrayList<>();
        int i = 0, j = 0;
        while (i < left.size() && j < right.size()) {
            if (cardCompare(left.get(i), right.get(j)) <= 0) {
                merged.add(left.get(i++));
            } else {
                merged.add(right.get(j++));
            }
        }
        while (i < left.size()) merged.add(left.get(i++));
        while (j < right.size()) merged.add(right.get(j++));

        return merged;
    }

    static void sortComparison(String[] filenames) throws IOException {
        int[] sizes = new int[filenames.length];
        long[] bubbleTimes = new long[filenames.length];
        long[] mergeTimes = new long[filenames.length];

        for (int i = 0; i < filenames.length; i++) {
            Path path = Paths.get("coursework2", filenames[i]);
            ArrayList<String> cards = new ArrayList<>(Files.readAllLines(path));
            sizes[i] = cards.size();

            ArrayList<String> bubble = new ArrayList<>(cards);
            long startBubble = System.currentTimeMillis();
            bubbleSort(bubble);
            long endBubble = System.currentTimeMillis();
            bubbleTimes[i] = endBubble - startBubble;

            ArrayList<String> merge = new ArrayList<>(cards);
            long startMerge = System.currentTimeMillis();
            mergeSort(merge);
            long endMerge = System.currentTimeMillis();
            mergeTimes[i] = endMerge - startMerge;
        }

        try (PrintWriter writer = new PrintWriter(new File("coursework2/sortComparison.csv"))) {
            writer.print(",");
            for (int i = 0; i < filenames.length; i++) {
                writer.print(filenames[i]);
                if (i < filenames.length - 1) writer.print(",");
            }
            writer.println();

            writer.print("Bubble Sort,");
            for (int i = 0; i < bubbleTimes.length; i++) {
                writer.print(bubbleTimes[i]);
                if (i < bubbleTimes.length - 1) writer.print(",");
            }
            writer.println();

            writer.print("Merge Sort,");
            for (int i = 0; i < mergeTimes.length; i++) {
                writer.print(mergeTimes[i]);
                if (i < mergeTimes.length - 1) writer.print(",");
            }
            writer.println();
        }
    }

    public static void main(String[] args) throws IOException {
        sortComparison(new String[]{"sort10.txt", "sort100.txt", "sort10000.txt"});
        System.out.println("CSV generated at: " + Paths.get("coursework2", "sortComparison.csv").toAbsolutePath());
    }
}


        
           