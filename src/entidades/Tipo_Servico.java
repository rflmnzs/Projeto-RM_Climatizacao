
package entidades;

public class Tipo_Servico {
    
    private int id_tipoServico;
    private String descricao;

    public Tipo_Servico(int id_tipoServico, String descricao) {
        this.id_tipoServico = id_tipoServico;
        this.descricao = descricao;
    }

    public int getId_tipoServico() {
        return id_tipoServico;
    }

    public void setId_tipoServico(int id_tipoServico) {
        this.id_tipoServico = id_tipoServico;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
