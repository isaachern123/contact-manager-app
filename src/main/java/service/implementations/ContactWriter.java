package service.implementations;

import data.Contact;
import service.IContactWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;

public class ContactWriter implements IContactWriter {
    public static final String CONTACTS_TXT = "contacts.txt";

    Path path;
    public ContactWriter() {
        path = Paths.get(CONTACTS_TXT);
    }

    @Override
    public void writeToFile(List<Contact> contacts) {
        deleteContactsFile();
        if (contacts.size() > 0) {
            try {
                Files.writeString(path, getStringToWrite(contacts), StandardOpenOption.CREATE);
            } catch (IOException e) {
                System.err.format("Error writing contacts to file: %s%n", e.getMessage());
            }
        }
    }

    private String getStringToWrite(List<Contact> contacts) {
        return contacts.stream()
                .map(c -> String.join(",", List.of(c.getName(),c.getPhoneNumber())) + "\n")
                .collect(Collectors.joining());
    }

    private void deleteContactsFile() {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //File line in file and delete it
    public void deleteContactFromFile(String contact) {
        try {
            List<String> lines = Files.readAllLines(path);
            lines.remove(contact);
            Files.write(path, lines);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
