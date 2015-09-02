package com.ideiah.gerenciadorpampatec.model;
// Generated 31/08/2015 13:49:28 by Hibernate Tools 4.3.1

import com.ideiah.gerenciadorpampatec.dao.EmpreededorDao;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Empreendedor generated by hbm2java
 */
public class Empreendedor implements java.io.Serializable {

    private int idEmpreendedor;
    private String senha;
    private Endereco endereco;
    private String nome;
    private String telefone;
    private String cpf;
    private String email;
    private String formacao;
    private String experiencia;
    private String competencia;
    private String participacaoAcionaria;
    private Set projetos = new HashSet(0);
    private static EmpreededorDao empreededorDao;

    public Empreendedor() {
        empreededorDao = new EmpreededorDao();
    }

    public Empreendedor(int idEmpreendedor, Endereco endereco, String nome, String cpf, String email, String formacao, String senha) {
        this.idEmpreendedor = idEmpreendedor;
        this.endereco = endereco;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.formacao = formacao;
        this.senha = senha;
        empreededorDao = new EmpreededorDao();

    }

    public Empreendedor(int idEmpreendedor, Endereco endereco, String nome, String telefone, String cpf, String email, String formacao, String experiencia, String competencia, String participacaoAcionaria, Set projetos, String senha) {
        this.idEmpreendedor = idEmpreendedor;
        this.endereco = endereco;
        this.nome = nome;
        this.telefone = telefone;
        this.cpf = cpf;
        this.email = email;
        this.formacao = formacao;
        this.experiencia = experiencia;
        this.competencia = competencia;
        this.participacaoAcionaria = participacaoAcionaria;
        this.projetos = projetos;
        this.senha = senha;
    }

    public int getIdEmpreendedor() {
        return this.idEmpreendedor;
    }

    public void setIdEmpreendedor(int idEmpreendedor) {
        this.idEmpreendedor = idEmpreendedor;
    }

    public Endereco getEndereco() {
        return this.endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFormacao() {
        return this.formacao;
    }

    public void setFormacao(String formacao) {
        this.formacao = formacao;
    }

    public String getExperiencia() {
        return this.experiencia;
    }

    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }

    public String getCompetencia() {
        return this.competencia;
    }

    public void setCompetencia(String competencia) {
        this.competencia = competencia;
    }

    public String getParticipacaoAcionaria() {
        return this.participacaoAcionaria;
    }

    public void setParticipacaoAcionaria(String participacaoAcionaria) {
        this.participacaoAcionaria = participacaoAcionaria;
    }

    public Set getProjetos() {
        return this.projetos;
    }

    public void setProjetos(Set projetos) {
        this.projetos = projetos;
    }

    /**
     * @return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    /*
    *
    */
    public void realizarCadastro(){
        empreededorDao.salvar(this);
    }
    
    public static ArrayList<Empreendedor> buscaEmpreendedores(){
        return empreededorDao.buscar();
    }
    
    public Empreendedor buscarPorCpf(String user){
        return empreededorDao.buscarPorCpf(user);
    }

}
