package org.apache.ibatis.override.session;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.override.exceptions.NExceptionFactory;

import java.io.IOException;
import java.io.Reader;

/**
 * @author youyu
 * @date 2019/4/12 3:07 PM
 */
public class NSqlSessionFactoryBuilder {
    public NSqlSessionFactory build(Reader reader){
        try {
            XMLConfigBuilder parser = new XMLConfigBuilder(reader, null, null);
            return new NDefaultSqlSessionFactory(parser.parse());
        }catch (Exception e){
            throw NExceptionFactory.wrapException("Error building override SqlSession.",e);
        }finally {
            try {
                reader.close();
            } catch (IOException e) {
                // Intentionally ignore. Prefer previous error.
            }
        }
    }
}
