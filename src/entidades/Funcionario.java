package entidades;

public class Funcionario {

    private int idFuncionario;
    private String cpf;
    private String nome;
    private String areaAtuacao;

    public Funcionario(int idFuncionario, String cpf, String nome, String areaAtuacao) {
        this.idFuncionario = idFuncionario;
        this.cpf = cpf;
        this.nome = nome;
        this.areaAtuacao = areaAtuacao;
    }

    public Funcionario(String cpf, String nome, String areaAtuacao) {
        this.cpf = cpf;
        this.nome = nome;
        this.areaAtuacao = areaAtuacao;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAreaAtuacao() {
        return areaAtuacao;
    }

    public void setAreaAtuacao(String areaAtuacao) {
        this.areaAtuacao = areaAtuacao;
    }
}
