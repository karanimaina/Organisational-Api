package models;

import org.junit.Test;

import static org.junit.Assert.*;

public class NewsTest {
    @Test
    public void News_NewsInstantiatesCorrectly() {
    News news = setupNews();
    assertTrue(news instanceof News);
    }

    @Test
    public void  News_getTitle() {
        News news = setupNews();
        assertEquals("Position advert",news.getTitle());
    }

    private News setupNews (){
        return new News("Position advert","new intern required",1);
    }

}