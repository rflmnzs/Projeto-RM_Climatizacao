package dao;

import conexaosql.Conexao;
import entidades.Servico_Funcionario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Servico_FuncionarioDAO {
   
    public void create(Servico_Funcionario relacao) {

        String sql = "INSERT INTO Servico_Funcionario (id_servico, id_funcionario) "
                   + "VALUES (?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, relacao.getId_servico());
            stmt.setInt(2, relacao.getId_funcionario());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir Servico_Funcionario: " + e.getMessage());
        }
    }
  
    public List<Servico_Funcionario> read() {

        List<Servico_Funcionario> lista = new ArrayList<>();
        String sql = "SELECT * FROM Servico_Funcionario";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Servico_Funcionario relacao = new Servico_Funcionario(
                    rs.getInt("id_servico"),
                    rs.getInt("id_funcionario")
                );

                lista.add(relacao);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar Servico_Funcionario: " + e.getMessage());
        }

        return lista;
    }
    
    public boolean update(Servico_Funcionario relacao) {

        String sql = "UPDATE Servico_Funcionario SET id_funcionario = ? "
                   + "WHERE id_servico = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, relacao.getId_funcionario());
            stmt.setInt(2, relacao.getId_servico());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar Servico_Funcionario: " + e.getMessage());
            return false;
        }
    }
    
    public boolean delete(int idServico) {

        String sql = "DELETE FROM Servico_Funcionario WHERE id_servico = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idServico);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao deletar Servico_Funcionario: " + e.getMessage());
            return false;
        }
    }
}
