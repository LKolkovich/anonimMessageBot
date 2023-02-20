package com.example.AnonimMessageBot.database;

public class Person {

    public Person(long id) {
        this.id = id;
        this.emojiName = getEmojiName();
    }

    public long getId() {
        return id;
    }

    public String getEmojiName() {
        return emojiName;
    }

    private String generateEmoji(String emojiName) {

        return null;
    }

    final private long id;
    final private String emojiName;


}
