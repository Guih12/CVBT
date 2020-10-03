package ControllerDAO;

import Database.ConnectionFactory;
import Exceptions.NotExistException;
import Exceptions.NotExistProduct;
import Model.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoControleDAO {

    public void create(Produto produto) throws SQLException {
        Connection connect = ConnectionFactory.getConnection();
        String query  = "INSERT INTO Produto (codigo_produto, descricao_produto, preco_produto," +
                "estoque_produto)" + "VALUES(?,?,?,?)";

        PreparedStatement execSQL;
        execSQL = connect.prepareStatement(query);
        execSQL.setInt(1, produto.getCodigo());
        execSQL.setString(2, produto.getDescricao());
        execSQL.setDouble(3, produto.getPreco());
        execSQL.setInt(4, produto.getQuantidadeEstoque());

        execSQL.executeUpdate();
        connect.commit();
        execSQL.close();
        connect.close();
    }
    public List<Produto> read() throws SQLException {
        List<Produto> produtos = new ArrayList<>();
        Connection connect = ConnectionFactory.getConnection();

        String query = "SELECT *  FROM Produto";
        PreparedStatement execSQL;
        execSQL = connect.prepareStatement(query);
        ResultSet result;
        result = execSQL.executeQuery();

        Produto produto;
        while (result.next()){
            produto = new Produto();
            produto.setCodigo(result.getInt("codigo_produto"));
            produto.setDescricao(result.getString("descricao_produto"));
            produto.setPreco(result.getDouble("preco_produto"));
            produto.setQuantidadeEstoque(result.getInt("estoque_produto"));
            produtos.add(produto);
        }
        return produtos;
    }
    
    public Produto pesquisarPK(int cod) throws SQLException, NotExistException {
        Produto produto;
        Connection connect = ConnectionFactory.getConnection();
        String query = "SELECT * FROM Produto WHERE codigo_produto = ?";

        PreparedStatement execSQL;
        execSQL = connect.prepareStatement(query);
        execSQL.setInt(1, cod);

        ResultSet result;
        result = execSQL.executeQuery();
        result.last();

        if(result.getRow() > 0){
            produto = new Produto();
            produto.setCodigo(result.getInt("codigo_produto"));
            produto.setDescricao(result.getString("descricao_produto"));
            produto.setPreco(result.getDouble("preco_produto"));
            produto.setQuantidadeEstoque(result.getInt("estoque_produto"));
        }else{
            throw new NotExistException("Não há esse produto");
        }
        execSQL.close();
        connect.close();
        return produto;
    }


    public void update(Produto produto) throws SQLException, NotExistProduct {
        Connection connect = ConnectionFactory.getConnection();
        String query = "UPDATE Produto set estoque_produto = ? WHERE codigo_produto = ?";

        PreparedStatement execSQL;

        execSQL = connect.prepareStatement(query);

        execSQL.setInt(1, produto.getQuantidadeEstoque());
        execSQL.setInt(2, produto.getCodigo());

        int quantidadeAlterados = execSQL.executeUpdate();

        connect.commit();
        execSQL.close();
        connect.close();

        if(quantidadeAlterados == 0)
            throw new NotExistProduct("Produto não existente");

    }

    public void delete(int cod) throws SQLException, NotExistException{
        Connection connect = ConnectionFactory.getConnection();
        String query = "DELETE FROM Produto WHERE codigo_produto = ?";

        PreparedStatement execSQL;
        execSQL = connect.prepareStatement(query);
        execSQL.setInt(1, cod);
        int linha = execSQL.executeUpdate();

        connect.commit();
        execSQL.close();
        connect.close();

        if(linha == 0){
            throw new NotExistException("Produto do codigo" + cod + "não existe");
        }
    }
}
