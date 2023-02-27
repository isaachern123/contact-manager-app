package data;

public class Contact {

    private final String name;

    private String phoneNumber;

    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        String fName = name+" ".repeat(20-name.length());
        String fPhoneNumber = "(" + phoneNumber.substring(0,3) + ") " + phoneNumber.substring(3,6) + "-" + phoneNumber.substring(6);
        return String.format("│ %s │ %s │", fName, fPhoneNumber);
    }

    //accessors

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
