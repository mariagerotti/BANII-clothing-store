package model;

/**
 * Classe que representa um Fornecedor da loja.
 */
public class Fornecedor {
    private int idFornecedor;
    private String nome;
    private String cnpj;
    private String telefone;
    private String email;
    private String endereco;

    public Fornecedor() {}

    public Fornecedor(String nome, String cnpj, String telefone, String email, String endereco) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
    }

    public Fornecedor(int idFornecedor, String nome, String cnpj, String telefone, String email, String endereco) {
        this.idFornecedor = idFornecedor;
        this.nome = nome;
        this.cnpj = cnpj;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
    }

    public int getIdFornecedor() { return idFornecedor; }
    public void setIdFornecedor(int idFornecedor) { this.idFornecedor = idFornecedor; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    @Override
    public String toString() {
        return "Fornecedor{" +
                "idFornecedor=" + idFornecedor +
                ", nome='" + nome + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", endereco='" + endereco + '\'' +
                '}';
    }
}
