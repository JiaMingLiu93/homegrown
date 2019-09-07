package org.apache.ibatis.override.session;

import mybatis.test.AutoConstructorMapper;
import mybatis.test.BaseDataTest;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.Reader;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author youyu
 * @date 2019/4/12 3:06 PM
 */
public class MapperTest {
    private static NSqlSessionFactory sqlSessionFactory;

    @BeforeAll
    static void setUp() throws Exception {
        // create a SqlSessionFactory
        try (Reader reader = Resources.getResourceAsReader("mybatis/test/mybatis-config.xml")) {
            sqlSessionFactory = new NSqlSessionFactoryBuilder().build(reader);
        }

        // populate in-memory mybatis.test.database
        BaseDataTest.runScript(sqlSessionFactory.getConfiguration().getEnvironment().getDataSource(),
                "mybatis/test/CreateDB.sql");
    }

    @Test
    void fullyPopulatedSubject() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            final AutoConstructorMapper mapper = sqlSession.getMapper(AutoConstructorMapper.class);
            final Object subject = mapper.getSubject(1);
            assertNotNull(subject);
        }
    }
}
