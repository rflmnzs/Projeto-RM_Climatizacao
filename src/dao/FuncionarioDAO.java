package dao;

import conexaosql.Conexao;
import entidades.Funcionario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    
    private String formatarCPF(String cpf) {
        if (cpf == null) return "";

        cpf = cpf.replaceAll("[^0-9]", ""); // mantém só dígitos

        if (cpf.length() != 11) return cpf;

        return cpf.substring(0, 3) + "." +
               cpf.substring(3, 6) + "." +
               cpf.substring(6, 9) + "-" +
               cpf.substring(9, 11);
    }

  
    public void create(Funcionario funcionario) {

        String sql = "INSERT INTO Funcionario (CPF, nome, AreaAtuacao) VALUES (?, ?, ?)";

        String cpfFormatado = formatarCPF(funcionario.getCpf());

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpfFormatado);
            stmt.setString(2, funcionario.getNome());
            stmt.setString(3, funcionario.getAreaAtuacao());

            stmt.executeUpdate();

            System.out.println("Funcionário inserido com CPF formatado: " + cpfFormatado);

        } catch (SQLException e) {
            System.out.println("Erro ao inserir funcionário: " + e.getMessage());
        }
    }

   
    public List<Funcionario> read() {

        List<Funcionario> lista = new ArrayList<>();
        String sql = "SELECT ID_Funcionario, nome, CPF, AreaAtuacao FROM Funcionario";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Funcionario funcionario = new Funcionario(
                    rs.getInt("ID_Funcionario"),
                    rs.getString("CPF"),     // já está formatado no banco
                    rs.getString("nome"),
                    rs.getString("AreaAtuacao")
                );

                lista.add(funcionario);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar funcionários: " + e.getMessage());
        }

        return lista;
    }


    public boolean update(Funcionario funcionario) {

        String sql = "UPDATE Funcionario SET CPF = ?, nome = ?, AreaAtuacao = ? "
                   + "WHERE ID_Funcionario = ?";

        String cpfFormatado = formatarCPF(funcionario.getCpf());

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpfFormatado);
            stmt.setString(2, funcionario.getNome());
            stmt.setString(3, funcionario.getAreaAtuacao());
            stmt.setInt(4, funcionario.getIdFuncionario());

            boolean atualizado = stmt.executeUpdate() > 0;

            if (atualizado) {
                System.out.println("Funcionário atualizado com CPF: " + cpfFormatado);
            }

            return atualizado;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar funcionário: " + e.getMessage());
            return false;
        }
    }


    public boolean delete(int idFuncionario) {

        String sql = "DELETE FROM Funcionario WHERE ID_Funcionario = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idFuncionario);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao deletar funcionário: " + e.getMessage());
            return false;
        }
    }
}
