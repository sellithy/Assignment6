import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class MyMiniSearchEngineTest {
    private List<String> documents() {
        return new ArrayList<>(
                Arrays.asList(
                        "hello world",
                        "hello",
                        "world",
                        "world world hello",
                        "seattle rains hello abc world",
                        "sunday hello world fun",
                        "Hello Seattle AbC World"));
    }

    @Test
    public void testOneWord() {
        MyMiniSearchEngine engine = new MyMiniSearchEngine(documents());
        List<Integer> result = engine.search("seattle");

        assertEquals(2, result.size());

        assertEquals(List.of(4,6), result);

        result = engine.search("World");
        assertEquals(List.of(0,2,3,4,5,6), result);
    }

    @Test
    public void testTwoWord() {
        MyMiniSearchEngine engine = new MyMiniSearchEngine(documents());
        List<Integer> result = engine.search("hello world");

        assertEquals(2, result.size());

        assertEquals(List.of(0, 5), result);
    }

    @Test
    public void testThreeWord() {
        MyMiniSearchEngine engine = new MyMiniSearchEngine(documents());

        String[] inputs = {
                "rains hello abc",
                "rains Hello abc",
        };

        for (String input : inputs) {
            List<Integer> result = engine.search(input);
            assertEquals(1, result.size());
            assertEquals(List.of(4), result);
        }

        assertEquals(List.of(5), engine.search("hello world fun"));
    }

    @Test
    public void testFourWord() {
        MyMiniSearchEngine engine = new MyMiniSearchEngine(documents());

        String[] inputs = {
                "seattle rains hello abc",
                "rains hello abc world",
        };

        for (String input : inputs) {
            List<Integer> result = engine.search(input);
            assertEquals(1, result.size());
            assertEquals(List.of(4), result);
        }

        String anotherInput = "hello seattle abc world";
        assertEquals(List.of(6), engine.search(anotherInput));
    }

    @Test
    public void testWordNotFound() {
        MyMiniSearchEngine engine = new MyMiniSearchEngine(documents());

        String[] inputs = {
                "Bothell",
                "IDK",
        };

        for (String input : inputs) {
            List<Integer> result = engine.search(input);
            assertEquals(0, result.size());
            assertEquals(List.of(), result);
        }
    }

    @Test
    public void testCheckConsecutive(){
        MyMiniSearchEngine engine = new MyMiniSearchEngine(documents());

        assertFalse(engine.checkConsecutive(List.of(List.of(), List.of())));
        assertFalse(engine.checkConsecutive(List.of(List.of(0,1), List.of(2), List.of(4))));
        assertFalse(engine.checkConsecutive(List.of(List.of(0,1), List.of(2,3), List.of(4))));

        assertTrue(engine.checkConsecutive(List.of(List.of(0))));
        assertTrue(engine.checkConsecutive(List.of(List.of(1), List.of(2), List.of(3))));
        assertTrue(engine.checkConsecutive(List.of(List.of(0,1), List.of(2), List.of(3))));
        assertTrue(engine.checkConsecutive(List.of(List.of(0,5), List.of(2,6), List.of(3,7))));
    }
}