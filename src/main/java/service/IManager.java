package service;

import data.Contact;

public interface IManager {
    void run();
    void printMenu();
    void addContact();
    void getContacts();
    Contact searchContacts(String name);
    void removeContact(String name);
    void doChoice(int choice);
    void writeToFile();
}
