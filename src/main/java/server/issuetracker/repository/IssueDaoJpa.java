package server.issuetracker.repository;

import server.issuetracker.bean.issue.IssueDataSet;

public class IssueDaoJpa implements IssueDao {

    @Override
    public IssueDataSet get(long id) {
        return null;
    }

    @Override
    public IssueDataSet getByName(String name) {
        return null;
    }

    @Override
    public void add(IssueDataSet issue) {
    }

    @Override
    public void delete(long id) {
    }

}
