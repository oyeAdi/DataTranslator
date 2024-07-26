package org.aytidA.service;

import org.aytidA.util.LoggerUtil;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * DataFileWriter writes the processed data to an output file.
 */
public class DataFileWriter {
    /**
     * Writes the processed data rows to the specified output file.
     *
     * @param outputPath    the path to the output file
     * @param dataRows      the list of processed data rows
     * @param columnMapping the column mapping configuration
     */
    public void writeFile(Path outputPath, List<DataRow> dataRows, Map<String, String> columnMapping) {
        try (BufferedWriter writer = Files.newBufferedWriter(outputPath)) {
            // Write header
            for (String newColumn : columnMapping.values()) {
                writer.write(newColumn + "    ");
            }
            writer.newLine();

            // Write data rows
            for (DataRow row : dataRows) {
                //writer.write(row.getId() + "    ");
                for (String newColumn : columnMapping.values()) {
                    writer.write(row.getData().getOrDefault(newColumn, "") + "    ");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            LoggerUtil.logError("Error writing file: " + outputPath, e);
        }
    }
}