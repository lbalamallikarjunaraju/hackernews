package com.hackernews.hacker;

import com.hackernews.hacker.api.HackerNewsReader;
import com.hackernews.hacker.api.impl.HackerNewsReaderImpl;

public class App {

    public static void main(String[] args) {
        HackerNewsReader reader = new HackerNewsReaderImpl();
        if (args.length != 3 || (!"hackernews".equals(args[0])) || !("--posts".equals(args[1]))) {
            System.err.println("arguments format should be: hackernews --posts #noOfRecords");
            return;
        }
        try {
            System.out.println(reader.getTopRankedNews(Integer.parseInt(args[2])));
        } catch (NumberFormatException e) {
            System.err.println("arguments format should be: hackernews --posts #noOfRecords");
        }
    }
}
