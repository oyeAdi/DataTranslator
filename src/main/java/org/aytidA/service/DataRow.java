package org.aytidA.service;

import java.util.Map;
/**
 * DataRow represents a row of processed data with a mapping of column names to their values.
 */
public class DataRow {
    private final Map<String, String> data;

    /**
     * Constructs a DataRow with the specified data.
     *
     * @param data a map of column names to their corresponding values
     */
    public DataRow(Map<String, String> data) {
        this.data = data;
    }

    /**
     * Gets the data of this row.
     *
     * @return a map of column names to their corresponding values
     */
    public Map<String, String> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "DataRow{" +
                "data=" + data +
                '}';
    }
}