package az.sacramento.database;

public class SqlQuery {

    public static final String INSERT_CURRENCY_TABLE = "insert into currency(currency_id, type, code, nominal, name, value) " +
            "values(CURRENCY_SEQ.nextval, ?, ?, ?, ?, ?)";
}
