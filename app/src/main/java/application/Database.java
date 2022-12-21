package application;

import java.sql.*;

public class Database {

    private static final String url = "jdbc:h2:./app/database/carsharing";

    public static Connection getConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return connection;
    }

    public static void closeConnection(Connection conn) throws SQLException {
        conn.close();
    }

    public static void closePreparedStatement(PreparedStatement ps) throws SQLException {
        ps.close();
    }

    public static void closeResultSet(ResultSet rs) throws SQLException {
        rs.close();
    }

    public static void closeStatement(Statement state) throws SQLException {
        state.close();
    }


}
