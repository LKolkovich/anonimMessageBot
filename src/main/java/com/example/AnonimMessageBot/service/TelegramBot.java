package com.example.AnonimMessageBot.service;

import com.example.AnonimMessageBot.config.BotConfig;
import com.example.AnonimMessageBot.database.Person;
import com.example.AnonimMessageBot.database.PersonList;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot{

    final BotConfig config;
    PersonList personList;

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
                    StartCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                default:
                    if(!personList.isPersonIn(chatId)){
                        Person newPerson = new Person(chatId);
                        personList.add(newPerson);
                    }
                    messageText = personList.getPersonById(chatId).getEmojiName() + "\n" + messageText;
                    sendMessage(1049967177, messageText);
            }

        }
    }

    private void StartCommandReceived(long chatId, String userName){
        String answer = "Привет, " + userName;
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
