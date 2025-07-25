package ru.shift.filterutility.config;

import org.junit.Test;
import ru.shift.filterutility.exception.NoInputFilesException;

import static org.junit.Assert.*;

public class ConfigTest {

    private Config config;

    public void setUp() {
        config = new Config();
    }

    @Test
    public void testInitialStateDefaults() {
        assertTrue(config.getInputFileNames().isEmpty());
        assertEquals("", config.getPrefix());
        assertEquals("", config.getOutputPath());
        assertFalse(config.getShouldAppend());
        assertFalse(config.getShowStats());
        assertFalse(config.getFullStats());
    }

    @Test
    public void testParseArgsWithInputFilesOnly() throws Exception {
        String[] args = {"file1.txt", "file2.txt", "file3.txt"};
        config.parseArgs(args);

        assertEquals(3, config.getInputFileNames().size());
        assertEquals("file1.txt", config.getInputFileNames().get(0));
        assertEquals("file2.txt", config.getInputFileNames().get(1));
        assertEquals("file3.txt", config.getInputFileNames().get(2));
    }

    @Test
    public void testParseArgsWithAllOptions() throws Exception {
        String[] args = {"-o", "output/path", "-p", "prefix", "-a", "-f", "file1.txt", "file2.txt"};
        config.parseArgs(args);

        assertEquals("output/path/", config.getOutputPath());
        assertEquals("prefix", config.getPrefix());
        assertTrue(config.getShouldAppend());
        assertTrue(config.getShowStats());
        assertTrue(config.getFullStats());
        assertEquals(2, config.getInputFileNames().size());
    }

    @Test
    public void testParseArgsWithDuplicateOutputPath() throws Exception {
        String[] args = {"-o", "first", "-o", "second", "input.txt"};
        config.parseArgs(args);

        assertEquals( "second/", config.getOutputPath());
    }

    @Test
    public void testParseArgsWithDuplicatePrefix() throws Exception {
        String[] args = {"-p", "first", "-p", "second", "input.txt"};
        config.parseArgs(args);

        assertEquals("second", config.getPrefix());
    }

    @Test
    public void testParseArgsWithDuplicateAppendFlag() throws Exception {
        String[] args = {"-a", "-a", "input.txt"};
        config.parseArgs(args);

        assertTrue(config.getShouldAppend());
    }

    @Test
    public void testParseArgsWithStatsOverride() throws Exception {
        String[] args = {"-s", "-f", "input.txt"};
        config.parseArgs(args);

        assertTrue(config.getShowStats());
        assertTrue(config.getFullStats());
    }

    @Test
    public void testParseArgsWithFullStatsOverriddenByShort() throws Exception {
        String[] args = {"-f", "-s", "input.txt"};
        config.parseArgs(args);

        assertTrue(config.getShowStats());
        assertFalse(config.getFullStats());
    }

    @Test
    public void testParseArgsWithSpecialCharacters() throws Exception {
        String[] args = {"-o", "path/with/русские-символы", "-p", "prefix@#$%", "файл.txt"};
        config.parseArgs(args);

        assertEquals("path/with/русские-символы/", config.getOutputPath());
        assertEquals("prefix@#$%", config.getPrefix());
        assertEquals("файл.txt", config.getInputFileNames().get(0));
    }

    @Test(expected = NoInputFilesException.class)
    public void testParseArgsWithNoInputFiles() throws Exception {
        String[] args = {};
        config.parseArgs(args);
    }

    @Test(expected = NoInputFilesException.class)
    public void testParseArgsWithOnlyOptions() throws Exception {
        String[] args = {"-o", "output", "-p", "prefix", "-a", "-s"};
        config.parseArgs(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseArgsWithMissingOutputPath() throws Exception {
        String[] args = {"-o"};
        config.parseArgs(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseArgsWithMissingPrefix() throws Exception {
        String[] args = {"-p"};
        config.parseArgs(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseArgsWithOutputPathAtEnd() throws Exception {
        String[] args = {"input.txt", "-o"};
        config.parseArgs(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseArgsWithPrefixAtEnd() throws Exception {
        String[] args = {"input.txt", "-p"};
        config.parseArgs(args);
    }

    @Test
    public void testParseArgsWithUnknownOptions() throws Exception {
        String[] args = {"-x", "-unknown", "--verbose", "input.txt"};
        config.parseArgs(args);

        assertEquals(4, config.getInputFileNames().size());
        assertEquals("-x", config.getInputFileNames().get(0));
        assertEquals("-unknown", config.getInputFileNames().get(1));
        assertEquals("--verbose", config.getInputFileNames().get(2));
        assertEquals("input.txt", config.getInputFileNames().get(3));
    }

    @Test
    public void testParseArgsWithFileNamesLookingLikeOptions() throws Exception {
        String[] args = {"-o", "output", "-s.txt", "-p.dat", "regular.txt"};
        config.parseArgs(args);

        assertEquals(3, config.getInputFileNames().size());
        assertTrue( config.getInputFileNames().contains("-s.txt"));
        assertTrue(config.getInputFileNames().contains("-p.dat"));
        assertTrue(config.getInputFileNames().contains("regular.txt"));
    }

    @Test(expected = NoInputFilesException.class)
    public void testParseArgsWithEmptyArray() throws Exception {
        String[] args = {};
        config.parseArgs(args);
    }

    @Test
    public void testParseArgsCalledTwice() throws Exception {
        String[] args1 = {"-p", "first", "file1.txt"};
        config.parseArgs(args1);

        assertEquals(1, config.getInputFileNames().size());
        assertEquals("first", config.getPrefix());

        String[] args2 = {"-p", "second", "file2.txt"};
        config.parseArgs(args2);

        assertEquals(2, config.getInputFileNames().size());
        assertEquals("second", config.getPrefix());
        assertTrue(config.getInputFileNames().contains("file1.txt"));
        assertTrue(config.getInputFileNames().contains("file2.txt"));
    }
}