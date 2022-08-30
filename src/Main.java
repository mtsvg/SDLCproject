import org.jsoup.Jsoup;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {

        //import the file from my local hard drive
        File file = new File("/Users/matthew/Documents/theraven.html");
        // use jsoup to remove html tags
        String ravenNoHMTL = Jsoup.parse(file).text();

        //locates the beginning and ending phrase of the poem to create
        //a substring containing only the poem
        int leadingIndex = ravenNoHMTL.indexOf("The Raven by Edgar Allan Poe");
        int followingIndex = ravenNoHMTL.indexOf("*** END OF THE PROJECT GUTENBERG EBOOK THE RAVEN ***");
        String ravenTrimmed = ravenNoHMTL.substring(leadingIndex, followingIndex);

        //initialize scanned on the substring
        Scanner theRaven = new Scanner(ravenTrimmed);

        //create a hash map to store the words mapped to the counts
        Map<String, Integer> words = new HashMap<>();

        //treemap is used to create an ordered map, and then a descending map that is populated by the hashmap
        TreeMap<Integer, String> wordsSorted = new TreeMap<>();
        Map<Integer, String> reverse = wordsSorted.descendingMap();

        //begin to scan each word of the poem
        while (theRaven.hasNext()){
            //creates a temporary word to store that is lowercase and removes any punctuation
            String word = theRaven.next().toLowerCase().replaceAll("\\p{Punct}" , "");

            //if the word does not exist in the hashmap, add it with the value count of 1
            if (words.get(word) == null){
                words.put(word, 1);

            //if word does exist it will be replaced by the same word with an updated count
            } else {
                //words.put(word, words.get(word) + 1);
                words.replace(word, words.get(word), words.get(word) + 1);
            }
        }

        //this iterates over the hashmap and uses it to populate the tree map for sorting
        words.forEach((k, v)
                -> wordsSorted.put(v, k));

        //converts the tree map into a set so that the top entries can be accessed by index
        Set<Map.Entry<Integer, String> > entrySet = reverse.entrySet();
        Map.Entry<Integer, String>[] entryArray = entrySet.toArray(new Map.Entry[entrySet.size()]);

        //display the top 10 key value pairs
        for (int i = 0; i < 10; i++){
            System.out.println(entryArray[i].getValue() + " : " + entryArray[i].getKey());
        }
    }
}