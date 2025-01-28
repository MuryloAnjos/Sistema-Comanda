
package Model;

public class PedidoProduto {
    
    private int pddId;
    private int pdtId;
    private String pdtNome;
    private double pdtPreco;
    private int quantidade;
    private int cmaId;

    public int getPddId() {
        return pddId;
    }

    public void setPddId(int pddId) {
        this.pddId = pddId;
    }

    public int getPdtId() {
        return pdtId;
    }

    public void setPdtId(int pdtId) {
        this.pdtId = pdtId;
    }

    public String getPdtNome() {
        return pdtNome;
    }

    public void setPdtNome(String pdtNome) {
        this.pdtNome = pdtNome;
    }

    public double getPdtPreco() {
        return pdtPreco;
    }

    public void setPdtPreco(double pdtPreco) {
        this.pdtPreco = pdtPreco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getCmaId() {
        return cmaId;
    }

    public void setCmaId(int cmaId) {
        this.cmaId = cmaId;
    }

   
    

    
}