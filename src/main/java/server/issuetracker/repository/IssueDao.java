package server.issuetracker.repository;

import server.issuetracker.bean.issue.IssueDataSet;

public interface IssueDao {
    IssueDataSet get(long id);
    IssueDataSet getByName(String name);
    void add(IssueDataSet issue);
    void delete(long id);
}
