package org.apache.ibatis.override.session;

import org.apache.ibatis.exceptions.ExceptionFactory;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;

import java.sql.SQLException;

/**
 * @author youyu
 * @date 2019/4/12 3:10 PM
 */
public class NDefaultSqlSessionFactory implements NSqlSessionFactory {
    private final Configuration configuration;

    public NDefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        Transaction tx = null;
        try {
            final Environment environment = configuration.getEnvironment();
            final TransactionFactory transactionFactory = getTransactionFactoryFromEnvironment(environment);
            tx = transactionFactory.newTransaction(environment.getDataSource(), null, false);
            Executor executor = configuration.newExecutor(tx, configuration.getDefaultExecutorType());
            return new NDefaultSqlSession(configuration,executor,false);
        }catch (Exception e){
            closeTransaction(tx); // may have fetched a connection so lets call close()
            throw ExceptionFactory.wrapException("Error opening session.  Cause: " + e, e);
        }finally {
            ErrorContext.instance().reset();
        }
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    private TransactionFactory getTransactionFactoryFromEnvironment(Environment environment) {
        if (environment == null || environment.getTransactionFactory() == null) {
            return new ManagedTransactionFactory();
        }
        return environment.getTransactionFactory();
    }

    private void closeTransaction(Transaction tx) {
        if (tx != null) {
            try {
                tx.close();
            } catch (SQLException ignore) {
                // Intentionally ignore. Prefer previous error.
            }
        }
    }
}
