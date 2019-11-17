package com.hackernews.hacker.api;

public interface HackerNewsReader {
    void readNews(String url, int noOfRecords, int page);
    String getTopRankedNews(int n);
    String getSavedNews();
}
