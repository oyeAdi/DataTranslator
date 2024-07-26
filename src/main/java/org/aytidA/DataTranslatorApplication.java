package org.aytidA;

import org.aytidA.config.ConfigurationLoader;
import org.aytidA.service.DataTranslator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;

/**
 * Main application class for Data Translator.
 * It loads configurations and initiates the data translation process.
 */
public class DataTranslatorApplication {

    /**
     * The main method to start the application.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        Properties config = loadConfig("config.properties");

        String folderPath = config.getProperty("data.folder.path");
        String columnMappingFilePath = config.getProperty("column.mapping.file");
        String identifierMappingFilePath = config.getProperty("identifier.mapping.file");
        int chunkSize = Integer.parseInt(config.getProperty("chunk.size"));

        Map<String, String> columnMapping = ConfigurationLoader.loadColumnMapping(columnMappingFilePath);
        Map<String, String> identifierMapping = ConfigurationLoader.loadIdentifierMapping(identifierMappingFilePath);

        DataTranslator translator = new DataTranslator(folderPath, columnMapping, identifierMapping, chunkSize);
        translator.translate();
    }

    /**
     * Loads the configuration properties from the specified file path.
     *
     * @param filePath the path to the configuration file
     * @return Properties object containing configuration settings
     */
    private static Properties loadConfig(String filePath) {
        Properties properties = new Properties();
        try {
            properties.load(Files.newBufferedReader(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}