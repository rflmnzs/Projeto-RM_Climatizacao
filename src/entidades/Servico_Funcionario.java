package entidades;

public class Servico_Funcionario {
    
    private int id_servico;
    private int id_funcionario;

    public Servico_Funcionario(int id_servico, int id_funcionario) {
        this.id_servico = id_servico;
        this.id_funcionario = id_funcionario;
    }

    public int getId_servico() {
        return id_servico;
    }

    public void setId_servico(int id_servico) {
        this.id_servico = id_servico;
    }

    public int getId_funcionario() {
        return id_funcionario;
    }

    public void setId_funcionario(int id_funcionario) {
        this.id_funcionario = id_funcionario;
    }
}
