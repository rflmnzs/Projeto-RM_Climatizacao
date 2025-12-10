package dao;

import conexaosql.Conexao;
import entidades.Tipo_Servico;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Tipo_ServicoDAO {
    
    public void create(Tipo_Servico tipo) {

        String sql = "INSERT INTO Tipo_Servico (descricao) VALUES (?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tipo.getDescricao());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir Tipo_Servico: " + e.getMessage());
        }
    }
   
    public List<Tipo_Servico> read() {

        List<Tipo_Servico> lista = new ArrayList<>();
        String sql = "SELECT * FROM Tipo_Servico";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Tipo_Servico tipo = new Tipo_Servico(
                    rs.getInt("id_tipoServico"),
                    rs.getString("descricao")
                );

                lista.add(tipo);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar Tipo_Servico: " + e.getMessage());
        }

        return lista;
    }
   
    public boolean update(Tipo_Servico tipo) {

        String sql = "UPDATE Tipo_Servico SET descricao = ? WHERE id_tipoServico = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tipo.getDescricao());
            stmt.setInt(2, tipo.getId_tipoServico());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar Tipo_Servico: " + e.getMessage());
            return false;
        }
    }
    
    public boolean delete(int idTipo) {

        String sql = "DELETE FROM Tipo_Servico WHERE id_tipoServico = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTipo);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao deletar Tipo_Servico: " + e.getMessage());
            return false;
        }
    }
}
