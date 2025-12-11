import java.util.*;
import java.io.*;
import java.nio.file.*;

public class SortComparison {
static int cardCompare(String card1, String card2){
        String suit1 = card1.substring(card1.length()-1);
        int number1 = Integer.parseInt(card1.substring(0, card1.length()-1));

        String suit2 = card2.substring(card2.length()-1);
        int number2 = Integer.parseInt(card2.substring(0, card2.length()-1));

        int suitValue1 = 0;
        switch (suit1){
            case "H": suitValue1 = 1; break;
            case "C": suitValue1 = 2; break;
            case "D": suitValue1 = 3; break;
            case "S": suitValue1 = 4; break;
        }

        int suitValue2 = 0;
        switch (suit2){
            case "H": suitValue2 = 1; break;
            case "C": suitValue2 = 2; break;
            case "D": suitValue2 = 3; break;
            case "S": suitValue2 = 4; break;
        }

        if (suitValue1 < suitValue2) return -1;
        if (suitValue1 > suitValue2) return 1;
        if (number1 < number2) return -1;
        if (number1 > number2) return 1;
        return 0;
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
     if (array.size() <= 1) {
         return array;
     }
     int mid = array.size() / 2;
     ArrayList<String> left = new ArrayList<>(array.subList(0, mid));
     ArrayList<String> right = new ArrayList<>(array.subList(mid, array.size()));
     
     left = mergeSort(left);
     right = mergeSort(right);

     ArrayList<String> merged = new ArrayList<>();
        int i = 0, j = 0;
        while (i< left.size() && j < right.size()) {
            if (cardCompare(left.get(i), right.get(j)) <= 0) {
                merged.add(left.get(i));
                i++;
            } else {
                merged.add(right.get(j));
                j++;
            }
        } 
        while (i < left.size()) {
            merged.add(left.get(i));
            i++;
        }
        while (j < right.size()) {
            merged.add(right.get(j));
            j++;
        }
        return merged;
    }    
       static void sortComparison(String[] filenames) throws IOException{
       int[] sizes = new int[filenames.length];
       long[] bubbleTimes = new long[filenames.length];
       long[] mergeTimes = new long[filenames.length];  

       for (int i = 0; i < filenames.length; i++) {
           ArrayList<String> cards = new ArrayList<>(Files.readAllLines(Paths.get(filenames[i])));
           sizes[i] = cards.size();
           ArrayList<String> bubble= new ArrayList<>(cards);
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

       try (PrintWriter printwriter = new PrintWriter(new File("sortcomparison.csv"))) {
               printwriter.print(",");
               for (int i = 0; i < filenames.length; i++) {
                   printwriter.print(sizes[i]);
                     if (i < sizes.length - 1) printwriter.print(",");
                   }
                printwriter.println();

                printwriter.print("Bubble Sort,");
                for (int i = 0; i < bubbleTimes.length; i++) {
                    printwriter.print(bubbleTimes[i]);
                    if (i < bubbleTimes.length - 1) printwriter.print(",");
                }
                printwriter.println();

                printwriter.print("Merge Sort,");
                for (int i = 0; i < mergeTimes.length; i++) {
                    printwriter.print(mergeTimes[i]);
                    if (i < mergeTimes.length - 1) printwriter.print(",");
                }

               }

           }
    public static void main(String[] args) throws IOException {
    System.out.println("cardCompare Tests:");
    System.out.println("4H vs 4H: " + cardCompare("4H", "4H")); 
    System.out.println("4H vs 3H: " + cardCompare("4H", "3H")); 
    System.out.println("4H vs 7S: " + cardCompare("4H", "7S")); 
    System.out.println();



     
    ArrayList<String> testCards1 = new ArrayList<>(Arrays.asList("5H", "3S", "10D", "2C", "7D"));
    bubbleSort(testCards1);
    System.out.println("After bubbleSort: " + testCards1);
    System.out.println(); 

       ArrayList<String> testCards2 = new ArrayList<>(Arrays.asList("5H", "3S", "10D", "2C", "7D"));
    ArrayList<String> sortedCards = mergeSort(testCards2);
    System.out.println("After mergeSort: " + sortedCards);
    System.out.println(); 


    sortComparison(new String[]{"sort10.txt", "sort100.txt", "sort10000.txt"});
    System.out.println("CSV will be created in: " + System.getProperty("user.dir"));
}


}



        
           