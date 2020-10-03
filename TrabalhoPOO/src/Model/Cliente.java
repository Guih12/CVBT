package Model;

import java.util.Objects;

public class Cliente {
    private int codigoCliente;
    private String nome;
    private String cpf;
    private String endereco;
    private String cidade;
    private String estado;
    private String tefefone;

    public Cliente() {
    }

    public Cliente(int codigoCliente, String nome, String cpf, String endereco, String cidade, String estado, String tefefone) {
        this.codigoCliente = codigoCliente;
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.cidade = cidade;
        this.estado = estado;
        this.tefefone = tefefone;
    }

    public int getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTefefone() {
        return tefefone;
    }

    public void setTefefone(String tefefone) {
        this.tefefone = tefefone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return codigoCliente == cliente.codigoCliente;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoCliente);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "\ncodigoCliente=" + codigoCliente +
                "\n, nome='" + nome + '\'' +
                "\n, cpf='" + cpf + '\'' +
                "\n, endereco='" + endereco + '\'' +
                "\n, cidade='" + cidade + '\'' +
                "\n, estado='" + estado + '\'' +
                "\n, tefefone='" + tefefone + '\'' +
                '}';
    }
}
