package entidades;

import java.time.LocalDate;

public class NotaFiscal {

    private int idNotaFiscal;
    private int idServico;
    private int idCliente;
    private String numNotaFiscal;
    private LocalDate dataEmissao;

    
    public NotaFiscal(int idNotaFiscal, int idServico, int idCliente,
                      String numNotaFiscal, LocalDate dataEmissao) {
        this.idNotaFiscal = idNotaFiscal;
        this.idServico = idServico;
        this.idCliente = idCliente;
        this.numNotaFiscal = numNotaFiscal;
        this.dataEmissao = dataEmissao;
    }

    
    public int getIdNotaFiscal() {
        return idNotaFiscal;
    }

    public void setIdNotaFiscal(int idNotaFiscal) {
        this.idNotaFiscal = idNotaFiscal;
    }


    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNumNotaFiscal() {
        return numNotaFiscal;
    }

    public void setNumNotaFiscal(String numNotaFiscal) {
        this.numNotaFiscal = numNotaFiscal;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }
}
