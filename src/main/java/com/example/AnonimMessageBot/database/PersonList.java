package com.example.AnonimMessageBot.database;


import java.util.ArrayList;

public class PersonList {
    ArrayList<Person> personList = new ArrayList<>();


    public void add(Person person){
        personList.add(person);
    }

    public Person getPersonById(long chatId){
        for (Person person: personList) {
            if(person.getId() == chatId){
                return person;
            }
        }
        return null;
    }

    public boolean isPersonIn(long chatId){
        for (Person person : personList) {
            if(person.getId() == chatId){
                return true;
            }
        }
        return false;
    }
}
