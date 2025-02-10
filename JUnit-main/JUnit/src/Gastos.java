import java.sql.ResultSet;

public class Gastos {
    private final Conexion conexion;
    private final String dni;

    public Gastos(Conexion conexion, String dni) {
        this.conexion = conexion;
        this.dni = dni;
    }

    public void registrarGasto(String concepto, double cantidad) {
        conexion.insertarMovimiento(dni, "Gasto", concepto, cantidad);
        System.out.println("Se ha registrado un gasto en " + concepto + " por " + cantidad + " €.");
    }

    public void registrarIngreso(String concepto, double cantidad) {
        conexion.insertarMovimiento(dni, "Ingreso", concepto, cantidad);
        System.out.println("Se ha registrado un ingreso de " + concepto + " por " + cantidad + " €.");
    }

    public void mostrarMovimientos() {
        ResultSet movimientos = conexion.obtenerMovimientos(dni);
        double saldo = 0.0;
        System.out.println("Movimientos de " + dni + ":");
        try {
            while (movimientos != null && movimientos.next()) {
                String tipo = movimientos.getString("tipo");
                String concepto = movimientos.getString("concepto");
                double cantidad = movimientos.getDouble("cantidad");
                saldo += tipo.equals("Ingreso") ? cantidad : -cantidad;
                System.out.println(tipo + " - " + concepto + ": " + cantidad + " €");
            }
        } catch (Exception e) {
            System.out.println("Error al mostrar movimientos: " + e.getMessage());
        }
        System.out.println("Saldo final: " + saldo + " €.");
    }
}
