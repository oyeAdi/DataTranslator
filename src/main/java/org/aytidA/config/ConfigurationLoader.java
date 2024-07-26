package org.aytidA.config;

import org.aytidA.util.LoggerUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ConfigurationLoader is responsible for loading column and identifier mappings from configuration files.
 */
public class ConfigurationLoader {

    /**
     * Loads column mappings from the specified file path.
     *
     * @param filePath the path to the column mapping file
     * @return a map of original column names to new column names
     */
    public static Map<String, String> loadColumnMapping(String filePath) {
        return loadMapping(filePath, "column");
    }

    /**
     * Loads identifier mappings from the specified file path.
     *
     * @param filePath the path to the identifier mapping file
     * @return a map of original identifiers to new identifiers
     */
    public static Map<String, String> loadIdentifierMapping(String filePath) {
        return loadMapping(filePath, "identifier");
    }

    /**
     * Loads mappings from the specified file path.
     *
     * @param filePath the path to the mapping file
     * @param type     the type of mapping (column or identifier)
     * @return a map of original names to new names
     */
    private static Map<String, String> loadMapping(String filePath, String type) {
        Map<String, String> mapping = new LinkedHashMap<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines.stream().collect(Collectors.toList())) {
                String[] parts = line.split("    ");
                if (parts.length < 2) {
                    LoggerUtil.logError("Invalid line in " + type + " mapping file: " + line, null);
                    continue;
                }
                String key = parts[0].trim();
                String value = parts[1].trim();
                mapping.put(key, value);
            }
        } catch (IOException e) {
            LoggerUtil.logError("Error loading " + type + " mapping from file: " + filePath, e);
        }
        return mapping;
    }
}