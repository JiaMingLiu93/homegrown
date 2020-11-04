/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package com.homegrown.tools.code.generator.struct.processor.demo.writer;

import freemarker.cache.StrongCacheStorage;
import freemarker.cache.TemplateLoader;
import freemarker.log.Logger;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;


import javax.tools.FileObject;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.homegrown.tools.code.generator.struct.processor.demo.writer.Writable.Context;

/**
 * Writes Java source files based on given mapper models, using a FreeMarker
 * template.
 *
 * @author youyu
 */
public class ModelWriter {

    /**
     * FreeMarker configuration. As per the documentation, thread-safe if not
     * altered after original initialization
     */
    private static final Configuration CONFIGURATION;

    static {
        try {
            Logger.selectLoggerLibrary( Logger.LIBRARY_NONE );
        }
        catch ( ClassNotFoundException e ) {
            throw new RuntimeException( e );
        }

        CONFIGURATION = new Configuration( Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS );
        CONFIGURATION.setTemplateLoader( new SimpleClasspathLoader() );
        CONFIGURATION.setObjectWrapper( new DefaultObjectWrapper( Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS ) );
//        CONFIGURATION.setSharedVariable(
//            "includeModel",
//            new ModelIncludeDirective( CONFIGURATION )
//        );
        // do not refresh/gc the cached templates, as we never change them at runtime
        CONFIGURATION.setCacheStorage( new StrongCacheStorage() );
        CONFIGURATION.setTemplateUpdateDelay( Integer.MAX_VALUE );
        CONFIGURATION.setLocalizedLookup( false );
    }

    public void writeModel(FileWriter sourceFile, Writable model) {
        try ( BufferedWriter writer = new BufferedWriter( new IndentationCorrectingWriter( sourceFile))) {
                Map<Class<?>, Object> values = new HashMap<>();
                values.put( Configuration.class, CONFIGURATION );

                model.write( new DefaultModelElementWriterContext( values ), writer );

                
                writer.flush();
        }
        catch ( RuntimeException e ) {
            throw e;
        }
        catch ( Exception e ) {
            throw new RuntimeException( e );
        }
    }

    /**
     * Simplified template loader that avoids reading modification timestamps and disables the jar-file caching.
     *
     * @author Andreas Gudian
     */
    private static final class SimpleClasspathLoader implements TemplateLoader {
        @Override
        public Reader getReader(Object name, String encoding) throws IOException {
            URL url = getClass().getClassLoader().getResource( String.valueOf( name ) );
            if ( url == null ) {
                throw new IllegalStateException( name + " not found on classpath" );
            }
            URLConnection connection = url.openConnection();

            // don't use jar-file caching, as it caused occasionally closed input streams [at least under JDK 1.8.0_25]
            connection.setUseCaches( false );

            InputStream is = connection.getInputStream();

            return new InputStreamReader( is, StandardCharsets.UTF_8 );
        }

        @Override
        public long getLastModified(Object templateSource) {
            return 0;
        }

        @Override
        public Object findTemplateSource(String name) throws IOException {
            return name;
        }

        @Override
        public void closeTemplateSource(Object templateSource) throws IOException {
        }
    }

    /**
     * {@link Context} implementation which provides access to the current FreeMarker {@link Configuration}.
     *
     * @author Gunnar Morling
     */
    static class DefaultModelElementWriterContext implements Context {

        private final Map<Class<?>, Object> values;

        DefaultModelElementWriterContext(Map<Class<?>, Object> values) {
            this.values = new HashMap<>( values );
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T get(Class<T> type) {
            return (T) values.get( type );
        }
    }
}
