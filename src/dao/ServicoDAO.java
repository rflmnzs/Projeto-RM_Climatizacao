
package dao;

import conexaosql.Conexao;
import entidades.Servico;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicoDAO {
    
    public void create(Servico servico) {
        String sql = "INSERT INTO Servico (cliente_id, data_solicitacao, descricao, data_conclusao, valor_total) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, servico.getCliente_id());
            stmt.setString(2, servico.getData_solicitacao());
            stmt.setString(3, servico.getDescricao());
            stmt.setString(4, servico.getData_conclusao());
            stmt.setDouble(5, servico.getValor_total());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao inserir Serviço: " + e.getMessage());
        }
    }
 
    public List<Servico> read() {
        List<Servico> lista = new ArrayList<>();
        String sql = "SELECT * FROM Servico";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Servico servico = new Servico(
                    rs.getInt("id_servico"),
                    rs.getInt("cliente_id"),
                    rs.getString("data_solicitacao"),
                    rs.getString("descricao"),
                    rs.getString("data_conclusao"),
                    rs.getDouble("valor_total")
                );
                lista.add(servico);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar Serviços: " + e.getMessage());
        }
        return lista;
    }
    
    public boolean update(Servico servico) {
        String sql = "UPDATE Servico SET cliente_id = ?, data_solicitacao = ?, descricao = ?, data_conclusao = ?, valor_total = ? WHERE id_servico = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, servico.getCliente_id());
            stmt.setString(2, servico.getData_solicitacao());
            stmt.setString(3, servico.getDescricao());
            stmt.setString(4, servico.getData_conclusao());
            stmt.setDouble(5, servico.getValor_total());
            stmt.setInt(6, servico.getId_servico());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar Serviço: " + e.getMessage());
            return false;
        }
    }
    
    public boolean delete(int idServico) {
        String sql = "DELETE FROM Servico WHERE id_servico = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idServico);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar Serviço: " + e.getMessage());
            return false;
        }
    }
}
