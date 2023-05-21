package br.unitins.ecommerce.model.usuario;

import java.util.List;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

import br.unitins.ecommerce.model.DefaultEntity;
import br.unitins.ecommerce.model.endereco.Endereco;
import br.unitins.ecommerce.model.produto.Produto;
import br.unitins.ecommerce.model.usuario.pessoafisica.PessoaFisica;

@Entity
public class Usuario extends DefaultEntity {

    @OneToOne
    @JoinColumn(name = "id_pessoa_fisica", unique = true, nullable = false)
    private PessoaFisica pessoaFisica;

    private String login;

    @Column(nullable = false)
    private String senha;

    @ElementCollection
    @CollectionTable(name = "perfis", joinColumns = @JoinColumn(name = "id_usuario", referencedColumnName = "id"))
    @Column(name = "perfil", length = 30)
    private Set<Perfil> perfis;

    @ManyToMany
    @JoinTable(name = "lista_desejo",
                joinColumns = @JoinColumn(name = "id_usuario"),
                inverseJoinColumns = @JoinColumn(name = "id_produto"))
    // Criando uma tabela auxiliar
    private List<Produto> produtos;

    @ManyToOne
    @JoinColumn(name = "id_endereco", nullable = false)
    private Endereco endereco;

    @OneToOne
    @JoinColumn(name = "id_telefone_principal", unique = true, nullable = false)
    private Telefone telefonePrincipal;

    @OneToOne
    @JoinColumn(name = "id_telefone_opcional", unique = true)
    private Telefone telefoneOpcional;

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(Produto produto) {
        this.produtos.add(produto);
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Telefone getTelefonePrincipal() {
        return telefonePrincipal;
    }

    public void setTelefonePrincipal(Telefone telefonePrincipal) {
        this.telefonePrincipal = telefonePrincipal;
    }

    public Telefone getTelefoneOpcional() {
        return telefoneOpcional;
    }

    public void setTelefoneOpcional(Telefone telefoneOpcional) {
        this.telefoneOpcional = telefoneOpcional;
    }

    public PessoaFisica getPessoaFisica() {
        return pessoaFisica;
    }

    public void setPessoaFisica(PessoaFisica pessoaFisica) {
        this.pessoaFisica = pessoaFisica;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Set<Perfil> getPerfis() {
        return perfis;
    }

    public void setPerfis(Set<Perfil> perfis) {
        this.perfis = perfis;
    }
}
