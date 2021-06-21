package com.homegrown.component.search.client;

/**
 * @author youyu
 */
public interface SearchClient {

    /**
     * create schema for search
     * @param name schema name
     * @param ddl data description language
     * @return whether successful
     */
    boolean createSchemaWith(String name, String ddl);

    /**
     * create schema for search with ext1
     * @param name schema name
     * @param ddl data description language
     * @param ext1 ext
     * @return whether successful
     */
    boolean createSchemaWith(String name, String ddl, String ext1);

    /**
     * create schema from alias
     * @param alias schema alias
     * @return new schema name
     */
    String createSchemaFrom(String alias);

    /**
     * check if index exist
     *
     * @param schemaName index name or alias
     * @return boolean
     */
    boolean exist(String schemaName);



}
