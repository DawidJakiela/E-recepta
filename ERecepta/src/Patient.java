public class Patient extends BaseModel {

    private Integer id;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public Patient(Integer id, String firstName, String lastName, String address, String phone){
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.address = address;
            this.phone = phone;
    }

    public static Patient create(String firstName, String lastName, String address, String phone){
        DatabaseConnector databaseConnector = getDatabaseConnector();

        Integer id = databaseConnector.create(
                "INSERT INTO PATIENTS (FIRST_NAME,LAST_NAME,ADDRESS,PHONE) " +
                        "VALUES (" + getStringValue(firstName) + "," + getStringValue(lastName) + "," + getStringValue(address) + "," + getStringValue(phone) + ");"
        );

        return new Patient(id, firstName, lastName, address, phone);
    }
}
