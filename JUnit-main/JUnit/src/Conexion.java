import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Conexion {
    private final String url = "jdbc:sqlite:gastos.db";
    private Connection conexion;

    public Conexion() {
        conectar();
        crearTablas();
    }

    private void conectar() {
        try {
            conexion = DriverManager.getConnection(url);
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (Exception e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }

    private void crearTablas() {
        String sqlUsuarios = "CREATE TABLE IF NOT EXISTS usuarios (dni TEXT PRIMARY KEY);";
        String sqlMovimientos = """
            CREATE TABLE IF NOT EXISTS movimientos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                dni TEXT,
                tipo TEXT,
                concepto TEXT,
                cantidad REAL,
                FOREIGN KEY (dni) REFERENCES usuarios(dni)
            );
        """;

        try (Statement stmt = conexion.createStatement()) {
            stmt.execute(sqlUsuarios);
            stmt.execute(sqlMovimientos);
        } catch (Exception e) {
            System.out.println("Error al crear tablas: " + e.getMessage());
        }
    }

    public boolean insertarUsuario(String dni) {
        String sql = "INSERT INTO usuarios (dni) VALUES (?)";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, dni);
            pstmt.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al insertar usuario: " + e.getMessage());
            return false;
        }
    }

    public void insertarMovimiento(String dni, String tipo, String concepto, double cantidad) {
        String sql = "INSERT INTO movimientos (dni, tipo, concepto, cantidad) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, dni);
            pstmt.setString(2, tipo);
            pstmt.setString(3, concepto);
            pstmt.setDouble(4, cantidad);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error al insertar movimiento: " + e.getMessage());
        }
    }

    public ResultSet obtenerMovimientos(String dni) {
        String sql = "SELECT tipo, concepto, cantidad FROM movimientos WHERE dni = ?";
        try {
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setString(1, dni);
            return pstmt.executeQuery();
        } catch (Exception e) {
            System.out.println("Error al obtener movimientos: " + e.getMessage());
            return null;
        }
    }
}
