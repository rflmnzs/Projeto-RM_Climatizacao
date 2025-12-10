package dao;

import conexaosql.Conexao;
import entidades.Maquina;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaquinaDAO {

    
    public boolean create(Maquina m) {
        String sql = "INSERT INTO Maquina (Tipo_Maquina, Modelo) VALUES (?, ?)";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, m.getTipo_maquina());
            stmt.setString(2, m.getModelo());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Erro ao inserir máquina: " + e.getMessage());
            return false;
        }
    }

   
    public List<Maquina> read() {
        List<Maquina> lista = new ArrayList<>();
        String sql = "SELECT * FROM Maquina";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Maquina(
                        rs.getInt("ID_Maquina"),
                        rs.getString("Tipo_Maquina"),
                        rs.getString("Modelo")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar máquinas: " + e.getMessage());
        }
        return lista;
    }

  
    public boolean update(Maquina m) {
        String sql = "UPDATE Maquina SET Tipo_Maquina=?, Modelo=? WHERE ID_Maquina=?";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, m.getTipo_maquina());
            stmt.setString(2, m.getModelo());
            stmt.setInt(3, m.getId_maquina());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar máquina: " + e.getMessage());
            return false;
        }
    }

    
    public boolean delete(int id) {
        String sql = "DELETE FROM Maquina WHERE ID_Maquina=?";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Erro ao deletar máquina: " + e.getMessage());
            return false;
        }
    }
}
