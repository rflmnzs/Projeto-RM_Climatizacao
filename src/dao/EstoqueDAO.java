package dao;

import conexaosql.Conexao;
import entidades.Estoque;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstoqueDAO {

    public void create(Estoque estoque) {

        String sql = "INSERT INTO Estoque (quantidade, id_maquina) VALUES (?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, estoque.getQuantidade());
            stmt.setInt(2, estoque.getId_maquina());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir estoque: " + e.getMessage());
        }
    }
    
    public List<Estoque> read() {

        List<Estoque> lista = new ArrayList<>();
        String sql = "SELECT * FROM Estoque";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Estoque est = new Estoque(
                    rs.getInt("id_estoque"),
                    rs.getInt("quantidade"),
                    rs.getInt("id_maquina")
                );

                lista.add(est);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar estoque: " + e.getMessage());
        }

        return lista;
    }
    
    public boolean update(Estoque estoque) {

        String sql = "UPDATE Estoque SET quantidade = ?, id_maquina = ? "
                   + "WHERE id_estoque = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, estoque.getQuantidade());
            stmt.setInt(2, estoque.getId_maquina());
            stmt.setInt(3, estoque.getId_estoque());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar estoque: " + e.getMessage());
            return false;
        }
    }
    
    public boolean delete(int idEstoque) {

        String sql = "DELETE FROM Estoque WHERE id_estoque = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEstoque);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao deletar estoque: " + e.getMessage());
            return false;
        }
    }
}
