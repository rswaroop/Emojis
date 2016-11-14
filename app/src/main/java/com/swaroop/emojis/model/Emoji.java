package com.swaroop.emojis.model;

/**
 * Created by swaroop.rayudu on 11/9/16.
 */

public class Emoji {
    public final String imageUrl;
    public final String name;

    public Emoji(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }
}
