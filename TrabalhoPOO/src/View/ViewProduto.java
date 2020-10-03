package View;

import Exceptions.NotExistException;
import ControllerDAO.ProdutoControleDAO;
import Exceptions.NotExistProduct;
import Model.Produto;

import java.sql.SQLException;
import java.util.Scanner;

public class ViewProduto {
    private ProdutoControleDAO produtoControleDAO = new ProdutoControleDAO();
    private Scanner scanner = new Scanner(System.in);

    public void cadastrarProduto(){
        Produto produto = new Produto();
        System.out.print("Codigo produto: ");
        produto.setCodigo(this.scanner.nextInt());

        System.out.print("Descrição:  ");
        produto.setDescricao(this.scanner.next());

        System.out.print("preço produto: ");
        produto.setPreco(this.scanner.nextDouble());

        System.out.println("Quantidade: ");
        produto.setQuantidadeEstoque(this.scanner.nextInt());
        try{
            this.produtoControleDAO.create(produto);
            System.out.println("Produto cadastrado com sucesso");
        }catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
    }

    public void listarProdutos(){
        try {
            System.out.println(this.produtoControleDAO.read());
        }catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
    }

    public void ProcurarProduto(){
        System.out.println("Digite o codigo produto: ");
        int cod = this.scanner.nextInt();
        try{
            System.out.println(this.produtoControleDAO.pesquisarPK(cod));

        }catch (SQLException | NotExistException ex){
            System.err.println(ex.getMessage());
        }
    }

    public void alterarCliente(){
        Produto produto = new Produto();
        try {
            System.out.print("Digite o codigo do produto a ser alterado: ");
            produto.setCodigo(this.scanner.nextInt());
            System.out.println(this.produtoControleDAO.pesquisarPK(produto.getCodigo()));

            System.out.print("Digite a quantidade: ");
            produto.setQuantidadeEstoque(this.scanner.nextInt());
            this.produtoControleDAO.update(produto);
        }catch (SQLException | NotExistProduct | NotExistException ex){
            System.err.println(ex.getMessage());
        }
    }

    public void deletarProduto(){
        System.out.println("Digite o codigo produto: ");
        int cod = this.scanner.nextInt();
        try{
            this.produtoControleDAO.delete(cod);
            System.out.println("Produto excluido com sucesso");
        }catch (SQLException | NotExistException ex){
            System.err.println(ex.getMessage());
        }
    }
}
