package server.issuetracker.bean.issue;

import java.time.LocalDateTime;
import java.util.List;

import server.issuetracker.bean.user.User;

public class Issue {
    private long id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private String summary;
    private String description;
    private Status status;
    private Type type;
    private Priority priority;
    private Project project;
    private Build build;
    private User assignee; 
    private User creator;
    private User modifier;
    private List<Comment> comments;
}
