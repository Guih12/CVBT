package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Venda {
    private int nrNotaFiscal;
    private double valorPago;
    private double desconto;
    private Date dataVenda;
    private String formaPagamento;

    private Cliente cliente;
    private static final List<ItemVenda> itemVendas = new ArrayList<>();

    public Venda() {
    }

    public Venda(int nrNotaFiscal, double valorPago, double desconto, Date dataVenda, String formaPagamento) {
        this.nrNotaFiscal = nrNotaFiscal;
        this.valorPago = valorPago;
        this.desconto = desconto;
        this.dataVenda = dataVenda;
        this.formaPagamento = formaPagamento;
    }

    public int getNrNotaFiscal() {
        return nrNotaFiscal;
    }

    public void setNrNotaFiscal(int nrNotaFiscal) {
        this.nrNotaFiscal = nrNotaFiscal;
    }

    public double getValorPago() {
        return valorPago;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemVenda> getItemVendas() {
        return itemVendas;
    }

    public void addItemVenda(ItemVenda itemVenda){
        Venda.itemVendas.add(itemVenda);
    }

    public double valorAtualizado(){
        double sub = 0.0;
        for(ItemVenda item : Venda.itemVendas){
            sub+= item.subTotal();
        }
        return sub;
    }

    public double valorTotal(){
        double total = 0.0;
        double desc = this.valorAtualizado() - this.desconto;
        return total =+ desc;
    }

    @Override
    public String toString() {
        return "Venda:" +
                "\n Nota fiscal: " + nrNotaFiscal +
                "\n Valor pago: " + valorPago +
                "\n Desconto: " + desconto +
                "\n Data venda: " + dataVenda +
                "\n Forma pagamento: " + formaPagamento + '\'' +
                "\n" +
                "------------------------------------------" +
                "\n";
    }
}
