package server.issuetracker.bean.issue;

import java.util.List;

import server.issuetracker.bean.user.User;

public class Project {
    private String name;
    private String description;
    private List<Build> builds;
    private User manager;
}
