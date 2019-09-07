package org.apache.ibatis.override.session;

import java.io.Closeable;

/**
 * @author jam
 * @date 2019/4/12 3:00 PM
 */
public interface NSqlSession extends Closeable {
    /**
     * Retrieves a mapper.
     * @param <T> the mapper type
     * @param type Mapper interface class
     * @return a mapper bound to this SqlSession
     */
    <T> T getMapper(Class<T> type);
}
