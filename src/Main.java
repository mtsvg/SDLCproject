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

        //initialize scanner on the substring
        Scanner theRaven = new Scanner(ravenTrimmed);

        //create a hash map to store the words mapped to the counts
        Map<String, Integer> words = new HashMap<>();

        //begin to scan each word of the poem
        while (theRaven.hasNext()){
            //creates a temporary word to store that is lowercase and removes any punctuation
            String word = theRaven.next().toLowerCase().replaceAll("[^a-zA-Z0-9]", "");

            //if the word does not exist in the hashmap, add it with the value count of 1
            if (words.get(word) == null){
                words.put(word, 1);

            //if word does exist it will be replaced by the same word with an updated count
            } else {
                //words.put(word, words.get(word) + 1);
                words.replace(word, words.get(word), words.get(word) + 1);
            }
        }

        //this iterates over the hashmap and uses it to populate the array of words with WordObjects

        ArrayList<WordObject> theWordList =  new ArrayList<WordObject>();

        for (Map.Entry<String, Integer> entry : words.entrySet()) {
            String k = entry.getKey();
            Integer v = entry.getValue();

            theWordList.add(new WordObject(k,v));
        }

    // sorts the arraylist by word count
        Collections.sort(theWordList, Comparator.comparingInt(WordObject::getCount).reversed());

        // print words and counts to console
        for (int i = 0; i < 20; i++){
            System.out.println(theWordList.get(i).word +" : "+theWordList.get(i).count);
        }
    }
}