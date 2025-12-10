package entidades;


public class Estoque {
    
    private int id_estoque;
    private int quantidade;
    private int id_maquina;

    public Estoque(int id_estoque, int quantidade, int id_maquina) {
        this.id_estoque = id_estoque;
        this.quantidade = quantidade;
        this.id_maquina = id_maquina;
    }

    public int getId_estoque() {
        return id_estoque;
    }

    public void setId_estoque(int id_estoque) {
        this.id_estoque = id_estoque;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getId_maquina() {
        return id_maquina;
    }

    public void setId_maquina(int id_maquina) {
        this.id_maquina = id_maquina;
    }
    
    
    
}
