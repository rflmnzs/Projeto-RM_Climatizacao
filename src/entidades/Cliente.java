package entidades;

public class Cliente {

    private int id_cliente;
    private String nome;
    private String cpf;
    private String tipo_contrato;
    private String contato;
    private String endereco;

    // construtor cheio
    public Cliente(int id_cliente, String nome, String cpf, String tipo_contrato, String contato, String endereco) {
        this.id_cliente = id_cliente;
        this.nome = nome;
        this.cpf = cpf;
        this.tipo_contrato = tipo_contrato;
        this.contato = contato;
        this.endereco = endereco;
    }

    // Construtor vazio
    public Cliente() {}

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTipo_contrato() {
        return tipo_contrato;
    }

    public void setTipo_contrato(String tipo_contrato) {
        this.tipo_contrato = tipo_contrato;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
