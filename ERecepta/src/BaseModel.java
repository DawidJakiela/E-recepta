public class BaseModel {
    protected static DatabaseConnector getDatabaseConnector(){
        return new DatabaseConnector("erecepta");
    }

    protected static String getStringValue(String value){
        return "'" + value + "'";
    }
}
