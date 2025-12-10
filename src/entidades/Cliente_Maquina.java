package entidades;

public class Cliente_Maquina {
    
    private int id_maquina;
    private int id_cliente;
    private String local_instalador;
    private String data_instalacao;

    public Cliente_Maquina(int id_maquina, int id_cliente, String local_instalador, String data_instalacao) {
        this.id_maquina = id_maquina;
        this.id_cliente = id_cliente;
        this.local_instalador = local_instalador;
        this.data_instalacao = data_instalacao;
    }

    public int getId_maquina() {
        return id_maquina;
    }

    public void setId_maquina(int id_maquina) {
        this.id_maquina = id_maquina;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getLocal_instalador() {
        return local_instalador;
    }

    public void setLocal_instalador(String local_instalador) {
        this.local_instalador = local_instalador;
    }

    public String getData_instalacao() {
        return data_instalacao;
    }

    public void setData_instalacao(String data_instalacao) {
        this.data_instalacao = data_instalacao;
    }
    
    
    
}
