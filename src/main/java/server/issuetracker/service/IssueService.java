package server.issuetracker.service;

import server.issuetracker.bean.issue.IssueDataSet;

public interface IssueService {
    IssueDataSet getIssue(long id);
    long addIssue(IssueDataSet issue);
}
