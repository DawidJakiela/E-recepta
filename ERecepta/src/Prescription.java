public class Prescription extends BaseModel {
    public static void create(String visitId, String drug, String dosage, String expirationDate){
        DatabaseConnector databaseConnector = getDatabaseConnector();

        databaseConnector.create(
                "INSERT INTO  PRESCRIPTIONS (VISIT_ID,DRUG,DOSAGE,EXPIRATION_DATE) " +
                "VALUES (" + getStringValue(visitId) + "," + getStringValue(drug) + "," + getStringValue(dosage) + "," + getStringValue(expirationDate) +  ");"
        );
    }
}