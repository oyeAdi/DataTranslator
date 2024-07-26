package org.aytidA.service;

import org.aytidA.util.LoggerUtil;

import java.util.*;
import java.util.concurrent.Callable;

/**
 * DataFileProcessor processes a chunk of data from a data file.
 * It maps columns and identifiers based on the provided configurations.
 */
public class DataFileProcessor implements Callable<List<DataRow>> {
    private final Map<String, String> columnMapping;
    private final Map<String, String> identifierMapping;
    private final List<String> lines;
    private final Map<String, Integer> headerIndexMap;

    /**
     * Constructs a DataFileProcessor with the specified parameters.
     *
     * @param columnMapping     the column mapping configuration
     * @param identifierMapping the identifier mapping configuration
     * @param lines             the lines of data to be processed
     */
    public DataFileProcessor(Map<String, String> columnMapping, Map<String, String> identifierMapping, List<String> lines) {
        this.columnMapping = columnMapping;
        this.identifierMapping = identifierMapping;
        this.lines = lines;
        this.headerIndexMap = createHeaderIndexMap(lines.get(0).split("    "));
    }

    /**
     * Processes the chunk of data.
     *
     * @return a list of processed data rows
     * @throws Exception if an error occurs during processing
     */
    @Override
    public List<DataRow> call() {
        List<DataRow> dataRows = new ArrayList<>();
        try {
            for (String line : lines) {
                String[] values = line.split("    ");
                if (values.length == 0) continue;

                String id = values[0].trim();
                if (!identifierMapping.containsKey(id)) {
                    LoggerUtil.logInfo("Skipping record with ID " + id + " as it is not in the identifier mapping.");
                    continue;
                }

                Map<String, String> data = new LinkedHashMap<>();
                for (Map.Entry<String, String> entry : columnMapping.entrySet()) {
                    String originalColumn = entry.getKey();
                    String newColumn = entry.getValue();

                    Integer index = headerIndexMap.get(originalColumn);
                    if (index == null) {
                        LoggerUtil.logError("Column " + originalColumn + " not found in header: " + String.join("    ", headerIndexMap.keySet()), null);
                        continue;
                    }

                    if (index < values.length) {
                        String value = (index == 0) ? identifierMapping.get(id).trim() : values[index].trim();
                        data.put(newColumn, value);
                    } else {
                        LoggerUtil.logError("Index out of bounds for column " + originalColumn + " in line: " + line, null);
                    }
                }
                dataRows.add(new DataRow(data));
            }
        } catch (Exception e) {
            LoggerUtil.logError("Error processing lines", e);
        }
        return dataRows;
    }

    /**
     * Creates a header index map for easy lookup of column indices.
     *
     * @param headers the headers of the data file
     * @return a map of column names to their indices
     */
    private Map<String, Integer> createHeaderIndexMap(String[] headers) {
        Map<String, Integer> headerIndexMap = new LinkedHashMap<>();
        for (int i = 0; i < headers.length; i++) {
            headerIndexMap.put(headers[i].trim(), i);
        }
        return headerIndexMap;
    }
}