package ControllerDAO;

import Database.ConnectionFactory;
import Exceptions.NotExistException;
import Model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteControleDAO {

    public void create(Cliente cliente) throws SQLException {
        Connection connect = ConnectionFactory.getConnection();
        String query = "insert into Cliente (codigo_cliente, nome_cliente, cpf_cliente, endereco_cliente, " +
                "cidade_cliente, estado_cliente, telefone_cliente)" + "values(?,?,?,?,?,?,?)";

        PreparedStatement execSQL;
        execSQL = connect.prepareStatement(query);
        execSQL.setInt(1, cliente.getCodigoCliente());
        execSQL.setString(2, cliente.getNome());
        execSQL.setString(3, cliente.getCpf());
        execSQL.setString(4, cliente.getEndereco());
        execSQL.setString(5, cliente.getCidade());
        execSQL.setString(6, cliente.getEstado());
        execSQL.setString(7, cliente.getTefefone());

        execSQL.executeUpdate();
        connect.commit();
        execSQL.close();
        connect.close();
    }

    public List<Cliente> read() throws SQLException {
        List<Cliente> listClientes = new ArrayList<>();

        Connection connect = ConnectionFactory.getConnection();

        String query = "SELECT * FROM Cliente";

        PreparedStatement excSQL;

        excSQL = connect.prepareStatement(query);
        ResultSet resultadoConsulta;

        resultadoConsulta = excSQL.executeQuery();

        Cliente cliente;
        while (resultadoConsulta.next()){
            cliente = new Cliente();
            cliente.setCodigoCliente(resultadoConsulta.getInt("codigo_cliente"));
            cliente.setNome(resultadoConsulta.getString("nome_cliente"));
            cliente.setCpf(resultadoConsulta.getString("cpf_cliente"));
            cliente.setEndereco(resultadoConsulta.getString("endereco_cliente"));
            cliente.setCidade(resultadoConsulta.getString("cidade_cliente"));
            cliente.setEstado(resultadoConsulta.getString("estado_cliente"));
            cliente.setTefefone(resultadoConsulta.getString("telefone_cliente"));

            listClientes.add(cliente);
        }
        return listClientes;
    }

    public Cliente pesquisarPK(int cod) throws SQLException, NotExistException {
        Cliente cliente;
        Connection connect = ConnectionFactory.getConnection();

        String query = "SELECT * FROM Cliente WHERE codigo_cliente = ?";

        PreparedStatement execSQL;

        execSQL = connect.prepareStatement(query);
        execSQL.setInt(1, cod);

        ResultSet resultconsult;

        resultconsult =  execSQL.executeQuery();
        resultconsult.last();

        if(resultconsult.getRow() > 0){
            cliente = new Cliente();
            cliente.setCodigoCliente(resultconsult.getInt("codigo_cliente"));
            cliente.setNome(resultconsult.getString("nome_cliente"));
        }else{
            throw new NotExistException("Cliente não encontrado");
        }
        execSQL.close();
        connect.close();

        return cliente;
    }


    public void update(Cliente cliente) throws SQLException, NotExistException {
        Connection connect = ConnectionFactory.getConnection();
        String query = "UPDATE Cliente SET nome_cliente = ? WHERE codigo_cliente = ?";

        PreparedStatement execSQL;

        execSQL = connect.prepareStatement(query);
        execSQL.setString(1, cliente.getNome());
        execSQL.setInt(2, cliente.getCodigoCliente());

        int quantidadeAlterados = execSQL.executeUpdate();

        connect.commit();
        execSQL.close();
        connect.close();

        if(quantidadeAlterados == 0)
            throw new NotExistException("Cliente não encontrado");
    }

    public void delete(int cod) throws SQLException, NotExistException {
        Connection connect = ConnectionFactory.getConnection();
        String query = "DELETE FROM Cliente WHERE codigo_cliente = ?";

        PreparedStatement execSQL;
        execSQL = connect.prepareStatement(query);
        execSQL.setInt(1, cod);
        int linha = execSQL.executeUpdate();

        connect.commit();

        execSQL.close();
        connect.close();

        if(linha == 0){
            throw new NotExistException("Cliente com o códido" + cod + "não existe");
        }
    }
}
