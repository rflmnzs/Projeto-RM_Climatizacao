package entidades;

import java.time.LocalDate;

public class Tabela_Servico {

    private int idTabelaServico;
    private int idFuncionario;
    private int idCliente;
    private String servico;
    private LocalDate prazoServico;
    private double precoServico;
    private String descricao;

    public Tabela_Servico(int idTabelaServico, int idFuncionario, int idCliente,
                          String servico, LocalDate prazoServico, double precoServico, String descricao) {

        this.idTabelaServico = idTabelaServico;
        this.idFuncionario = idFuncionario;
        this.idCliente = idCliente;
        this.servico = servico;
        this.prazoServico = prazoServico;
        this.precoServico = precoServico;
        this.descricao = descricao;
    }

    // Construtor sem ID (para INSERT)
    public Tabela_Servico(int idFuncionario, int idCliente,
                          String servico, LocalDate prazoServico, double precoServico, String descricao) {

        this.idFuncionario = idFuncionario;
        this.idCliente = idCliente;
        this.servico = servico;
        this.prazoServico = prazoServico;
        this.precoServico = precoServico;
        this.descricao = descricao;
    }

    public int getIdTabelaServico() {
        return idTabelaServico;
    }

    public void setIdTabelaServico(int idTabelaServico) {
        this.idTabelaServico = idTabelaServico;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public LocalDate getPrazoServico() {
        return prazoServico;
    }

    public void setPrazoServico(LocalDate prazoServico) {
        this.prazoServico = prazoServico;
    }

    public double getPrecoServico() {
        return precoServico;
    }

    public void setPrecoServico(double precoServico) {
        this.precoServico = precoServico;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
