package entidades;

public class Maquina {

    private int id_maquina;
    private String tipo_maquina;
    private String modelo;

   
    public Maquina(int id_maquina, String tipo_maquina, String modelo) {
        this.id_maquina = id_maquina;
        this.tipo_maquina = tipo_maquina;
        this.modelo = modelo;
    }

    // Construtor usado ao cadastrar nova máquina
    public Maquina(String tipo_maquina, String modelo) {
        this.tipo_maquina = tipo_maquina;
        this.modelo = modelo;
    }

    // GETTERS E SETTERS

    public int getId_maquina() {
        return id_maquina;
    }

    public void setId_maquina(int id_maquina) {
        this.id_maquina = id_maquina;
    }

    public String getTipo_maquina() {
        return tipo_maquina;
    }

    public void setTipo_maquina(String tipo_maquina) {
        this.tipo_maquina = tipo_maquina;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}
