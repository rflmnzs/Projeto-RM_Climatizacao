package dao;
 
import entidades.Cliente_Maquina;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import conexaosql.Conexao;
 
public class Cliente_MaquinaDAO {
 
    public void create(Cliente_Maquina relacao) {
 
        String sql = "INSERT INTO Cliente_Maquina (id_maquina, id_cliente, local_instalador, data_instalacao) "
                   + "VALUES (?, ?, ?, ?)";
 
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
 
            stmt.setInt(1, relacao.getId_maquina());
            stmt.setInt(2, relacao.getId_cliente());
            stmt.setString(3, relacao.getLocal_instalador());
            stmt.setString(4, relacao.getData_instalacao());
 
            stmt.executeUpdate();
 
        } catch (SQLException e) {
            System.out.println("Erro ao inserir Cliente_Maquina: " + e.getMessage());
        }
    }
    
    public List<Cliente_Maquina> read() {
 
        List<Cliente_Maquina> lista = new ArrayList<>();
        String sql = "SELECT * FROM Cliente_Maquina";
 
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
 
            while (rs.next()) {
 
                Cliente_Maquina relacao = new Cliente_Maquina(
                    rs.getInt("id_maquina"),
                    rs.getInt("id_cliente"),
                    rs.getString("local_instalador"),
                    rs.getString("data_instalacao")
                );
 
                lista.add(relacao);
            }
 
        } catch (SQLException e) {
            System.out.println("Erro ao ler Cliente_Maquina: " + e.getMessage());
        }
 
        return lista;
    }
    
    public boolean update(Cliente_Maquina relacao) {
 
        String sql = "UPDATE Cliente_Maquina SET id_cliente = ?, local_instalador = ?, data_instalacao = ? "
                   + "WHERE id_maquina = ?";
 
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
 
            stmt.setInt(1, relacao.getId_cliente());
            stmt.setString(2, relacao.getLocal_instalador());
            stmt.setString(3, relacao.getData_instalacao());
            stmt.setInt(4, relacao.getId_maquina());
 
            return stmt.executeUpdate() > 0;
 
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar Cliente_Maquina: " + e.getMessage());
            return false;
        }
    }
    
    public boolean delete(int idMaquina) {
 
        String sql = "DELETE FROM Cliente_Maquina WHERE id_maquina = ?";
 
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
 
            stmt.setInt(1, idMaquina);
            return stmt.executeUpdate() > 0;
 
        } catch (SQLException e) {
            System.out.println("Erro ao deletar Cliente_Maquina: " + e.getMessage());
            return false;
        }
        
        
    }
}