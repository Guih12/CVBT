package View;

import ControllerDAO.ClienteControleDAO;
import ControllerDAO.ProdutoControleDAO;
import ControllerDAO.VendaControleDAO;
import Exceptions.NotExistException;
import Exceptions.NotExistVenda;
import Model.Cliente;
import Model.ItemVenda;
import Model.Produto;
import Model.Venda;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ViewVenda {
    private VendaControleDAO vendaControleDAO = new VendaControleDAO();
    private ProdutoControleDAO produtoControleDAO = new ProdutoControleDAO();
    private ClienteControleDAO clienteControleDAO = new ClienteControleDAO();
    private Scanner scanner = new Scanner(System.in);

    public void addVenda(){
        Venda venda = new Venda();
        int quantAux = 0;

        try{
            System.out.print("Qual usuário sera relacionado a compra: ");
            int cod = this.scanner.nextInt();
            Cliente cliente = this.clienteControleDAO.pesquisarPK(cod);
            System.out.println(cliente.getNome());
            venda.setCliente(cliente);
        }catch (SQLException | NotExistException ex){
            System.err.println(ex.getMessage());
        }
        int op;
        do{
            System.out.println("1 - realizar venda 0 - finalizar venda: ");
            op = this.scanner.nextInt();
            switch (op){
                case 1:
                    while (true){
                        try {
                            //pesquisa o codigo do produto
                            System.out.print("Codigo do produto: ");
                            int codProd = this.scanner.nextInt();
                            Produto produto = this.produtoControleDAO.pesquisarPK(codProd);
                            System.out.println(produto);
                            System.out.print("Quantidade desse produto: ");
                            int quantidade = this.scanner.nextInt();

                            if(quantidade > produto.getQuantidadeEstoque()){
                                System.out.println("Produto a baixo do estoque");
                            }else{
                                quantAux += quantidade;

                                //adcionar um novo produto
                                ItemVenda itemVenda = new ItemVenda(quantidade, produto);
                                venda.addItemVenda(itemVenda);

                                System.out.println("SUBTOTAL: R$" + venda.valorAtualizado());
                            }
                        }catch (SQLException | NotExistException ex){
                            System.out.println(ex.getMessage());
                        }
                        break;
                    }
            }

        }while (op!=0);

        System.out.print("Digite o numero da nota fiscal: ");
        venda.setNrNotaFiscal(this.scanner.nextInt());

        System.out.print("Digite o desconto: ");
        venda.setDesconto(this.scanner.nextDouble());

        System.out.println("Valor TOTAL:" + venda.valorTotal());
        venda.setValorPago(venda.valorTotal());

        Date data = null;
        while (data == null){
            try {
                System.out.print("Digite a data da venda: ");
                String datavenda = this.scanner.next();
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                data = format.parse(datavenda);
                venda.setDataVenda(data);
            }catch (ParseException e){
                System.err.println(e.getMessage());
            }
        }
        System.out.print("Digite a forma de pagamento: ");
        venda.setFormaPagamento(this.scanner.next());
        try{
            this.vendaControleDAO.realizarVenda(venda);
            System.out.println("VENDA REALIZADA COM SUCESSO");

        }catch (SQLException | NotExistException ex){
            System.err.println(ex.getMessage());
        }
    }

    public void listarVendas(){
        try{
            System.out.println(this.vendaControleDAO.listarVendas());
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    public void listarVendaPK(){
        try{
            System.out.print("Digite o numero da nota fiscal: ");
            int notafiscal = this.scanner.nextInt();
            System.out.println(this.vendaControleDAO.VendaPK(notafiscal));
        }catch (SQLException | NotExistVenda ex){
            System.err.println(ex.getMessage());
        }
    }

    public void totalDia(){
        try{
            System.out.print("Digite o dia: ");
            int dia = this.scanner.nextInt();
            System.out.println("TOTAL: R$: " + this.vendaControleDAO.getTotalVendaDia(dia)+ " REAIS");
        }catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
    }

    public void totalMes(){
        try{
            System.out.print("Digite o mes: ");
            int mes = this.scanner.nextInt();
            System.out.println("TOTAL: R$: " + this.vendaControleDAO.getTotalVendaMes(mes)+ " REAIS");
        }catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
    }

    public void totalVendaPagamento(){
        try{
            System.out.print("Digite o a forma de pagamento: ");
            String formapagamento = this.scanner.next();
            System.out.println("TOTAL R$: " + this.vendaControleDAO.totalFormaPagamento(formapagamento) + " REAIS");
        }catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
    }

    public void vendasCliente(){
        try{
            System.out.print("Digite o codigo do cliente: ");
            int cod = this.scanner.nextInt();
            System.out.println(this.vendaControleDAO.getVendaCliente(cod));
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
}
