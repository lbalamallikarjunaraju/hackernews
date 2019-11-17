package com.hackernews.hacker.model;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class HackerNews {
    private String title;
    private String uri;
    private String author;
    private int points;
    private int comments;
    private int rank;

}
