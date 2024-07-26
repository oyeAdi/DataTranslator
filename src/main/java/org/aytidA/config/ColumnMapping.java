package org.aytidA.config;

/**
 * ColumnMapping represents the mapping configuration for columns.
 * It holds the original column name and its corresponding new column name.
 */
public class ColumnMapping {
    private String originalColumn;
    private String newColumn;

    /**
     * Constructs a ColumnMapping with the specified original and new column names.
     *
     * @param originalColumn the original column name
     * @param newColumn      the new column name
     */
    public ColumnMapping(String originalColumn, String newColumn) {
        this.originalColumn = originalColumn;
        this.newColumn = newColumn;
    }

    /**
     * Gets the original column name.
     *
     * @return the original column name
     */
    public String getOriginalColumn() {
        return originalColumn;
    }

    /**
     * Gets the new column name.
     *
     * @return the new column name
     */
    public String getNewColumn() {
        return newColumn;
    }
}
