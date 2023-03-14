import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EPrescriptionSystemTest {

    private DatabaseConnector databaseConnector;
    public EPrescriptionSystemTest() throws SQLException, ClassNotFoundException {
        databaseConnector = new DatabaseConnector("test");
    }

    @Test
    @Order(1)
    void init() throws SQLException {
//        List<String> doctorsColumns = new ArrayList<>();
//
//        doctorsColumns.add("ID INTEGER PRIMARY KEY");
//        doctorsColumns.add("FIRST_NAME TEXT NOT NULL");
//        doctorsColumns.add("LAST_NAME TEXT NOT NULL");
//        doctorsColumns.add("SPECIALIZATION TEXT NOT NULL");
//
//        databaseConnector.createTable("doctors", doctorsColumns);
//
//        List<String> patientsColumns = new ArrayList<>();
//        patientsColumns.add("ID INTEGER PRIMARY KEY");
//        patientsColumns.add("FIRST_NAME TEXT NOT NULL");
//        patientsColumns.add( "LAST_NAME TEXT NOT NULL");
//        patientsColumns.add("ADDRESS TEXT NOT NULL");
//        patientsColumns.add("PHONE TEXT NOT NULL");
//
//        databaseConnector.createTable("patients", patientsColumns);
//
//        List<String> visitsColumns = new ArrayList<>();
//        visitsColumns.add("ID INTEGER PRIMARY KEY");
//        visitsColumns.add("DOCTOR_ID INT");
//        visitsColumns.add("PATIENT_ID INT");
//        visitsColumns.add("DATE TEXT NOT NULL");
//        visitsColumns.add("FOREIGN KEY (DOCTOR_ID) REFERENCES doctors (DOCTOR_ID)");
//        visitsColumns.add("FOREIGN KEY (PATIENT_ID) REFERENCES patients (PATIENT_ID)");
//
//        databaseConnector.createTable("visits", visitsColumns);
//
//        List<String> prescriptionsColumns = new ArrayList<>();
//        prescriptionsColumns.add("ID INTEGER PRIMARY KEY");
//        prescriptionsColumns.add("VISIT_ID INT");
//        prescriptionsColumns.add("DRUG TEXT NOT NULL");
//        prescriptionsColumns.add("DOSAGE TEXT NOT NULL");
//        prescriptionsColumns.add("EXPIRATION_DATE TEXT NOT NULL");
//        prescriptionsColumns.add("FOREIGN KEY (VISIT_ID) REFERENCES visits (VISIT_ID)");
//
//        databaseConnector.createTable("prescriptions", prescriptionsColumns);

        CachedRowSet doctorsRowSet = databaseConnector.all("DOCTORS");

        assertFalse(doctorsRowSet.next());
    }

    @Test
    @Order(2)
    void insertData() throws SQLException {
        Integer id = Doctor.create("Jan", "Kowalski", "Ginekolog");

        assertNotNull(id);
    }

    @Test
    @Order(3)
    void registerVisit() {
        Integer doctorId = Doctor.create("Jan", "Kowalski", "Ginekolog");

        Patient patient = Patient.create("Tomasz", "Nowak", "Warszawa, Kolejowa 12", "669977044");

        Integer visitId = Visit.create(doctorId.toString(), patient.getId().toString(), "2023-04-22");

        assertNotNull(visitId);
    }

    @Test
    @Order(4)
    void deleteVisit() {
        Integer doctorId = Doctor.create("Jan", "Kowalski", "Ginekolog");

        Patient patient = Patient.create("Tomasz", "Nowak", "Warszawa, Kolejowa 12", "669977044");

        Integer visitId = Visit.create(doctorId.toString(), patient.getId().toString(), "2023-04-22");

        Visit.destroy(visitId);

        CachedRowSet visitRowSet = databaseConnector.find(visitId, "VISITS");

        assertNull(visitRowSet);
    }
}