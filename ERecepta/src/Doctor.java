public class Doctor extends BaseModel {

    public static Integer create(String firstName, String lastName, String specialization){
        DatabaseConnector databaseConnector = getDatabaseConnector();

        return databaseConnector.create(
                "INSERT INTO DOCTORS (FIRST_NAME,LAST_NAME,SPECIALIZATION) " +
                "VALUES (" + getStringValue(firstName) + "," + getStringValue(lastName) + "," + getStringValue(specialization) +  ");"
        );
    }
}
