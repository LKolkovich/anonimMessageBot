package com.example.AnonimMessageBot.service;

import com.example.AnonimMessageBot.config.BotConfig;
import com.example.AnonimMessageBot.database.Person;
import com.example.AnonimMessageBot.database.PersonList;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;
    PersonList personList = new PersonList();
    long lastMessageId = 123;
    int ignoreNextUpdates = 1;

    public TelegramBot(BotConfig config){
        this.config = config;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(ignoreNextUpdates == 1) {
            if (update.hasMessage() && (update.getMessage().hasText() || update.getMessage().hasPhoto())) {
                String messageText = update.getMessage().getText();
                long chatId = update.getMessage().getChatId();
                long recieverId = 1049967177;
                if (!personList.isPersonIn(chatId)) {
                    Person newPerson = new Person(chatId);
                    personList.add(newPerson);
                    System.out.println("added user with id " + newPerson.getId());
                }
                if (chatId != lastMessageId) {
                    SendPreMessage(recieverId, personList.getPersonById(chatId).getEmojiName());
                    lastMessageId = chatId;
                }
                if (messageText == "/start") {
                    StartCommandReceived(chatId);
                } else if (update.getMessage().hasPhoto()) {
                    ignoreNextUpdates = sendPhotoWithText(recieverId, update);
                } else {
                    String emojiNick = personList.getPersonById(chatId).getEmojiName();
                    //sendMessage(1094278171, messageText); // ксюшин id
                    sendMessageFromUser(1049967177, messageText); // мой id
                    SendMediaGroup mediaGroup = new SendMediaGroup();
                }
            }
        }
        else{
            ignoreNextUpdates--;
        }
    }



    private void SendPreMessage(long chatId, String emojiNick){
        String preMessage = "новое сообщение от пользователя " + emojiNick + " :";
        sendMessage(chatId, preMessage);
    }

    private int sendPhotoWithText(long chatId, Update update){
//        SendPhoto msg = new SendPhoto();
//
//        String text = update.getMessage().getCaption();
//        InputFile photo = new InputFile(update.getMessage().getPhoto().get(0).getFileId());
//
//        msg.setPhoto(photo);
//        msg.setChatId(chatId);
//        msg.setCaption(text);
//        System.out.println(update.getMessage().getText());
//
//        InputFile photo2 = new InputFile(update.getMessage().getPhoto().get(1).getFileId());
//        SendPhoto msg2 = new SendPhoto();
//        msg.setPhoto(photo2);
//        msg.setChatId(chatId);


        List<InputMedia> media = new ArrayList<>();
        List<PhotoSize> photoList = update.getMessage().getPhoto();

        for(int i = 0; i < photoList.size(); i++){
            media.add(new InputMediaPhoto(photoList.get(i).getFileId()));
        }



        SendMediaGroup sendMediaGroup = new SendMediaGroup(String.valueOf(chatId), media);

        SendMessage message = new SendMessage();
        message.setText(update.getMessage().getCaption());
        message.setChatId(chatId);
        media.add(new InputMediaPhoto(photoList.get(5).getFileId()));

        try {
            execute(sendMediaGroup);
            execute(message);
            System.out.println("message with photo successfully send");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return photoList.size();
    }

    private void sendMessageFromUser(long chatId, String messageText){
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
            System.out.println("message successfully send");
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
