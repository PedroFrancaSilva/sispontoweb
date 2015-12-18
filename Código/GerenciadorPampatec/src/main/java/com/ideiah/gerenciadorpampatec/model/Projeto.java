package com.ideiah.gerenciadorpampatec.model;
// Generated 31/08/2015 13:49:28 by Hibernate Tools 4.3.1

import com.ideiah.gerenciadorpampatec.controller.NotificacoesEmpreendedorBean;
import com.ideiah.gerenciadorpampatec.dao.ProjetoDao;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import javax.faces.context.FacesContext;

/**
 * Projeto generated by hbm2java
 */
public class Projeto implements java.io.Serializable {

    public static final int ELABORACAO = 0;
    public static final int EM_PRE_AVALIACAO = 1;
    public static final int AVALIACAO = 2;
    public static final int FORMALIZACAO = 3;
    public static final int INCUBACAO = 4;
    public static final int PRE_APROVADO = 5;
    public static final int REPROVADO = 6;
    public static final int PRE_MELHORIA = 7;
    public static final int DEFININDO_EQUIPE = 8;
    public static final int LINHA_DE_BASE = 9;

    private Integer idProjeto;
    private Analiseemprego analiseemprego;
    private Negocio negocio;
    private Planofinanceiro planofinanceiro;
    private Produtoouservico produtoouservico;
    private String nome;
    private Date dataEnvio;
    private Integer status;
    private String potencialEmprego;
    private Set empreendedores = new HashSet(0);
    private String participacaoacionaria;
    private String edital;
    private Date dataCriacao;
    private GerenteRelacionamento gerenteRelacionamento;
    private NotificacoesEmpreendedorBean notificacoesBean;
    private Empreendedor empreendedorCorrespondente;
    private Set ProjetoBaseComoReferencia = new HashSet(0);//Projeto Base em que este projeto é referência.
    private Set ProjetoBase = new HashSet(0);//Projeto Base que esse projeto representa.

    public Projeto() {
        pegaObserver();
    }

    public Projeto(Integer idProjeto, Analiseemprego analiseemprego, Negocio negocio, Planofinanceiro planofinanceiro, Produtoouservico produtoouservico, String participacaoacionaria) {
        this.idProjeto = idProjeto;
        this.analiseemprego = analiseemprego;
        this.negocio = negocio;
        this.planofinanceiro = planofinanceiro;
        this.produtoouservico = produtoouservico;
        this.participacaoacionaria = participacaoacionaria;
        pegaObserver();
    }

    public Projeto(Integer idProjeto, Analiseemprego analiseemprego, Negocio negocio, Planofinanceiro planofinanceiro, Produtoouservico produtoouservico, String nome, Date dataEnvio, Integer status, String potencialEmprego, Set empreendedors, String participacaoacionaria, String edital,
            Date dataCriacao, GerenteRelacionamento gerenteDeRelacionamento) {
        this.idProjeto = idProjeto;
        this.analiseemprego = analiseemprego;
        this.negocio = negocio;
        this.planofinanceiro = planofinanceiro;
        this.produtoouservico = produtoouservico;
        this.nome = nome;
        this.dataEnvio = dataEnvio;
        this.status = status;
        this.potencialEmprego = potencialEmprego;
        this.empreendedores = empreendedors;
        this.participacaoacionaria = participacaoacionaria;
        this.edital = edital;
        this.dataCriacao = dataCriacao;
        this.gerenteRelacionamento = gerenteDeRelacionamento;
        pegaObserver();
    }

    private void pegaObserver() {
        FacesContext context = FacesContext.getCurrentInstance();
        NotificacoesEmpreendedorBean bean = context.getApplication().evaluateExpressionGet(context, "#{notificacoesEmpreendedorBean}", NotificacoesEmpreendedorBean.class);
        notificacoesBean = bean;
    }

    public boolean SalvarProjeto(Projeto projeto) {
        ProjetoDao projetoDao = new ProjetoDao();
        try {
            projetoDao.update(projeto);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public Integer getIdProjeto() {
        return this.idProjeto;
    }

    public void setIdProjeto(Integer idProjeto) {
        this.idProjeto = idProjeto;
    }

    public Analiseemprego getAnaliseemprego() {
        return this.analiseemprego;
    }

    public void setAnaliseemprego(Analiseemprego analiseemprego) {
        this.analiseemprego = analiseemprego;
    }

    public Negocio getNegocio() {
        return this.negocio;
    }

    public void setNegocio(Negocio negocio) {
        this.negocio = negocio;
    }

    public Planofinanceiro getPlanofinanceiro() {
        return this.planofinanceiro;
    }

    public void setPlanofinanceiro(Planofinanceiro planofinanceiro) {
        this.planofinanceiro = planofinanceiro;
    }

    public Produtoouservico getProdutoouservico() {
        return this.produtoouservico;
    }

    public void setProdutoouservico(Produtoouservico produtoouservico) {
        this.produtoouservico = produtoouservico;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataEnvio() {
        return this.dataEnvio;
    }

    public void setDataEnvio(Date dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public Integer getStatus() {
        return this.status;
    }

    public String getStatusString(int status) {
        String statusDescricao = "";

        switch (status) {
            case ELABORACAO:
                statusDescricao = "Em elaboração";
                break;
            case EM_PRE_AVALIACAO:
                statusDescricao = "Em Pré-Avaliação";
                break;
            case AVALIACAO:
                statusDescricao = "Em Avaliação";
                break;
            case FORMALIZACAO:
                statusDescricao = "Em Formalização";
                break;
            case INCUBACAO:
                statusDescricao = "Incubação";
                break;
        }

        return statusDescricao;

    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * Muda o status do Projeto ativando os observadores.
     *
     * @param status
     */
    public void mudarStatus(Integer status) {
        setStatus(status);
        notificacoesBean.update(null, this);
    }

    public String getPotencialEmprego() {
        return this.potencialEmprego;
    }

    public void setPotencialEmprego(String potencialEmprego) {
        this.potencialEmprego = potencialEmprego;
    }

    public Set getEmpreendedores() {
        return this.empreendedores;
    }

    public void setEmpreendedores(Set empreendedors) {
        this.empreendedores = empreendedors;
    }

    /**
     * @return the participacaoacionaria
     */
    public String getParticipacaoacionaria() {
        return participacaoacionaria;
    }

    /**
     * @param participacaoacionaria the participacaoacionaria to set
     */
    public void setParticipacaoacionaria(String participacaoacionaria) {
        this.participacaoacionaria = participacaoacionaria;
    }

    /**
     * @return the edital
     */
    public String getEdital() {
        return edital;
    }

    /**
     * @param edital the edital to set
     */
    public void setEdital(String edital) {
        this.edital = edital;
    }

    /**
     * @return the dataCriacao
     */
    public Date getDataCriacao() {
        return dataCriacao;
    }

    /**
     * @param dataCriacao the dataCriacao to set
     */
    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    /**
     * @return the gerenteRelacionamento
     */
    public GerenteRelacionamento getGerenteRelacionamento() {
        return gerenteRelacionamento;
    }

    /**
     * @param gerenteRelacionamento the gerenteRelacionamento to set
     */
    public void setGerenteRelacionamento(GerenteRelacionamento gerenteRelacionamento) {
        this.gerenteRelacionamento = gerenteRelacionamento;
    }

    /**
     *
     * @param status
     * @return lista de projeto com o status inserido
     */
    public static ArrayList<Projeto> buscarProjetoPorStatus(int status) {
        ArrayList<Projeto> listaDeProjetos;
        ProjetoDao projetoDao = new ProjetoDao();
        listaDeProjetos = projetoDao.buscar();

        ArrayList<Projeto> projetosGerente = new ArrayList<>();

        for (Projeto projeto : listaDeProjetos) {
            if (projeto.status == status) {
                projetosGerente.add(projeto);
            }
        }

        return projetosGerente;

    }
    
    public ArrayList<Empreendedor> retornaListaEmpreendedores(){
        ArrayList<Empreendedor> listaEmpreendedores =  new ArrayList<>();
        for (Object objeto : empreendedores) {
            Empreendedor empreendedor = (Empreendedor) objeto;
            listaEmpreendedores.add(empreendedor);
        }
        return listaEmpreendedores;
    }

    /**
     * @return the empreendedorCorrespondente
     */
    public Empreendedor getEmpreendedorCorrespondente() {
        return empreendedorCorrespondente;
    }

    /**
     * @param empreendedorCorrespondente the empreendedorCorrespondente to set
     */
    public void setEmpreendedorCorrespondente(Empreendedor empreendedorCorrespondente) {
        this.empreendedorCorrespondente = empreendedorCorrespondente;
    }

    /**
     * @return the ProjetoBaseComoReferencia
     */
    public Set getProjetoBaseComoReferencia() {
        return ProjetoBaseComoReferencia;
    }

    /**
     * @param ProjetoBaseComoReferencia the ProjetoBaseComoReferencia to set
     */
    public void setProjetoBaseComoReferencia(Set ProjetoBaseComoReferencia) {
        this.ProjetoBaseComoReferencia = ProjetoBaseComoReferencia;
    }

    /**
     * @return the ProjetoBase
     */
    public Set getProjetoBase() {
        return ProjetoBase;
    }

    /**
     * @param ProjetoBase the ProjetoBase to set
     */
    public void setProjetoBase(Set ProjetoBase) {
        this.ProjetoBase = ProjetoBase;
    }
    
    /**
     * Verifica se o projeto está em pré-avaliação
     * @return 
     */
    public boolean verificarEmPreAvaliacao(){
        return this.getStatus() != Projeto.EM_PRE_AVALIACAO;
    }

}
