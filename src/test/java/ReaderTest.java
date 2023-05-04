import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ReaderTest {

    private static HashSet<String> wordsInFile;

    @BeforeAll
    public static void setUpClass() {
        wordsInFile = new HashSet<>(Arrays.asList("hello", "world", "goodbye", "cruel", "world"));
    }

    @BeforeEach
    public void setUp() {
        Reader.meetRequirement(wordsInFile);
    }

    @Test
    public void testPut() {
        HashMap<String, Integer> wordsWithNumber = new HashMap<>();
        Reader.put(wordsWithNumber, wordsInFile);
        assertEquals(4, wordsWithNumber.size());
        assertTrue(wordsWithNumber.containsKey("hello"));
        assertTrue(wordsWithNumber.containsKey("world"));
        assertTrue(wordsWithNumber.containsKey("goodbye"));
        assertTrue(wordsWithNumber.containsKey("cruel"));
        assertThat(wordsWithNumber.values(), containsInAnyOrder(3,4,3,3));
    }

    @Test
    public void testPrinter() {
        HashMap<String, Integer> wordsWithNumber = new HashMap<>();
        Reader.put(wordsWithNumber, wordsInFile);
        assertDoesNotThrow(() -> Reader.printer(wordsWithNumber));
    }

    @Test
    public void testNumberOfLetters() {
        assertEquals(3, Reader.numberOfLetters("hello"));
        assertEquals(4, Reader.numberOfLetters("world"));
        assertEquals(3, Reader.numberOfLetters("goodbye"));
        assertEquals(3, Reader.numberOfLetters("cruel"));
    }

    @Test
    public void testMeetRequirement() {
        assertFalse(wordsInFile.contains("supercalifragilisticexpialidocious"));
        assertFalse(wordsInFile.contains("supercalifragilisticexpiali"));
    }

    @Test
    public void testMeetRequirementLongWord() {
        wordsInFile.add("supercalifragilisticexpialidocious");
        Reader.meetRequirement(wordsInFile);
        assertTrue(wordsInFile.contains("supercalifragilisticexpialidocious"));
        assertFalse(wordsInFile.contains("supercalifragilisticexpiali"));
        wordsInFile.remove("supercalifragilisticexpialidocious");
    }

    @Test
    public void testMeetRequirement2() {
        HashSet<String> words = new HashSet<>(Arrays.asList("this", "is", "a", "test", "string", "with", "longwordslikethisone"));
        Reader.meetRequirement(words);
        assertThat(words, hasItems("longwordslikethisone", "this", "is", "a", "test", "string"));
        assertThat(words, not(hasItem("longwordslikethisoneinvalid")));
    }

    @Test
    public void testMeetRequirementShortWord() {
        wordsInFile.add("hi");
        Reader.meetRequirement(wordsInFile);
        assertTrue(wordsInFile.contains("hi"));
    }

    @Test
    public void testException() {
        assertThrows(NullPointerException.class, () -> Reader.meetRequirement(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"hello", "world", "goodbye", "cruel"})
    public void testPutParameterized(String word) {
        HashMap<String, Integer> wordsWithNumber = new HashMap<>();
        Reader.put(wordsWithNumber, wordsInFile);
        assertTrue(wordsWithNumber.containsKey(word));
    }

    @Test
    public void testPrinterWithDuplicates() {
        wordsInFile.add("hello");
        HashMap<String, Integer> wordsWithNumber = new HashMap<>();
        Reader.put(wordsWithNumber, wordsInFile);
        assertDoesNotThrow(() -> Reader.printer(wordsWithNumber));
    }
}
