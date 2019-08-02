package az.sacramento.database;

import az.sacramento.domain.Currency;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Properties;
public class Database {

    public static Connection connect() {
        Connection connection;
        try {
            Properties config = new Properties();
            config.load(new FileReader("DataBase.properties"));

            Class.forName(config.getProperty("driver"));
            connection = DriverManager.getConnection(config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password"));

            connection.setAutoCommit(false);

        } catch (IOException | ClassNotFoundException | SQLException e) {
            String ErrorMessage = "Error occurred when connected to database: ";
            throw new RuntimeException(ErrorMessage, e);
        }
        return connection;
    }

    public static void close(ResultSet rs, PreparedStatement ps, Connection connection) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (connection != null) {
                connection.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*"insert into currency(currency_id, type, code, nominal, name, value) " +
            "values(CURRENCY_SEQ.nextval, ?, ?, ?, ?, ?)";*/

    public void insertCurrency (List<Currency> currencyList) {
        System.out.println("insertCurrency started: ");
        Connection connection = connect();
        PreparedStatement ps = null;

        for (int i = 0; i < currencyList.size(); i++) {
            Currency currency = currencyList.get(i);
            try {
                ps = connection.prepareStatement(SqlQuery.INSERT_CURRENCY_TABLE);
                ps.setString(1, currency.getCurrencyType());
                ps.setString(2, currency.getCurrencyCode());
                ps.setString(3, currency.getCurrencyNominal());
                ps.setString(4, currency.getCurrencyName());
                ps.setBigDecimal(5, currency.getCurrencyValue());

                int count = ps.executeUpdate();
                System.out.println(count + " currency is added");
                connection.commit();

            } catch (Exception e) {
                String ErrorMessage = "Error occurred when currency inserted: " + currency;
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                throw new RuntimeException(ErrorMessage, e);
            }
        }

        close(null, ps, connection);
        System.out.println("insetCurrency ended");
    }

}
