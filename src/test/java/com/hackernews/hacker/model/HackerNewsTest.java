package com.hackernews.hacker.model;

import org.junit.Assert;
import org.junit.Test;

public class HackerNewsTest {

    @Test
    public void testAllArgumentConstructor(){
        HackerNews testA = new HackerNews("title","uri","author",2,20,1);
        HackerNews testB = new HackerNews("title","uri","author",2,20,1);

        Assert.assertEquals(testA.getComments(), testB.getComments());
        Assert.assertEquals(testA.getPoints(), testB.getPoints());
        Assert.assertEquals(testA.getUri(), testB.getUri());
        Assert.assertEquals(testA.getRank(), testB.getRank());
        Assert.assertEquals(testA.getTitle(), testB.getTitle());
        Assert.assertEquals(testA.getAuthor(), testB.getAuthor());

        Assert.assertEquals(testA.toString(), testB.toString());

        testA.setUri("test");
        testA.setComments(5);
        testA.setAuthor("test");
        testA.setPoints(4);
        testA.setTitle("test");
        testA.setRank(2);
        Assert.assertEquals("test", testA.getUri());
        Assert.assertEquals("test", testA.getAuthor());
        Assert.assertEquals("test", testA.getTitle());
        Assert.assertEquals(5, testA.getComments());
        Assert.assertEquals(4, testA.getPoints());
        Assert.assertEquals(2, testA.getRank());

        Assert.assertNotEquals(testA.toString(), testB.toString());
    }
}
