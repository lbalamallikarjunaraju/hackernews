package com.hackernews.hacker.util;

public final class Constants {
    private Constants(){}

    public static final String WEB_ELEMENT_TITLE = "td.title";
    public static final String WEB_ELEMENT_SUBTEXT = "td.subtext";
    public static final String HACKER_NEWS_URL = "https://news.ycombinator.com/news?p=";

    public static final String RANK_ERROR_MSG = "Rank should be positive and non--zero integer";
    public static final String URL_ERROR_MSG = "Invalid URL";
    public static final String TITLE_ERROR_MSG = "Invalid Title";
    public static final String POINTS_ERROR_MSG = "Points should be positive and non--zero integer";
    public static final String COMMENTS_ERROR_MSG = "Comments should be positive and non--zero integer";
}
