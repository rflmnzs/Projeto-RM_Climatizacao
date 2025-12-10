package dao;

import conexaosql.Conexao;
import entidades.Tabela_Servico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Tabela_ServicoDAO {

    // CREATE
    public void create(Tabela_Servico t) {

    String sql = "INSERT INTO TabelaServico " +
            "(ID_Funcionario, ID_Cliente, servico, prazo_servico, preco_servico, descricao) " +
            "VALUES (?, ?, ?, ?, ?, ?)";

    try (Connection conn = Conexao.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, t.getIdFuncionario());
        stmt.setInt(2, t.getIdCliente());
        stmt.setString(3, t.getServico());
        stmt.setDate(4, Date.valueOf(t.getPrazoServico()));
        stmt.setDouble(5, t.getPrecoServico());
        stmt.setString(6, t.getDescricao());

        stmt.executeUpdate();

    } catch (SQLException e) {
        System.out.println("Erro ao inserir TabelaServico: " + e.getMessage());
    }
}


    // READ
    public List<Tabela_Servico> read() {

        List<Tabela_Servico> lista = new ArrayList<>();
        String sql = "SELECT * FROM TabelaServico ORDER BY ID_TabelaServico";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Tabela_Servico t = new Tabela_Servico(
                        rs.getInt("ID_TabelaServico"),
                        rs.getInt("ID_Funcionario"),
                        rs.getInt("ID_Cliente"),
                        rs.getString("servico"),
                        rs.getDate("prazo_servico").toLocalDate(),
                        rs.getDouble("preco_servico"),
                        rs.getString("descricao")
                );

                lista.add(t);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar TabelaServico: " + e.getMessage());
        }

        return lista;
    }

    // UPDATE
    public boolean update(Tabela_Servico t) {

        String sql = "UPDATE TabelaServico SET " +
                "ID_Funcionario=?, ID_Cliente=?, servico=?, prazo_servico=?, preco_servico=?, descricao=? " +
                "WHERE ID_TabelaServico=?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, t.getIdFuncionario());
            stmt.setInt(2, t.getIdCliente());
            stmt.setString(3, t.getServico());
            stmt.setDate(4, Date.valueOf(t.getPrazoServico()));
            stmt.setDouble(5, t.getPrecoServico());
            stmt.setString(6, t.getDescricao());
            stmt.setInt(7, t.getIdTabelaServico());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar TabelaServico: " + e.getMessage());
            return false;
        }
    }

    // DELETE
    public boolean delete(int idTabelaServico) {

        String sql = "DELETE FROM TabelaServico WHERE ID_TabelaServico=?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTabelaServico);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao deletar TabelaServico: " + e.getMessage());
            return false;
        }
    }
}
