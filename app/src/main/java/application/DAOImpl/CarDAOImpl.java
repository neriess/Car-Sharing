package application.DAOImpl;

import application.DAO.CarDAO;
import application.Database;
import application.Models.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDAOImpl implements CarDAO {

    @Override
    public Car get(int id) {
        Car car = null;
        String sql = "SELECT id, name, company_id, is_rented FROM CAR WHERE id = ?";

        try (Connection conn = Database.getConnection()) {

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int carId = rs.getInt("id");
                String name = rs.getString("name");
                int companyId = rs.getInt("company_id");
                boolean isRented = rs.getBoolean("is_rented");
                car = new Car(carId, name, companyId, isRented);
            }

            Database.closePreparedStatement(ps);
            Database.closeConnection(conn);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return car;
    }

    @Override
    public List<Car> getAll() {
        return null;
    }

    @Override
    public List<Car> getAllById(int companyId, boolean printAlsoRented) {

        String sql = printAlsoRented ? "SELECT id, name, is_rented FROM CAR WHERE company_id = ?" :
                "SELECT id, name, is_rented FROM CAR WHERE company_id = ? AND is_rented = FALSE";
        List<Car> list = new ArrayList<>();

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, companyId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                boolean isRented = rs.getBoolean("is_rented");
                list.add(new Car(id, name, companyId, isRented));
            }

            Database.closeStatement(ps);
            Database.closeResultSet(rs);
            Database.closeConnection(conn);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS CAR" +
                "(id INTEGER AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR UNIQUE NOT NULL," +
                "company_id INTEGER NOT NULL," +
                "is_rented BOOLEAN," +
                "FOREIGN KEY (company_id) REFERENCES COMPANY(id))";

        try (Connection conn = Database.getConnection()) {
            Statement statement = conn.createStatement();

            statement.executeUpdate(sql);

            Database.closeStatement(statement);
            Database.closeConnection(conn);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void insert(Car car) {
        String sql = "INSERT INTO CAR(name, company_id, is_rented) VALUES(?,?,?)";

        try (Connection conn = Database.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, car.getName());
            ps.setInt(2, car.getCompanyId());
            ps.setBoolean(3, car.isRented());
            ps.executeUpdate();

            Database.closePreparedStatement(ps);
            Database.closeConnection(conn);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Car car, boolean isRented) {
        String sql = "UPDATE CAR SET is_rented = ? WHERE id = ?";

        try (Connection conn = Database.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setBoolean(1, isRented);
            ps.setInt(2, car.getId());
            ps.executeUpdate();

            Database.closePreparedStatement(ps);
            Database.closeConnection(conn);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
