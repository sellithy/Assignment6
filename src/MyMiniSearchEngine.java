import java.util.*;
import java.util.regex.Pattern;

public class MyMiniSearchEngine {
    private final static Pattern WORDS = Pattern.compile("[^\\p{Alnum}]");
    // default solution. OK to change.
    // do not change the signature of index()
    private Map<String, List<List<Integer>>> indexes;

    // disable default constructor
    private MyMiniSearchEngine() {
    }

    public MyMiniSearchEngine(List<String> documents) {
        indexes = new HashMap<>();
        index(documents);
    }

    // each item in the List is considered a document.
    // assume documents only contain alphabetical words separated by
    // white spaces.
    private void index(List<String> texts) {
        // Splits the documents into words
        // i is the document id
        for (int i = 0; i < texts.size(); i++) {
            String[] words = WORDS.split(texts.get(i));

            // Adds or updates each word in the map
            // j is the position of the word in the doc
            for (int j = 0; j < words.length; j++)
                // Adds the document and position to the map
                indexes.computeIfAbsent(words[j].toLowerCase(),
                        key -> getLOfLWithSize(texts.size())).get(i).add(j);
        }
    }

    // Returns a List of size lists
    // e.g: if size is 2, returns a List of two empty
    // Lists of Integers
    private static List<List<Integer>> getLOfLWithSize(int size) {
        List<List<Integer>> list = new ArrayList<>();
        for (int i = 0; i < size; i++)
            list.add(new ArrayList<>());
        return list;
    }

    // search(key) return all the document ids where the given key
    // phrase appears.key phrase can have one or two words in English
    // alphabetic characters. return an empty list if search() finds
    // no match in all documents.
    public List<Integer> search(String keyPhrase) {
        Set<Integer> matchingDocuments = new TreeSet<>();
        String[] words = WORDS.split(keyPhrase);
        List<List<List<Integer>>> results = new ArrayList<>();

        for (String word : words)
            results.add(indexes.get(word.toLowerCase()));

        if(results.get(0) == null)
            return new ArrayList<>();

        for (int i = 0; i < results.get(0).size(); i++) {
            List<List<Integer>> resultByDocument = new ArrayList<>();

            for (List<List<Integer>> result :results)
                resultByDocument.add(result.get(i));

            if(checkConsecutive(resultByDocument))
                matchingDocuments.add(i);
        }

        return new ArrayList<>(matchingDocuments);
    }


    // Not private for testing
    // Returns true when there is a number a is in the first list
    // in lists, a + 1 in the next list, a + 2 in the next and so on
    boolean checkConsecutive(List<List<Integer>> lists) {
        List<Integer> first = lists.get(0);
        for (int a : first) {
            boolean consecutive = true;
            for (int j = 1; j < lists.size(); j++) {
                a++;
                if (!lists.get(j).contains(a))
                    consecutive = false;
            }
            if (consecutive)
                return true;
        }
        return false;
    }
}