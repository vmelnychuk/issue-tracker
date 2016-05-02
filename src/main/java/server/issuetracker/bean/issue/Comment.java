package server.issuetracker.bean.issue;

import java.time.LocalDateTime;

import server.issuetracker.bean.user.User;

public class Comment {
    private User author;
    private LocalDateTime date;
    private String text;
}
