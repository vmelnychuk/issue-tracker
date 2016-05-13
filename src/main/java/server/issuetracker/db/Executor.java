package server.issuetracker.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Executor {
    private final Connection connection;
    
    public Executor(Connection connection) {
        this.connection = connection;
    }
    
    public void execUpdate(String update) throws SQLException {
        Statement stament = connection.createStatement();
        stament.executeQuery(update);
        stament.close();
    }
    
    public void execUpdateWithTransaction(String[] updates) {
        try {
            connection.setAutoCommit(false);
            for(String update : updates) {
                Statement stament = connection.createStatement();
                stament.executeQuery(update);
                stament.close();
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
                connection.setAutoCommit(false);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
    
    public <T> T execQuery(String query, ResultHandler<T> handler) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeQuery(query);
        ResultSet resultSet = statement.getResultSet();
        T value = handler.handle(resultSet);
        resultSet.close();
        statement.close();
        return value;
    }
}
