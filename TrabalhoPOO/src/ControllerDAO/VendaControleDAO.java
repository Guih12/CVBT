package ControllerDAO;

import Database.ConnectionFactory;
import Exceptions.NotExistException;
import Exceptions.NotExistVenda;
import Model.Cliente;
import Model.ItemVenda;
import Model.Produto;
import Model.Venda;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class VendaControleDAO {
    private ProdutoControleDAO produtoControleDAO = new ProdutoControleDAO();

    public void realizarVenda(Venda venda) throws SQLException, NotExistException {
        Connection connect = ConnectionFactory.getConnection();
        String query = "insert into Venda (venda_notafiscal, venda_valorpago, venda_desconto, venda_datavenda," +
                "venda_formapagamento, cliente_cod)" + "values (?,?,?,?,?,?)";

        PreparedStatement ExecSQL;
        ExecSQL = connect.prepareStatement(query);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        ExecSQL.setInt(1, venda.getNrNotaFiscal());
        ExecSQL.setDouble(2, venda.getValorPago());
        ExecSQL.setDouble(3, venda.getDesconto());
        ExecSQL.setString(4, format.format(venda.getDataVenda()));
        ExecSQL.setString(5, venda.getFormaPagamento());
        ExecSQL.setInt(6, venda.getCliente().getCodigoCliente());

        ExecSQL.executeUpdate();


        for (ItemVenda itemVenda : venda.getItemVendas()){

            String queryItemVenda = "insert into ItemVenda (quantidade, preco, nota_fiscal, codigo_prod)" +
                    "values (?,?,?,?)";

            PreparedStatement execItem;
            execItem = connect.prepareStatement(queryItemVenda);
            execItem.setInt(1, itemVenda.getQuantidade());
            execItem.setDouble(2, itemVenda.getPreco());
            execItem.setInt(3, venda.getNrNotaFiscal());
            execItem.setInt(4, itemVenda.getProduto().getCodigo());

            Produto produto = itemVenda.getProduto();
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - itemVenda.getQuantidade());

            int quantidade = itemVenda.getProduto().getQuantidadeEstoque() - itemVenda.getQuantidade();
            if(quantidade> 0){
                itemVenda.getProduto().setQuantidadeEstoque(quantidade);
            }else{
                throw new NotExistException("Quatidade desse produto a baixo do estoque");
            }

            String queryUpdate = "update Produto set estoque_produto = estoque_produto - ? " +
                    "where codigo_produto = ?";


            PreparedStatement execUpdate = connect.prepareStatement(queryUpdate);
            execUpdate.setInt(1, itemVenda.getQuantidade());
            execUpdate.setInt(2, itemVenda.getProduto().getCodigo());
            execUpdate.executeUpdate();
            execUpdate.close();

            execItem.executeUpdate();
            execItem.close();
        }
        connect.commit();
        ExecSQL.close();
        connect.close();

    }

    public List<Venda> listarVendas() throws SQLException {
        List<Venda> vendas = new ArrayList<>();
        Connection connection = ConnectionFactory.getConnection();

        String query = "select * from Venda";

        PreparedStatement execSQL;
        execSQL = connection.prepareStatement(query);
        ResultSet resultset;

        resultset = execSQL.executeQuery();

        Venda venda;
        while (resultset.next()){
            venda = new Venda();
            Cliente cliente = new Cliente();
            venda.setNrNotaFiscal(resultset.getInt("venda_notafiscal"));
            venda.setValorPago(resultset.getDouble("venda_valorpago"));
            venda.setDesconto(resultset.getDouble("venda_desconto"));
            venda.setDataVenda(resultset.getDate("venda_datavenda"));
            venda.setFormaPagamento(resultset.getNString("venda_formapagamento"));
            vendas.add(venda);
        }

        return vendas;
    }

    public Venda VendaPK(int notafiscal) throws SQLException, NotExistVenda {
        Venda venda;
        Connection connect = ConnectionFactory.getConnection();

        String query = "select * from Venda where venda_notafiscal = ?";

        PreparedStatement execSQL;
        execSQL = connect.prepareStatement(query);
        execSQL.setInt(1, notafiscal);

        ResultSet resultSet;
        resultSet = execSQL.executeQuery();
        resultSet.last();

        if(resultSet.getRow() > 0){
            venda = new Venda();
            venda.setNrNotaFiscal(resultSet.getInt("venda_notafiscal"));
            venda.setValorPago(resultSet.getDouble("venda_valorpago"));
            venda.setDesconto(resultSet.getDouble("venda_desconto"));
            venda.setDataVenda(resultSet.getDate("venda_datavenda"));
            venda.setFormaPagamento(resultSet.getNString("venda_formapagamento"));
        }else{
            throw new NotExistVenda("Venda não encontrada");
        }
        execSQL.close();
        connect.close();
        return venda;

    }

    public double getTotalVendaDia(int dia) throws SQLException {
        double total = 0.0;
        Connection connect = ConnectionFactory.getConnection();
        String query = "select sum(venda_valorpago) as ValorTotal from " +
                "Venda where day(venda_datavenda) = ?";

        PreparedStatement execSQL;
        execSQL = connect.prepareStatement(query);
        execSQL.setInt(1, dia);

        ResultSet resultSet;
        resultSet = execSQL.executeQuery();

        if(resultSet.next()){
            total += resultSet.getDouble("ValorTotal");
        }else{
            throw new SQLException("Não há vendas nesse dia");
        }
        execSQL.close();
        connect.close();
        return total;
    }

    public double getTotalVendaMes(int mes) throws SQLException {
        double total = 0.0;
        Connection connect = ConnectionFactory.getConnection();
        String query = "select sum(venda_valorpago) as ValorTotal from " +
                "Venda where month(venda_datavenda) = ?";

        PreparedStatement execSQL;
        execSQL = connect.prepareStatement(query);
        execSQL.setInt(1, mes);

        ResultSet resultSet;
        resultSet = execSQL.executeQuery();

        if(resultSet.next()){
            total += resultSet.getDouble("ValorTotal");
        }else{
            throw new SQLException("Não há vendas nesse dia");
        }
        execSQL.close();
        connect.close();
        return total;
    }

    public double totalFormaPagamento(String formapagamento) throws SQLException {
        double total = 0.0;
        Connection connect = ConnectionFactory.getConnection();
        String query = "select sum(venda_valorpago) as ValorTotal from Venda where venda_formapagamento = ?";

        PreparedStatement execSQL;
        execSQL = connect.prepareStatement(query);
        execSQL.setString(1, formapagamento);
        ResultSet resultSet;
        resultSet = execSQL.executeQuery();

        if(resultSet.next()){
             total+= resultSet.getDouble("ValorTotal");
        }
        execSQL.close();
        connect.close();
        return total;
    }

    public List<Venda> getVendaCliente(int codigo) throws SQLException {
        List<Venda> vendas = new ArrayList<>();
        Venda venda;
        Connection connect = ConnectionFactory.getConnection();
        String query = "select * from Venda where cliente_cod = ?";

        PreparedStatement execSQL;
        execSQL = connect.prepareStatement(query);
        execSQL.setInt(1, codigo);

        ResultSet resultSet;
        resultSet = execSQL.executeQuery();

        while (resultSet.next()){
            venda = new Venda();
            venda.setNrNotaFiscal(resultSet.getInt("venda_notafiscal"));
            venda.setValorPago(resultSet.getDouble("venda_valorpago"));
            venda.setDesconto(resultSet.getDouble("venda_desconto"));
            venda.setDataVenda(resultSet.getDate("venda_datavenda"));
            venda.setFormaPagamento(resultSet.getNString("venda_formapagamento"));
            vendas.add(venda);
        }
        execSQL.close();
        connect.close();
        return vendas;
    }


}
