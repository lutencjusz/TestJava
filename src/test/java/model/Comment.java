package model;

import lombok.Getter;

@Getter
public class Comment {
    private int postId;
    private int id;
    private String name;
    private String body;
}
