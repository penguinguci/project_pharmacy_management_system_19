package connectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    private static Connection con = null;
    private static ConnectDB instance = new ConnectDB();


    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static ConnectDB getInstance() {
        return instance;
    }


    public void connect() {
        if (con == null || isConnectionClosed()) {
            String url = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyNhaThuoc";
            String user = "sa";
            String password = "sa123";
            try {
                con = DriverManager.getConnection(url, user, password);
                System.out.println("Kết nối thành công đến cơ sở dữ liệu.");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Kết nối không thành công đến cơ sở dữ liệu.");
            }
        }
    }


    private boolean isConnectionClosed() {
        try {
            return con == null || con.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }


    public void disconnect() {
        if (con != null && !isConnectionClosed()) {
            try {
                con.close();
                System.out.println("Đóng kết nối thành công.");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Đóng kết nối không thành công.");
            }
        }
    }

    // Phương thức getConnection trả về kết nối hiện tại, nếu chưa có kết nối thì tự động kết nối
    public static Connection getConnection() {
        if (instance.isConnectionClosed()) {
            instance.connect();
        }
        return con;
    }
}
