package data;

import service.IContactWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ContactDatabase {
    List<Contact> contacts;
    IContactWriter writer;

    public ContactDatabase(IContactWriter writer) {
        this.contacts = new ArrayList<>();
        this.writer = writer;
        importContacts();
    }

    private void importContacts() {

        try {
            Path path = Paths.get("contacts.txt");
            if (Files.exists(path)) {
                List<String> lines = Files.readAllLines(path);
                for (String line : lines) {
                   String [] arr = line.split(",");
                   contacts.add(new Contact(arr[0], arr[1]));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public void removeContact(String name) {
        for (Contact contact : contacts) {
            if(name.equalsIgnoreCase(contact.getName())) {
                contacts.remove(contact);
                String contactLine = contact.getName() + "," + contact.getPhoneNumber();
                writer.deleteContactFromFile(contactLine);
                break;
            }
        }
    }

    public void editContact(Contact contact) {
        for (Contact c : contacts) {
            if(contact.getName().equals(c.getName())) {
                c.setPhoneNumber(contact.getPhoneNumber());
                break;
            }
        }
    }

    public Contact getContact(String name) {
        Contact result = null;
        for (Contact contact : contacts) {
            if(name.equalsIgnoreCase(contact.getName())) {
                result = contact;
                break;
            }
        }
        return result;
    }

    public List<Contact> getAllContacts() {
        return contacts;
    }

    public void writeToFile() {
        writer.writeToFile(contacts);
    }

    public int size() {
        return contacts.size();
    }

}
