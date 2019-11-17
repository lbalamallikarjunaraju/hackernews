package com.hackernews.hacker.api;

import com.google.gson.Gson;
import com.hackernews.hacker.api.impl.HackerNewsReaderImpl;
import com.hackernews.hacker.model.HackerNews;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static com.hackernews.hacker.util.Constants.HACKER_NEWS_URL;
import static com.hackernews.hacker.util.Constants.URL_ERROR_MSG;

public class HackerNewsReaderTest {

    private HackerNewsReader reader;
    private Gson gson;

    @Before
    public void init() {
        reader = new HackerNewsReaderImpl();
        gson = new Gson();
    }

    private void validateResult(String jsonArray, int expectedRecordsCount) {
        HackerNews[] newsArray = gson.fromJson(jsonArray, HackerNews[].class);
        Assert.assertEquals(expectedRecordsCount, newsArray.length);
        for (HackerNews news : newsArray) {
            Assert.assertNotNull(news);
            Assert.assertNotNull(news.getAuthor());
            Assert.assertNotNull(news.getTitle());
            Assert.assertNotNull(news.getUri());
            try {
                new URI(news.getUri());
            } catch (URISyntaxException e) {
                Assert.fail(URL_ERROR_MSG);
            }

            Assert.assertTrue(news.getPoints() > 0);
            Assert.assertTrue(news.getRank() > 0);
            Assert.assertTrue(news.getComments() > 0);
        }
    }

    @Test
    public void testGetNews() {
        String jsonArray = reader.getTopRankedNews(1);
        Assert.assertNotNull(jsonArray);
        validateResult(jsonArray, 1);
    }

    @Test(expected = RuntimeException.class)
    public void testCommentsValidation() {
        reader.getTopRankedNews(22);
    }

    @Test(expected = RuntimeException.class)
    public void testInputValidation() {
        reader.getTopRankedNews(-1);
    }

    @Test(expected = RuntimeException.class)
    public void testInputValidationZeroRecords() {
        reader.getTopRankedNews(0);
    }

    @Test(expected = RuntimeException.class)
    public void testInputValidationInvalidURL() {
        reader.readNews("InvalidURL", 9, 1);
    }

    @Test(expected = RuntimeException.class)
    public void testInputValidationInvalidRecords() {
        reader.readNews(HACKER_NEWS_URL, -9, 1);
    }

    @Test(expected = RuntimeException.class)
    public void testInputValidationInvalidPage() {
        reader.readNews(HACKER_NEWS_URL, 9, -1);
    }

    @Test
    public void testReadNews() {
        reader.readNews(HACKER_NEWS_URL, 1, 1);
        validateResult(reader.getSavedNews(), 1);
    }
}
