package server.issuetracker.db;

import org.h2.jdbcx.JdbcDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBService implements DataBaseService {
    private static final String URL = "jdbc:h2:./database";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "root";
    
    private static final Logger log = LoggerFactory.getLogger(DBService.class);
    
    private final Connection connection;
    
    public DBService() {
        this.connection = getConnection();
    }

    @Override
    public void printConnectionInfo() {
        try {
            log.info("DB name: " + connection.getMetaData().getDatabaseProductName());
            log.info("DB version: " + connection.getMetaData().getDatabaseProductVersion());
            log.info("Driver: " + connection.getMetaData().getDriverName());
            log.info("Autocommit status: " + connection.getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() {
        Connection connection = null;
        try {
            JdbcDataSource dataSource = new JdbcDataSource();
            dataSource.setUrl(URL);
            dataSource.setUser(LOGIN);
            dataSource.setPassword(PASSWORD);
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
