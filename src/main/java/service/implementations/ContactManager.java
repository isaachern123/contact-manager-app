package service.implementations;

import data.Contact;
import data.ContactDatabase;
import org.apache.commons.text.WordUtils;
import service.IManager;
import utility.Input;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static utility.FontColor.*;

public class ContactManager implements IManager {

    private final ContactDatabase db;

    private final Input input;

    public ContactManager(Input input, ContactDatabase db) {
        this.db = db;
        this.input = input;
    }

    @Override
    public void removeContact(String name) {
        if(searchContacts(name) == null){
            System.out.format(renderRed("%n⚠ Contact '%s' not found.%n%n"), WordUtils.capitalizeFully(name));
            return;
        }
        db.removeContact(name);
        System.out.format(renderBlue("%nContact '%s' has been deleted.%n%n"), WordUtils.capitalizeFully(name));
    }

    @Override
    public void printMenu() {
        System.out.format("""
                1 - View contacts.
                2 - Add a new contact.
                3 - Search a contact by name.
                4 - Delete an existing contact.
                5 - Exit%n
                """);
    }

    @Override
    public void run() {
        String title = importTitle();
        System.out.format("""
                    
                    
                    %s
                    
                    """, title);

        while(true){

            printMenu();

            int choice = input.getInteger(1, 5, renderYellow("Please make a numeric selection: "));

            doChoice(choice);

            if (choice == 5){
                System.out.format(renderYellow("%nGoodbye!%n"));
                writeToFile();
                break;
            }

        }

    }

    @Override
    public void doChoice(int choice){
        switch (choice) {
            case 1 -> getContacts();
            case 2 -> addContact();
            case 3 -> searchForContact();
            case 4 -> deleteContact();
        }
    }

    private void deleteContact() {
        String name = input.getString(renderYellow("Who do you want to delete? "));
        removeContact(name);
    }

    private void searchForContact() {
        String name = input.getString(renderYellow("Who are you looking for? "));
        if(searchContacts(name) == null){
            System.out.format(renderRed("%n⚠ Contact '%s' not found.%n%n"), WordUtils.capitalizeFully(name));
            return;
        }
        Contact contact = searchContacts(name);
        StringBuilder sb = new StringBuilder();
        sb.append(getDbHeader())
                .append(contact)
                .append("\n")
                .append("└──────────────────────┴────────────────┘");
        System.out.format(renderBlue("%s%n%n"), sb);
    }

    @Override
    public void addContact() {
        String name = input.getString(renderYellow("Enter the name of the contact: "));
        String phoneNumber = getContactPhoneNumber();
        Contact contact = new Contact(WordUtils.capitalizeFully(name), phoneNumber);
        if (db.getContact(name) != null){
            String prompt = String.format(renderRed("%n⚠ There is already a contact named %s. Would you like to overwrite it? [y/n] "), name);
            if (input.yesNo(prompt)) {
                db.editContact(contact);
                System.out.format(renderBlue("%nContact '%s' has been updated.%n%n"), contact.getName());
                return;
            }
        }
        db.addContact(contact);
        System.out.format(renderBlue("%n'%s' added!%n%n"), contact.getName());
    }

    private String getContactPhoneNumber() {
        String phoneNumber;
        do {
            phoneNumber = input.getString(renderYellow("Enter the 10-digit phone number of the contact [digits only]: "));
        } while (!validatedPhoneNumber(phoneNumber));
        return phoneNumber;
    }

    private boolean validatedPhoneNumber(String phoneNumber) {
        boolean isValid = phoneNumber.length() == 10 && phoneNumber.matches("[0-9]+");
        if (!isValid) {
            System.out.format(renderRed("%n⚠ Invalid phone number. Please try again.%n%n"));
        }
        return isValid;
    }

    @Override
    public void getContacts() {
        if (db.getAllContacts().isEmpty()){
            System.out.format(renderRed("%n⚠ No contacts found%n%n"));
            return;
        }
        List<Contact> contacts = db.getAllContacts();
        StringBuilder sb = new StringBuilder();
        sb.append(getDbHeader());
        for (int i = 0; i < db.size(); i++) {
            sb.append(contacts.get(i)).append("\n");
            if (i < contacts.size()- 1) {
                sb.append("├──────────────────────┼────────────────┤")
                        .append("\n");
            } else {
                sb.append("└──────────────────────┴────────────────┘")
                        .append("\n");
            }
        }
        System.out.println(renderBlue(sb.toString()));
    }

    private String getDbHeader() {
        return """
                
                ┌──────────────────────┬────────────────┐
                │ Name                 │ Phone Number   │
                ├──────────────────────┼────────────────┤
                """;
    }

    @Override
    public Contact searchContacts(String name) {
        return db.getContact(name);
    }

    public void writeToFile(){
        db.writeToFile();
    }

    public String importTitle() {
        StringBuilder sb = new StringBuilder();
        try {
            Path path = Paths.get("src/main/resources/title.txt");
            if (Files.exists(path)) {
                List<String> lines = Files.readAllLines(path);
                for (String line : lines) {
                    sb.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }
}
