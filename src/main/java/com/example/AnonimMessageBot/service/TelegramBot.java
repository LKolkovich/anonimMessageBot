package com.example.AnonimMessageBot.service;

import com.example.AnonimMessageBot.config.BotConfig;
import com.example.AnonimMessageBot.database.Person;
import com.example.AnonimMessageBot.database.PersonList;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot{

    final BotConfig config;
    PersonList personList = new PersonList();

    public TelegramBot(BotConfig config){
        this.config = config;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            switch (messageText){
                case "/start":
                    StartCommandReceived(chatId);
                    break;
                default:
                    if(!personList.isPersonIn(chatId)) {
                        Person newPerson = new Person(chatId);
                        personList.add(newPerson);
                        System.out.println("added user with id" + newPerson.getId());
                    }
                    String emojiNick = personList.getPersonById(chatId).getEmojiName();
                    //sendMessage(1094278171, messageText); // ксюшин id
                    sendMessageFromUser(1049967177, emojiNick,  messageText); // мой id
            }

        }
    }

    private void sendMessageFromUser(long chatId, String emojiNick, String messageText){
        String preMessage = "новое сообщение от пользователя " + emojiNick + " :";
        sendMessage(chatId, preMessage);
        sendMessage(chatId, messageText);
    }

    private void StartCommandReceived(long chatId){
        String answer = "Привет, этот бот отправляет анонимные сообщения";
        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textToSend){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        try {
            execute(message);
        }
        catch (TelegramApiException e){
            System.out.println("exception");
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken(){
        return config.getToken();
    }

}
