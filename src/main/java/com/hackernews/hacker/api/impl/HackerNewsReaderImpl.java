package com.hackernews.hacker.api.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hackernews.hacker.api.HackerNewsReader;
import com.hackernews.hacker.model.HackerNews;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ListIterator;
import java.util.SortedMap;
import java.util.TreeMap;

import static com.hackernews.hacker.util.Constants.*;

public class HackerNewsReaderImpl implements HackerNewsReader {
    private SortedMap<Integer, HackerNews> hackerNewsMap;

    /**
     * Navigates to the URL and reads all the web elements to get the required fields.
     *
     * @param url         - URL to read the page
     * @param noOfRecords - Number of news to read
     * @param page        - pagination index
     */
    @Override
    public void readNews(String url, int noOfRecords, int page) {
        Document doc = null;
        try {
            // Connect and get the page as document, get method validates the URL and MIME type
            doc = Jsoup.connect(url + page).get();
        } catch (IOException e) {
            throw new RuntimeException(URL_ERROR_MSG);
        }
        if (doc == null) {
            throw new RuntimeException(URL_ERROR_MSG);
        }
        if (hackerNewsMap == null) {
            hackerNewsMap = new TreeMap<>();
        }
        if (noOfRecords <= 0 || page <= 0) {
            throw new RuntimeException("Invalid input");
        }
        Elements posts = doc.select(WEB_ELEMENT_TITLE);
        Elements subPosts = doc.select(WEB_ELEMENT_SUBTEXT);
        ListIterator<Element> postIterator = posts.listIterator();
        ListIterator<Element> subPostIterator = subPosts.listIterator();
        while (postIterator.hasNext()) {
            HackerNews hackerNews = new HackerNews();
            String rank = "";
            Element rankE = postIterator.next();
            if (rankE == null) {
                throw new RuntimeException(RANK_ERROR_MSG);
            } else {
                rank = rankE.text();
                if (rank == null || rank.isEmpty()) {
                    throw new RuntimeException(RANK_ERROR_MSG);
                }
            }
            // Checking for the End of the page
            if (!rank.equals("More")) {
                int rankAsInt = 0;
                try {
                    rankAsInt = Integer.parseInt(rank.replace(".", "").trim());
                } catch (NumberFormatException e) {
                    throw new RuntimeException(RANK_ERROR_MSG);
                }
                if (rankAsInt <= 0) {
                    throw new RuntimeException(RANK_ERROR_MSG);
                }
                hackerNews.setRank(rankAsInt);
            } else {
                // Navigating through pagination to get number of records that are expected
                if (hackerNewsMap.size() <= noOfRecords) {
                    readNews(url, noOfRecords, page + 1);
                    break;
                } else {
                    break;
                }
            }
            // Reading news title
            Element titleInformation = postIterator.next();
            Element title = titleInformation.select("a").first();
            //title should be non empty string and not longer than 256 characters.
            if (title == null || title.text().length() > 256) {
                throw new RuntimeException(TITLE_ERROR_MSG);
            }
            hackerNews.setTitle(title.text());

            // Reading news URI
            String newsUri = title.attr("href");
            try {
                new URI(newsUri);
            } catch (URISyntaxException e) {
                throw new RuntimeException(URL_ERROR_MSG);
            }
            hackerNews.setUri(newsUri);

            Element subPostInformation = subPostIterator.next();

            // Reading points
            Element points = subPostInformation.select("span").first();
            if (points != null) {
                int pointsAsInt = 0;
                try {
                    pointsAsInt = Integer.parseInt(points.text()
                            .replace("points", "")
                            .replace("point", "")
                            .trim());
                } catch (NumberFormatException e) {
                    throw new RuntimeException(POINTS_ERROR_MSG);
                }
                if (pointsAsInt <= 0) {
                    throw new RuntimeException(POINTS_ERROR_MSG);
                }
                hackerNews.setPoints(pointsAsInt);
            } else {
                throw new RuntimeException(POINTS_ERROR_MSG);
            }

            //User details
            Element user = subPostInformation.select("a[href^=user]").first();
            //author should be non empty string and not longer than 256 characters.
            if (user == null || user.text().length() > 256) {
                throw new RuntimeException("Invalid Author");
            }
            hackerNews.setAuthor(user.text());

            //Comments
            Element comment = subPostInformation.select("a[href^=item]")
                    .last();
            if (comment != null) {
                int commentsAsInt = 0;
                try {
                    commentsAsInt = Integer.parseInt(comment.text()
                            .replace("comments", "")
                            .replace("comment", "")
                            .trim());
                } catch (NumberFormatException e) {
                    throw new RuntimeException(COMMENTS_ERROR_MSG);
                }
                if (commentsAsInt <= 0) {
                    throw new RuntimeException(COMMENTS_ERROR_MSG);
                }
                hackerNews.setComments(commentsAsInt);
            } else {
                throw new RuntimeException(COMMENTS_ERROR_MSG);
            }

            hackerNewsMap.put(hackerNews.getRank(), hackerNews);
            if (noOfRecords == hackerNewsMap.size()) {
                return;
            }
        }
    }

    /**
     * Function to get top N records
     *
     * @param noOfRecords - number of records to fetch
     * @return JSON String- Returns JSON Array of top rank news information
     */
    @Override
    public String getTopRankedNews(int noOfRecords) {
        if (noOfRecords <= 0) {
            throw new RuntimeException("Invalid input");
        }
        hackerNewsMap = new TreeMap<>();
        // Start with page-1, init method takes care of navigating to remaining pages if required
        readNews(HACKER_NEWS_URL, noOfRecords, 1);
        return toJson(hackerNewsMap.headMap(noOfRecords + 1));
    }

    /**
     * Returns all news that are processed.
     *
     * @return JSON of all news that are stored
     */
    @Override
    public String getSavedNews() {
        if (hackerNewsMap == null || hackerNewsMap.size() <= 0) {
            return "";
        }
        return toJson(hackerNewsMap);

    }

    private String toJson(SortedMap<Integer, HackerNews> sortedNewsMap) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(sortedNewsMap.values().toArray());
    }
}
