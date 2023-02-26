package com.example.AnonimMessageBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AnonimMessageBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnonimMessageBotApplication.class, args);
	}

}


//1) отделить эмодзи от соо
//2) добавить картинки, видео, стикеры
// https://monsterdeveloper.gitbooks.io/writing-telegram-bots-on-java/content/lesson-2.-photobot.html
//3) сделать так, чтобы можн было писать по айди разным пользователям
