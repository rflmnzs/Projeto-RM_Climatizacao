package conexaosql;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/RM_Climatizacao";
    private static final String USUARIO = "root";
    private static final String SENHA = "1234";

    public static Connection conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
            System.out.println("Conectado ao banco!");
            return conn;
        } catch (Exception e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
            return null;
        }
    }
}
