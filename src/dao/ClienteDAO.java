package dao;

import conexaosql.Conexao;
import entidades.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    
    // aqui estap os formatadores (pra deixar o cpf com . e o contato organizado

    private String formatarCPF(String cpf) {
        if (cpf == null) return "";

        cpf = cpf.replaceAll("[^0-9]", ""); // mantém só números

        if (cpf.length() != 11) return cpf;

        return cpf.substring(0, 3) + "." +
               cpf.substring(3, 6) + "." +
               cpf.substring(6, 9) + "-" +
               cpf.substring(9, 11);
    }

    private String formatarContato(String contato) {
        if (contato == null) return "";

        contato = contato.replaceAll("[^0-9]", ""); // mantém só números

        // pra telefone (11)1234-5678
        if (contato.length() == 10) {
            return "(" + contato.substring(0, 2) + ")" +
                   contato.substring(2, 6) + "-" +
                   contato.substring(6, 10);
        }

        // pra celular (11)91234-5678
        if (contato.length() == 11) {
            return "(" + contato.substring(0, 2) + ")" +
                   contato.substring(2, 7) + "-" +
                   contato.substring(7, 11);
        }

        return contato; 
    }
    
    public void create(Cliente cliente) {
        String sql = "INSERT INTO Cliente (nome, CPF, tipo_contrato, contato, endereco) VALUES (?, ?, ?, ?, ?)";

        String cpfFormatado = formatarCPF(cliente.getCpf());
        String contatoFormatado = formatarContato(cliente.getContato());

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cpfFormatado);
            stmt.setString(3, cliente.getTipo_contrato());
            stmt.setString(4, contatoFormatado);
            stmt.setString(5, cliente.getEndereco());

            stmt.executeUpdate();

            System.out.println("Cliente cadastrado!");
            System.out.println("CPF salvo: " + cpfFormatado);
            System.out.println("Contato salvo: " + contatoFormatado);

        } catch (Exception e) {
            System.err.println("Erro ao inserir cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public List<Cliente> read() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM Cliente";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente c = new Cliente(
                    rs.getInt("ID_Cliente"),
                    rs.getString("nome"),
                    rs.getString("CPF"),     
                    rs.getString("tipo_contrato"),
                    rs.getString("contato"),  
                    rs.getString("endereco")
                );

                lista.add(c);
            }

        } catch (Exception e) {
            System.err.println("Erro ao ler clientes: " + e.getMessage());
        }

        return lista;
    }
    public boolean update(Cliente cliente) {
        String sql = "UPDATE Cliente SET nome = ?, CPF = ?, tipo_contrato = ?, contato = ?, endereco = ? WHERE ID_Cliente = ?";

        String cpfFormatado = formatarCPF(cliente.getCpf());
        String contatoFormatado = formatarContato(cliente.getContato());

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cpfFormatado);
            stmt.setString(3, cliente.getTipo_contrato());
            stmt.setString(4, contatoFormatado);
            stmt.setString(5, cliente.getEndereco());
            stmt.setInt(6, cliente.getId_cliente());

            boolean atualizado = stmt.executeUpdate() > 0;

            if (atualizado) {
                System.out.println("Cliente atualizado!");
                System.out.println("CPF salvo: " + cpfFormatado);
                System.out.println("Contato salvo: " + contatoFormatado);
            }

            return atualizado;

        } catch (Exception e) {
            System.err.println("Erro ao atualizar cliente: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean delete(int id) {
        String sql = "DELETE FROM Cliente WHERE ID_Cliente = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            System.err.println("Erro ao deletar cliente: " + e.getMessage());
            return false;
        }
    }
}
