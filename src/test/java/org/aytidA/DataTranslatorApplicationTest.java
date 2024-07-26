package org.aytidA;

import org.aytidA.config.ConfigurationLoader;
import org.aytidA.service.DataFileProcessor;
import org.aytidA.service.DataRow;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * DataTranslatorApplicationTest contains unit tests for the DataFileProcessor class.
 * These tests ensure that data is processed correctly according to the given mappings.
 */
public class DataTranslatorApplicationTest {
    /**
     * Tests the processing of a data file to ensure that the data rows are correctly
     * filtered and mapped according to the column and identifier mappings.
     */
    @Test
    public void testProcessFile() throws IOException {
        // Define the paths to the test files
        String columnMappingFilePath = "src/test/resources/column_mapping.tsv";
        String identifierMappingFilePath = "src/test/resources/identifier_mapping.tsv";
        String dataFilePath = "src/test/resources/dataFile0.tsv";

        // Load the column and identifier mappings
        Map<String, String> columnMapping = ConfigurationLoader.loadColumnMapping(columnMappingFilePath);
        Map<String, String> identifierMapping = ConfigurationLoader.loadIdentifierMapping(identifierMappingFilePath);

        // Read the data file lines
        List<String> dataLines = Files.readAllLines(Paths.get(dataFilePath));

        // Create a DataFileProcessor instance with the loaded mappings and the data lines
        DataFileProcessor processor = new DataFileProcessor(columnMapping, identifierMapping, dataLines);

        // Process the data file
        List<DataRow> dataRows = processor.call();

        // Perform assertions to verify the processed data
        assertEquals(1, dataRows.size()); // Expecting 1 row after filtering
        assertEquals("OURIDXXX", dataRows.get(0).getData().get("OURID")); // Check if the ID is correctly mapped
    }
}