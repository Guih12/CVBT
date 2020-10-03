package Model;

public class ItemVenda {
    private int quantidade;
    private double preco;
    private Produto produto;

    public ItemVenda() {
    }

    public ItemVenda(int quantidade, Produto produto) {
        this.quantidade = quantidade;
        this.preco = produto.getPreco();
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public double subTotal(){
        return  this.preco * this.quantidade;
    }
    @Override
    public String toString() {
        return "ItemVenda{\n" +
                "\nquantidade=" + quantidade +
                "\n, preco=" + preco +
                "\n, produto=" + produto +
                '}';
    }
}
