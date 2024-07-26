package org.aytidA.config;

/**
 * IdentifierMapping represents the mapping configuration for identifiers.
 * It holds the original identifier and its corresponding new identifier.
 */
public class IdentifierMapping {
    private String originalId;
    private String newId;

    /**
     * Constructs an IdentifierMapping with the specified original and new identifiers.
     *
     * @param originalId the original identifier
     * @param newId      the new identifier
     */
    public IdentifierMapping(String originalId, String newId) {
        this.originalId = originalId;
        this.newId = newId;
    }

    /**
     * Gets the original identifier.
     *
     * @return the original identifier
     */
    public String getOriginalId() {
        return originalId;
    }

    /**
     * Gets the new identifier.
     *
     * @return the new identifier
     */
    public String getNewId() {
        return newId;
    }
}
