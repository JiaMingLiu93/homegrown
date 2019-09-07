package mybatis.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.Reader;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author jam
 * @date 2019/4/12 1:27 PM
 */
public class MybatisTest {
    private static SqlSessionFactory sqlSessionFactory;
    @BeforeAll
    static void setUp() throws Exception {
        // create a SqlSessionFactory
        try (Reader reader = Resources.getResourceAsReader("mybatis/test/mybatis-config.xml")) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
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
