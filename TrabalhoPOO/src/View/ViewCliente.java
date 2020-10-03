package View;

import ControllerDAO.ClienteControleDAO;
import Exceptions.NotExistException;
import Model.Cliente;

import java.sql.SQLException;
import java.util.Scanner;

public class ViewCliente {
    private ClienteControleDAO clienteControleDAO = new ClienteControleDAO();
    private Scanner scanner = new Scanner(System.in);

    public void cadastrarCliente(){
        Cliente cliente = new Cliente();
        System.out.print("Digite o codigo: ");
        cliente.setCodigoCliente(this.scanner.nextInt());

        System.out.print("Digite o nome do cliente:");
        cliente.setNome(this.scanner.next());

        System.out.print("Digite o cpf:");
        cliente.setCpf(this.scanner.next());

        System.out.print("Digite o endereço: ");
        cliente.setEndereco(this.scanner.next());

        System.out.print("Digite a cidade: ");
        cliente.setCidade(this.scanner.next());

        System.out.print("Digite sigla estado: ");
        cliente.setEstado(this.scanner.next());

        System.out.print("Digite telefone: ");
        cliente.setTefefone(this.scanner.next());
        try{
            this.clienteControleDAO.create(cliente);
        }catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
    }
    public void listarClientes(){
        try {
            System.out.println(this.clienteControleDAO.read());

        }catch (SQLException ex){
            System.err.println(ex.getMessage());
        }
    }

    public void pesquisar(){
        System.out.print("Digite o codigo a ser pesquisado: ");
        int cod = this.scanner.nextInt();
        try{
            System.out.println(this.clienteControleDAO.pesquisarPK(cod).getNome());
        }catch (SQLException | NotExistException ex){
            System.err.println(ex.getMessage());
        }
    }

    public void alterarCliente(){
        Cliente cliente = new Cliente();
        try {
            System.out.print("Digite o codigo do cliente a ser alterado: ");
            cliente.setCodigoCliente(this.scanner.nextInt());

            System.out.println(this.clienteControleDAO.pesquisarPK(cliente.getCodigoCliente()).getNome());
            System.out.println("Digite o nome: ");
            cliente.setNome(this.scanner.next());

            this.clienteControleDAO.update(cliente);
        }catch (SQLException | NotExistException ex){
            System.err.println(ex.getMessage());
        }
    }

    public void deletarCliente(){
        System.out.print("Digite o codigo a ser deletado: ");
        int cod = this.scanner.nextInt();
        try{
            this.clienteControleDAO.delete(cod);
            System.out.println("Cliente deletado com sucesso");

        }catch (SQLException | NotExistException ex){
            System.err.println(ex.getCause());
        }
    }
}
