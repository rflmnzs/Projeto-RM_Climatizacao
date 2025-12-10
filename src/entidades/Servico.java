
package entidades;

public class Servico {

    private int id_servico;
    private int cliente_id;
    private String data_solicitacao;
    private String descricao;
    private String data_conclusao;
    private double valor_total;

    public Servico(int id_servico, int cliente_id, String data_solicitacao, String descricao, String data_conclusao, double valor_total) {
        this.id_servico = id_servico;
        this.cliente_id = cliente_id;
        this.data_solicitacao = data_solicitacao;
        this.descricao = descricao;
        this.data_conclusao = data_conclusao;
        this.valor_total = valor_total;
    }

    public Servico(int id_servico, int cliente_id, String data_solicitacao, String descricao, double valor_total) {
        this.id_servico = id_servico;
        this.cliente_id = cliente_id;
        this.data_solicitacao = data_solicitacao;
        this.descricao = descricao;
        this.data_conclusao = null;
        this.valor_total = valor_total;
    }

    public int getId_servico() {
        return id_servico;
    }

    public void setId_servico(int id_servico) {
        this.id_servico = id_servico;
    }

    public int getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }

    public String getData_solicitacao() {
        return data_solicitacao;
    }

    public void setData_solicitacao(String data_solicitacao) {
        this.data_solicitacao = data_solicitacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData_conclusao() {
        return data_conclusao;
    }

    public void setData_conclusao(String data_conclusao) {
        this.data_conclusao = data_conclusao;
    }

    public double getValor_total() {
        return valor_total;
    }

    public void setValor_total(double valor_total) {
        this.valor_total = valor_total;
    }
}
