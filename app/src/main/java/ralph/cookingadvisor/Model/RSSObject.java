package ralph.cookingadvisor.Model;

import java.util.List;

/**
 * Created by rafaelchris on 21/10/17.
 */

public class RSSObject
{
    public String status;
    public Feed feed;
    public List<Item> items;

    public RSSObject(String status, Feed feed, List<Item> items) {
        this.status = status;
        this.feed = feed;
        this.items = items;
    }

    public String getStatus() {
        return status;
    }

    public Feed getFeed() {
        return feed;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}


