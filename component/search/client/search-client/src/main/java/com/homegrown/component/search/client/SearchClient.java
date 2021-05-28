package com.homegrown.component.search.client;

/**
 * @author youyu
 */
public interface SearchClient {

    /**
     * create scheme for search
     * @param name scheme name
     * @param ddl data description language
     * @return
     */
    boolean createWithSchema(String name, String ddl);

    /**
     * create scheme for search with ext1
     * @param name scheme name
     * @param ddl data description language
     * @param ext1 ext
     * @return
     */
    boolean createWithSchema(String name, String ddl, String ext1);

    /**
     * check if index exist
     *
     * @param schemaName index name or alias
     * @return boolean
     */
    boolean exist(String schemaName);



}
