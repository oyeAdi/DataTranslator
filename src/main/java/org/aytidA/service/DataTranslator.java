package org.aytidA.service;

import org.aytidA.util.LoggerUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * DataTranslator is responsible for translating data files based on column and identifier mappings.
 * It processes files in chunks using an ExecutorService for parallel processing.
 */
public class DataTranslator {
    private final String folderPath;
    private final Map<String, String> columnMapping;
    private final Map<String, String> identifierMapping;
    private final int chunkSize;

    /**
     * Constructs a DataTranslator with the specified parameters.
     *
     * @param folderPath        the path to the folder containing data files
     * @param columnMapping     the column mapping configuration
     * @param identifierMapping the identifier mapping configuration
     * @param chunkSize         the size of chunks for processing
     */
    public DataTranslator(String folderPath, Map<String, String> columnMapping, Map<String, String> identifierMapping, int chunkSize) {
        this.folderPath = folderPath;
        this.columnMapping = columnMapping;
        this.identifierMapping = identifierMapping;
        this.chunkSize = chunkSize;
    }

    /**
     * Initiates the translation process for all data files in the specified folder.
     */
    public void translate() {
        try {
            Files.list(Paths.get(folderPath))
                    .filter(path -> path.toString().endsWith(".tsv") && path.getFileName().toString().startsWith("dataFile"))
                    .forEach(this::processFile);
        } catch (IOException e) {
            LoggerUtil.logError("Error listing files in folder: " + folderPath, e);
        }
    }

    /**
     * Processes a single data file by splitting it into chunks and processing each chunk in parallel.
     *
     * @param dataFilePath the path to the data file to be processed
     */
    private void processFile(Path dataFilePath) {
        String fileName = dataFilePath.getFileName().toString();
        String outputFileName = fileName.replace("dataFile", "outputFile");
        Path outputFilePath = Paths.get(folderPath, outputFileName);

        try {
            List<String> lines = Files.readAllLines(dataFilePath);
            if (lines.isEmpty()) {
                LoggerUtil.logError("File is empty: " + dataFilePath, null);
                return;
            }

            System.out.println("Lines read from file: " + lines.size());

            // Initialize the ExecutorService
            ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            List<Future<List<DataRow>>> futures = new ArrayList<>();

            // Extract header line
            String headerLine = lines.get(0);
            lines = lines.subList(1, lines.size());

            // Submit chunks to the ExecutorService
            for (int i = 0; i < lines.size(); i += chunkSize) {
                int end = Math.min(i + chunkSize, lines.size());
                List<String> chunk = new ArrayList<>(lines.subList(i, end));
                if (i == 0) {
                    chunk.add(0, headerLine); // Ensure the first chunk has the header
                }
                System.out.println("Processing chunk from line " + i + " to " + end);
                DataFileProcessor processor = new DataFileProcessor(columnMapping, identifierMapping, chunk);
                futures.add(executorService.submit(processor));
            }

            // Combine results
            List<DataRow> allDataRows = new ArrayList<>();
            for (Future<List<DataRow>> future : futures) {
                List<DataRow> result = future.get();
                System.out.println("Chunk processed, number of rows: " + result.size());
                for (DataRow row : result) {
                    System.out.println(row);
                }
                allDataRows.addAll(result);
            }

            // Shutdown the ExecutorService
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
                Thread.currentThread().interrupt();
            }

            // Write the combined results to the output file
            DataFileWriter writer = new DataFileWriter();
            writer.writeFile(outputFilePath, allDataRows, columnMapping);
        } catch (IOException | InterruptedException | ExecutionException e) {
            LoggerUtil.logError("Error processing file: " + dataFilePath, e);
        }
    }
}