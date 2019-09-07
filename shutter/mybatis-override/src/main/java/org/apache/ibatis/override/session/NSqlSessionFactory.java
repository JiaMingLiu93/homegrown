package org.apache.ibatis.override.session;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;

/**
 * @author jam
 * @date 2019/4/12 2:56 PM
 */
public interface NSqlSessionFactory {
    SqlSession openSession();

    Configuration getConfiguration();
}
