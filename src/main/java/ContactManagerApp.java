import data.ContactDatabase;
import utility.Input;
import service.IManager;
import service.implementations.ContactManager;
import service.implementations.ContactWriter;
import service.IContactWriter;

public class ContactManagerApp {

    public static void main(String[] args) {
        try(Input input = new Input()) {
            IContactWriter writer = new ContactWriter();
            ContactDatabase db = new ContactDatabase(writer);
            IManager manager = new ContactManager(input, db);
            manager.run();
        }
    }
}
