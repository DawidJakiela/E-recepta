import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EPrescriptionSystem {

    private DatabaseConnector databaseConnector;
    public EPrescriptionSystem() throws SQLException, ClassNotFoundException {
        databaseConnector = new DatabaseConnector("erecepta");
    }

    public void init(){
        List<String> doctorsColumns = new ArrayList<>();

        doctorsColumns.add("ID INTEGER PRIMARY KEY");
        doctorsColumns.add("FIRST_NAME TEXT NOT NULL");
        doctorsColumns.add("LAST_NAME TEXT NOT NULL");
        doctorsColumns.add("SPECIALIZATION TEXT NOT NULL");

        databaseConnector.createTable("doctors", doctorsColumns);

        List<String> patientsColumns = new ArrayList<>();
        patientsColumns.add("ID INTEGER PRIMARY KEY");
        patientsColumns.add("FIRST_NAME TEXT NOT NULL");
        patientsColumns.add( "LAST_NAME TEXT NOT NULL");
        patientsColumns.add("ADDRESS TEXT NOT NULL");
        patientsColumns.add("PHONE TEXT NOT NULL");

        databaseConnector.createTable("patients", patientsColumns);

        List<String> visitsColumns = new ArrayList<>();
        visitsColumns.add("ID INTEGER PRIMARY KEY");
        visitsColumns.add("DOCTOR_ID INT");
        visitsColumns.add("PATIENT_ID INT");
        visitsColumns.add("DATE TEXT NOT NULL");
        visitsColumns.add("FOREIGN KEY (DOCTOR_ID) REFERENCES doctors (DOCTOR_ID)");
        visitsColumns.add("FOREIGN KEY (PATIENT_ID) REFERENCES patients (PATIENT_ID)");

        databaseConnector.createTable("visits", visitsColumns);

        List<String> prescriptionsColumns = new ArrayList<>();
        prescriptionsColumns.add("ID INTEGER PRIMARY KEY");
        prescriptionsColumns.add("VISIT_ID INT");
        prescriptionsColumns.add("DRUG TEXT NOT NULL");
        prescriptionsColumns.add("DOSAGE TEXT NOT NULL");
        prescriptionsColumns.add("EXPIRATION_DATE TEXT NOT NULL");
        prescriptionsColumns.add("FOREIGN KEY (VISIT_ID) REFERENCES visits (VISIT_ID)");

        databaseConnector.createTable("prescriptions", prescriptionsColumns);
    }

    public void insertData(){
        Doctor.create("Jan", "Kowalski", "Ginekolog");
        Doctor.create("Anna", "Nowak", "Okulista");
        Doctor.create("Maria", "Wisniewska","Chirurg");
        Doctor.create("Andrzej", "Kwiatkowski","Gastrolog");
        Doctor.create("Karol", "Kowalczuk","Urolog");

        Patient.create("Pawel","Czyz","Zapowiedzka 1/10","760398441");
        Patient.create("Adam","Pojnar","Młyńska14/12","345349132");
        Patient.create("Dorota","Wojtasik","Słoneczna21/3","729988648");
        Patient.create("Jacek","Bunczyk","Szkolna6/9","976456066");
        Patient.create("Aleksandra","Kasperkowicz","Jagodowa32/5","691739045");

        Visit.create("3","1","2023-05-29");
        Visit.create("1","2","2023-04-21");
        Visit.create("2","3","2023-06-24");
        Visit.create("4","4","2023-06-02");
        Visit.create("5","5","2023-05-15");

        Prescription.create("1", "Gynalign", "2", "2023-08-23");
        Prescription.create("2", "Paracetamol", "1", "2023-07-23");
        Prescription.create("3", "Maxiluten", "4", "2023-08-24");
        Prescription.create("4", "Accidel", "3", "2023-09-12");
        Prescription.create("5", "Urosept", "5", "2023-10-04");
    }

    public void registerVisit() throws SQLException {
        Scanner scan = new Scanner(System.in);

        System.out.print("Podaj imię: ");
        String firstName = scan.next();

        System.out.print("Podaj nazwisko: ");
        String lastName = scan.next();

        System.out.print("Podaj adres: ");
        String address = scan.next();

        System.out.print("Podaj nr telefonu: ");
        String phone = scan.next();

        Patient patient = Patient.create(firstName, lastName, address, phone);

        CachedRowSet doctorsRowSet = databaseConnector.all("DOCTORS");

        System.out.print("Wybierz specjaliste: \n");

        while (doctorsRowSet.next()){
            System.out.print(doctorsRowSet.getInt("ID") + ". "+ doctorsRowSet.getString("FIRST_NAME") + " " + doctorsRowSet.getString("LAST_NAME") + " " + doctorsRowSet.getString("SPECIALIZATION") + "\n");
        }

        String doctorId = scan.next();

        System.out.print("Podaj date wizyty: \n");
        String date = scan.next();

        Visit.create(doctorId, patient.getId().toString(), date);

        System.out.print("Wizyta została stworzona na dzień: " + date + "\n");
    }

    public void updateVisitDate() {
        try {
            Scanner scan = new Scanner(System.in);
            System.out.print("Podaj id wizyty: ");

            CachedRowSet visitRowSet = databaseConnector.find(scan.nextInt(), "VISITS");

            String oldDate = visitRowSet.getString("DATE");

            System.out.print("Podaj nowy termin dla wizyty z dnia "+ oldDate +": ");
            String date = scan.next();

            Visit.updateDate(visitRowSet.getInt("ID"), date);

            System.out.print("Zmieniono termin na: " + date + "\n");
        } catch ( Exception e ) {
            System.err.println("Nie znaleziono wizyty");
            System.exit(0);
        }
    }

    public void deleteVisit(){
        try {
            Scanner scan = new Scanner(System.in);
            System.out.print("Podaj id wizyty: ");

            CachedRowSet visitRowSet = databaseConnector.find(scan.nextInt(), "VISITS");

            String date = visitRowSet.getString("DATE");
            Visit.destroy(visitRowSet.getInt("ID"));

            System.out.print("Anulowano wizyte z dnia: "+ date +"\n");
        } catch ( Exception e ) {
            System.err.println("Nie znaleziono wizyty");
            System.exit(0);
        }
    }

    public void showPrescriptionDetails()
    {
        try {
            Scanner scan = new Scanner(System.in);
            System.out.print("Podaj id recepty: ");

            CachedRowSet prescriptionRowSet = databaseConnector.find(scan.nextInt(), "PRESCRIPTIONS");

            System.out.print("Leki: "+ prescriptionRowSet.getString("DRUG") + "\n");
            System.out.print("Dawkowanie: "+ prescriptionRowSet.getString("DOSAGE")+ "\n");
            System.out.print("Data wygaśnięcia: "+ prescriptionRowSet.getString("EXPIRATION_DATE")+ "\n");
        } catch ( Exception e ) {
            System.err.println("Nie znaleziono recepty");
            System.exit(0);
        }
    }

    public void check(){
        databaseConnector.test();
    }

    public void showMenu() throws Exception {
        Scanner scanner = new Scanner(System.in);
        int option = 0;
        do {
            System.out.println("1. Rejestracja wizyty");
            System.out.println("2. Zmiana terminu wizyty");
            System.out.println("3. Anulowanie wizyty");
            System.out.println("4. Wyświetlenie recepty");
            System.out.println("5. Wyjście");
            System.out.println("Wprowadź opcję: ");

            option = Integer.parseInt(scanner.nextLine());
            switch (option) {
                case 1:
                    registerVisit();
                    break;
                case 2:
                    updateVisitDate();
                    break;
                case 3:
                    deleteVisit();
                    break;
                case 4:
                    showPrescriptionDetails();
                    break;
            }
        } while (option != 5);
    }
}
