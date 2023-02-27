package service;

import data.Contact;

import java.util.List;

public interface IContactWriter {
    void writeToFile(List<Contact> contacts);

    void deleteContactFromFile(String contact);
}
