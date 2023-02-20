package com.example.AnonimMessageBot.database;

import java.util.Random;

public class Person {

    public Person(long id) {
        this.id = id;
        this.emojiName = generateEmoji();
    }

    public long getId() {
        return id;
    }

    public String getEmojiName() {
        return emojiName;
    }

    private String generateEmoji() {
        String name = "";
        int[] codes = {126976, 126976, 126976};
        Random rand = new Random();
        final int difBetweenMaxAndMin = 492;
        for(int i = 0; i < 3; i++){
            codes[i] = codes[i] + rand.nextInt(difBetweenMaxAndMin) + 769;

        }
        String emoji = new String(codes , 0 , codes.length);
        return emoji;
    }


    // from	U+1F301 to U+1F4EE
    // from U+1F + "769" to U+1F + "1262"
    final private long id;
    final private String emojiName;


}
