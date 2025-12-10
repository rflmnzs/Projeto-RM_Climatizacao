package dao;

import conexaosql.Conexao;
import entidades.NotaFiscal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotaFiscalDAO {

   
    public boolean create(NotaFiscal nota) {

        String sql = "INSERT INTO NotaFiscal (ID_Cliente, numNotaFiscal, dataEmissao) " +
                     "VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, nota.getIdCliente());
            stmt.setString(2, nota.getNumNotaFiscal());
            stmt.setDate(3, Date.valueOf(nota.getDataEmissao()));

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao inserir Nota Fiscal: " + e.getMessage());
            return false;
        }
    }


   
    public List<NotaFiscal> read() {

        List<NotaFiscal> lista = new ArrayList<>();

        String sql = "SELECT * FROM NotaFiscal";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                NotaFiscal nota = new NotaFiscal(
                        rs.getInt("ID_NotaFiscal"),
                        0, // ID SERVIÇO REMOVIDO → valor padrão
                        rs.getInt("ID_Cliente"),
                        rs.getString("numNotaFiscal"),
                        rs.getDate("dataEmissao").toLocalDate()
                );

                lista.add(nota);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar Notas Fiscais: " + e.getMessage());
        }

        return lista;
    }


   
    public boolean update(NotaFiscal nota) {

        String sql = "UPDATE NotaFiscal SET ID_Cliente=?, numNotaFiscal=?, dataEmissao=? " +
                     "WHERE ID_NotaFiscal=?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, nota.getIdCliente());
            stmt.setString(2, nota.getNumNotaFiscal());
            stmt.setDate(3, Date.valueOf(nota.getDataEmissao()));
            stmt.setInt(4, nota.getIdNotaFiscal());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar Nota Fiscal: " + e.getMessage());
            return false;
        }
    }


    
    public boolean delete(int idNotaFiscal) {

        String sql = "DELETE FROM NotaFiscal WHERE ID_NotaFiscal=?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idNotaFiscal);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao deletar Nota Fiscal: " + e.getMessage());
            return false;
        }
    }
}
