package server.issuetracker.db;

import java.sql.Connection;

public interface DataBaseService {
    void printConnectionInfo();
    Connection getConnection();
}
