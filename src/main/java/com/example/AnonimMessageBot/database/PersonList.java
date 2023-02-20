package com.example.AnonimMessageBot.database;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PersonList {
    ArrayList<Person> personList = new ArrayList<>();


    public void add(Person person){
        this.personList.add(person);
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
        if (this.personList == null){
            return false;
        }
        for (Person person : personList) {
            if(person.getId() == chatId){
                return true;
            }
        }
        return false;
    }


}
