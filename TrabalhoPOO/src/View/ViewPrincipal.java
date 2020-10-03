package View;

import java.util.Scanner;

public class ViewPrincipal {
    private ViewCliente viewCliente = new ViewCliente();
    private ViewProduto viewProduto = new ViewProduto();
    private ViewVenda viewVenda = new ViewVenda();
    private Scanner scanner = new Scanner(System.in);

    public void start(){
        int options;
        int op;
        do{

            System.out.println("1 - CRUD de clientes");
            System.out.println("2 - CRUD de produtos");
            System.out.println("3 - Vendas");
            options = this.scanner.nextInt();
            switch (options){
                case 1:
                        do{
                            System.out.println("########### AREA DE CLIENTES ############");
                            System.out.println("1 - cadastrar cliente");
                            System.out.println("2 - listar clientes");
                            System.out.println("3 - pesquisar cliente");
                            System.out.println("4 - alterar cliente");
                            System.out.println("5 - deletar cliente");
                            System.out.println("0 - voltar ao inicio");
                            op = this.scanner.nextInt();
                            switch (op){
                                case 1:
                                    this.viewCliente.cadastrarCliente();
                                    break;
                                case 2:
                                    this.viewCliente.listarClientes();
                                    break;

                                case 3:
                                    this.viewCliente.pesquisar();
                                    break;
                                case 4:
                                    this.viewCliente.alterarCliente();
                                    break;

                                case 5:
                                    this.viewCliente.deletarCliente();
                                    break;
                            }
                        }while(op!=0);

                    break;

                case 2:
                        do{
                            System.out.println("############## AREA DE PRODUTOS ############");
                            System.out.println("1 - cadastrar produto");
                            System.out.println("2 - listar produto");
                            System.out.println("3 - pesquisar produto");
                            System.out.println("4 - alterar produto");
                            System.out.println("5 - deletar produto");
                            System.out.println("0 - voltar ao inicio");
                            op = this.scanner.nextInt();
                            switch (op){
                                case 1:
                                    this.viewProduto.cadastrarProduto();
                                    break;

                                case 2:
                                    this.viewProduto.listarProdutos();
                                    break;

                                case 3:
                                    this.viewProduto.ProcurarProduto();
                                    break;

                                case 4:
                                    this.viewProduto.alterarCliente();
                                    break;

                                case 5:
                                    this.viewProduto.deletarProduto();
                                    break;
                            }
                        }while (op!=0);
                    break;
                case 3:
                        do{
                            System.out.println("########### CAIXA ABERTO ###############");
                            System.out.println(">>>>> 1 - REALIZAR VENDA - <<<<<");
                            System.out.println(">>>>> 2 - LISTAR VENDAS - <<<<<");
                            System.out.println(">>>>> 3 - LISTAR UMA VENDA - <<<<<");
                            System.out.println(">>>>> 4 - TOTAL VENDA DIARIA - <<<<<");
                            System.out.println(">>>>> 5 - TOTAL VENDA MENSAL - <<<<<");
                            System.out.println(">>>>> 6 - TOTAL DE VENDAS FORMA DE PAGAMENTO - <<<<<");
                            System.out.println(">>>>> 7 - FILTRAR VENDAS DE UM CLIENTE - <<<<<");
                            System.out.println(">>>>> 0 - VOLTAR AO INICIO - <<<<<");
                            op = this.scanner.nextInt();
                            switch (op){
                                case 1:
                                    viewVenda.addVenda();
                                    break;

                                case 2:
                                    viewVenda.listarVendas();
                                    break;

                                case 3:
                                    viewVenda.listarVendaPK();
                                    break;

                                case 4:
                                    viewVenda.totalDia();
                                    break;

                                case 5:
                                    viewVenda.totalMes();
                                    break;

                                case 6:
                                    viewVenda.totalVendaPagamento();
                                    break;

                                case 7:
                                    viewVenda.vendasCliente();
                                    break;
                            }
                        }while (op!=0);
                    break;
            }
        }while (options!=0);
    }
}
