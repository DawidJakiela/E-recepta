import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;
import java.util.*;

public class DatabaseConnector {
    private String databaseName;

    public DatabaseConnector(String databaseName){
        this.databaseName = databaseName;
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Connection connection;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + this.databaseName +".db");
        } catch (Exception e) {
            throw e;
        }

        return connection;
    }

    public void createTable(String name, List<String> columns) {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();

            String query = "CREATE TABLE " + name + "(";

            Iterator<String> columnsIterator = columns.iterator();

            while (columnsIterator.hasNext()) {
                query += columnsIterator.next();


                if (columnsIterator.hasNext()) {
                    query += ",";
                }
            }

            query += ")";

            System.out.println("SQL: \n" + query);


            statement.executeUpdate(query);
            statement.close();



        }catch(Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        System.out.println("Table: " + name + " created successfully");

    }

    public Integer create(String query){
        Integer id = null;

        try {
            Connection connection = getConnection();
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();

            id = statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            connection.commit();
            connection.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return id;
    }

    public void update(String query){
        try {
            Connection connection = getConnection();
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();

            statement.executeUpdate(query);
            connection.commit();
            connection.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

    public CachedRowSet find(Integer id, String tableName){
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.setMaxRows(1);

            RowSetFactory factory = RowSetProvider.newFactory();
            CachedRowSet rowSet = factory.createCachedRowSet();

            ResultSet result = statement.executeQuery("SELECT * FROM " + tableName + " WHERE ID=" + id);

            rowSet.populate(result);

            connection.close();

            if(rowSet.next()){
                return rowSet;
            }
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return null;
    }

    public CachedRowSet all(String tableName){
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();

            // Żeby zwrócić wiersze z danej tabeli po zamknięciu połączenia z bazą danych
            // konieczne jest zwrócenie instancji klasy CachedRowSet zamiast ResultSet
            RowSetFactory factory = RowSetProvider.newFactory();
            CachedRowSet rowSet = factory.createCachedRowSet();

            ResultSet result = statement.executeQuery("SELECT * FROM " + tableName);

            rowSet.populate(result);

            connection.close();

            return rowSet;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        return null;
    }

    public void test(){
        try {
            Connection connection = getConnection();

            Statement statement = connection.createStatement();

//            String query = "SELECT * FROM doctors;" ;
//
//            ResultSet rs = statement.executeQuery( query);
//
//            while ( rs.next() ) {
//                int id = rs.getInt("ID");
//                String firstName = rs.getString("FIRST_NAME");
//                String lastName = rs.getString("LAST_NAME");
//                String specialization = rs.getString("SPECIALIZATION");
//
//
//                System.out.println( "ID = " + id );
//                System.out.println( "firstName = " + firstName );
//                System.out.println( "lastName = " + lastName );
//                System.out.println( "specialization = " + specialization );
//                System.out.println();
//            }

//            String query = "SELECT * FROM patients;" ;
//
//            ResultSet rs = statement.executeQuery( query);
//
//            while ( rs.next() ) {
//                int id = rs.getInt("ID");
//                String firstName = rs.getString("FIRST_NAME");
//                String lastName = rs.getString("LAST_NAME");
//
//
//                System.out.println( "ID = " + id );
//                System.out.println( "firstName = " + firstName );
//                System.out.println( "lastName = " + lastName );
//                System.out.println();
//            }

//            String query = "SELECT * FROM visits;" ;
//
//            ResultSet rs = statement.executeQuery( query);
//
//            while ( rs.next() ) {
//                int id = rs.getInt("ID");
//                String date = rs.getString("DATE");
//                String doctorId = rs.getString("DOCTOR_ID");
//                String patientId = rs.getString("PATIENT_ID");
//
//
//                System.out.println( "ID = " + id );
//                System.out.println( "date = " + date );
//                System.out.println( "doctorId = " + doctorId );
//                System.out.println( "patientId = " + patientId );
//                System.out.println();
//            }


            connection.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }

}
