public class Visit extends BaseModel {
    public static Integer create(String doctorId, String patientId, String date){
        DatabaseConnector databaseConnector = getDatabaseConnector();

        return databaseConnector.create(
                "INSERT INTO VISITS (DOCTOR_ID,PATIENT_ID,DATE) " +
                        "VALUES (" + getStringValue(doctorId) + "," + getStringValue(patientId) + "," + getStringValue(date) + ");"
        );
    }

    public static void updateDate(Integer id, String date){
        DatabaseConnector databaseConnector = getDatabaseConnector();

        databaseConnector.update(
                "UPDATE VISITS SET DATE=" + getStringValue(date) + " WHERE ID=" + id
        );
    }

    public static void destroy(Integer id){
        DatabaseConnector databaseConnector = getDatabaseConnector();

        databaseConnector.update("DELETE FROM VISITS WHERE ID=" + id);
    }
}
