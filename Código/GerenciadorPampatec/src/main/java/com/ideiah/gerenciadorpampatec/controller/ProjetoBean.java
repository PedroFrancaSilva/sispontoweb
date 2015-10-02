/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ideiah.gerenciadorpampatec.controller;

import com.ideiah.gerenciadorpampatec.dao.ProjetoDao;
import com.ideiah.gerenciadorpampatec.model.Analiseemprego;
import com.ideiah.gerenciadorpampatec.model.Empreendedor;
import com.ideiah.gerenciadorpampatec.model.Negocio;
import com.ideiah.gerenciadorpampatec.model.Planofinanceiro;
import com.ideiah.gerenciadorpampatec.model.Produtoouservico;
import com.ideiah.gerenciadorpampatec.model.Projeto;
import com.ideiah.gerenciadorpampatec.util.EmailUtil;
import com.ideiah.gerenciadorpampatec.util.FacesUtil;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Pedro
 */
@ManagedBean(name = "projetoBean")
@ViewScoped
public class ProjetoBean implements Serializable {

    private Empreendedor empreendedorSelected;
    private Projeto projeto;
    private Analiseemprego analiseEmprego;
    private Negocio negocio;
    private Produtoouservico produtoOuSevico;
    private Planofinanceiro planoFinanceiro;
    private String emailEmpreendedor;
    private List<Empreendedor> listaEmpreendedor;
    private List<Empreendedor> empreedendoresAdicionados;
    private String selectedButton;
    private String descricaoButtonOutro;
    private Empreendedor empreendedorSession;

    public ProjetoBean() {
        listaEmpreendedor = Empreendedor.retornarEmpreendedores();
        empreedendoresAdicionados = new ArrayList<>();
        HttpSession secao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        projeto = (Projeto) secao.getAttribute("projetoSelecionado");
        empreendedorSession = (Empreendedor) secao.getAttribute("empreendedor");
        preecheRadioButton();
    }
    /**
     * Atualiza o projeto que está na sessão.
     */
    public void atualizarProjetoSessao() {
        HttpSession secao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        secao.setAttribute("projetoSelecionado", projeto);
    }

    /**
     * Preeche o radio button se o projeto já estiver com ele preenchido
     */
    public void preecheRadioButton() {
        if (projeto != null && projeto.getProdutoouservico() != null && projeto.getProdutoouservico().getEstagioEvolucao() != null) {
            if (projeto.getProdutoouservico().verificaStatusProjeto(projeto.getProdutoouservico().getEstagioEvolucao()).equals("Outro:")) {
                selectedButton = projeto.getProdutoouservico().verificaStatusProjeto(projeto.getProdutoouservico().getEstagioEvolucao());
                descricaoButtonOutro = projeto.getProdutoouservico().getEstagioEvolucao();
            } else {
                selectedButton = projeto.getProdutoouservico().verificaStatusProjeto(projeto.getProdutoouservico().getEstagioEvolucao());
            }
        }
    }

    public void pegaValorRadioButton() {
        if (selectedButton != null) {
            switch (selectedButton) {
                case "Ideia Básica":
                    projeto.getProdutoouservico().setEstagioEvolucao("1");
                    descricaoButtonOutro = null;
                    break;
                case "Projeto básico":
                    projeto.getProdutoouservico().setEstagioEvolucao("2");
                    descricaoButtonOutro = null;
                    break;
                case "Projeto detalhado":
                    projeto.getProdutoouservico().setEstagioEvolucao("3");
                    descricaoButtonOutro = null;
                    break;
                case "Protótipo desenvolvido":
                    projeto.getProdutoouservico().setEstagioEvolucao("4");
                    descricaoButtonOutro = null;
                    break;
                case "Em teste no mercado":
                    projeto.getProdutoouservico().setEstagioEvolucao("5");
                    descricaoButtonOutro = null;
                    break;
                case "Clientes Pagando":
                    projeto.getProdutoouservico().setEstagioEvolucao("6");
                    descricaoButtonOutro = null;
                    break;
                case "Outro:":
                    projeto.getProdutoouservico().setEstagioEvolucao(descricaoButtonOutro);
                    descricaoButtonOutro = null;
                    break;

                default:

            }
        }
    }

    public void salvarProjeto() {
        pegaValorRadioButton();
        EnviaEmails(projeto);
        ProjetoDao daoProj = new ProjetoDao();
        projeto = daoProj.salvarRetornandoProjeto(projeto);
        atualizarProjetoSessao();
    }

    public void salvarProjetoeSair() {
        salvarProjeto();

    }

    /**
     * Envia emails de termino de cadastro para os empreendedores necessários
     * dentro do projeto e atauliza os empreendedores.
     *
     * @param projeto Projeto que contém os empreendedores para se envar os
     * emails
     */
    public void EnviaEmails(Projeto projeto) {
        Empreendedor empreendedor, empreendedorCadastrado;

        for (Object object : projeto.getEmpreendedores()) {
            empreendedor = (Empreendedor) object;
            empreendedorCadastrado = Empreendedor.buscaEmpreendedorID(empreendedor.getIdUnico());
            enviarEmailCadastro(empreendedor, projeto);
        }
    }

    /**
     * Envia um email para que o empreendedor possa terminar o seu cadastro.
     *
     * @param empreendedor Empreendedor para se colocar o IDUnico e enviar o
     * email.
     * @param projeto Projeto para se adicionar o empreendedor atualizado.
     */
    public void enviarEmailCadastro(Empreendedor empreendedor, Projeto projeto) {
        String idUnico;
        if (!Empreendedor.verificaDadosEmpreendedor(empreendedor)) {
            if (empreendedor.getIdUnico() == null) {
                idUnico = UUID.randomUUID().toString();
                EmailUtil.mandarEmailConcluirCadastro(empreendedorSession.getNome(), projeto.getNome(), empreendedor.getEmail(), idUnico);
                empreendedor.setIdUnico(idUnico);
                empreendedor.atualizarEmpreendedor(empreendedor);
            }
        }
    }

    public List<String> completarEmpreendedor(String busca) {
        List<String> listaFiltrada = new ArrayList<>();

        for (Empreendedor empreendedor : getListaEmpreendedor()) {
            if (empreendedor.getEmail().toLowerCase().startsWith(busca)) {
                listaFiltrada.add(empreendedor.getEmail());
            }
        }

        return listaFiltrada;
    }

    /**
     *
     * @return true se o empreendedor não for o dono do projeto
     */
    public Boolean verificarEmpreendedorBoss(Empreendedor emp2) {
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Empreendedor empreendedor = (Empreendedor) sessao.getAttribute("empreendedor");
        projeto = (Projeto) sessao.getAttribute("projetoSelecionado");

        if (empreendedor.getEmail().equals(emp2.getEmail())) {
            return true;
        }

        return false;
    }

    /**
     * Deletar empreendedor de um projeto desde que ele não seja o dono do mesmo
     */
    public void deletarEmpreendedor() {

        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Empreendedor empreendedor = (Empreendedor) sessao.getAttribute("empreendedor");
        if (!empreendedor.getEmail().equals(empreendedorSelected.getEmail())) {
            projeto.getEmpreendedores().remove(empreendedorSelected);
            getEmpreedendoresAdicionados().remove(empreendedorSelected);
        }
    }

    /**
     * Adiciona o Empreendedor ao projeto.
     */
    public void adicionarEmpreendedor() {
        if (emailEmpreendedor.equals("")) {
            FacesUtil.addErrorMessage("Adicione um email valido para cadastrar um novo empreendedor", "formulario_cadastro_projeto:autocomplete");
        } else {

            boolean existe = false;
            Empreendedor empreendedorAchado = null;
            for (Empreendedor empreendedor : listaEmpreendedor) {
                if (empreendedor.getEmail().equals(emailEmpreendedor)) {
                    existe = true;
                    empreendedorAchado = empreendedor;
                    break;
                }
            }

            if (existe == false) {
                Empreendedor empreendedor = new Empreendedor();
                empreendedor.setEmail(emailEmpreendedor);
                empreendedorAchado = empreendedor;
            }
            if (!verificarLista(empreedendoresAdicionados, empreendedorAchado)) {
                if (!existe) {
                    empreendedorAchado.cadastrarEmpreendedor(empreendedorAchado);
                    empreendedorAchado = Empreendedor.buscaPorEmail(emailEmpreendedor);
                }
                getEmpreedendoresAdicionados().add(empreendedorAchado);
                projeto.getEmpreendedores().add(empreendedorAchado);
            } else {
                FacesUtil.addErrorMessage("Empreendedor já adicionado", "formulario_cadastro_projeto:autocomplete");
            }
        }
    }

    /**
     * Verifica se o empreendedor disponibilizado está na lista.
     *
     * @param empreendedores
     * @param empreendedorAchado
     * @return True se ele está presente na lista. ou se já esta no projeto!
     */
    public boolean verificarLista(List<Empreendedor> empreendedores, Empreendedor empreendedorAchado) {
        for (Object emp : projeto.getEmpreendedores()) {
            Empreendedor empree = (Empreendedor) emp;
            if (empree.getEmail().equals(empreendedorAchado.getEmail())) {
                return true;
            }
        }
        for (Empreendedor empreendedore : empreendedores) {
            if (empreendedore.getEmail().equals(empreendedorAchado.getEmail())) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return the projeto
     */
    public Projeto getProjeto() {
        return projeto;
    }

    /**
     * @param projeto the projeto to set
     */
    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    /**
     * @return the analiseEmprego
     */
    public Analiseemprego getAnaliseEmprego() {
        return analiseEmprego;
    }

    /**
     * @param analiseEmprego the analiseEmprego to set
     */
    public void setAnaliseEmprego(Analiseemprego analiseEmprego) {
        this.analiseEmprego = analiseEmprego;
    }

    /**
     * @return the negocio
     */
    public Negocio getNegocio() {
        return negocio;
    }

    /**
     * @param negocio the negocio to set
     */
    public void setNegocio(Negocio negocio) {
        this.negocio = negocio;
    }

    /**
     * @return the produtoOuSevico
     */
    public Produtoouservico getProdutoOuSevico() {
        return produtoOuSevico;
    }

    /**
     * @param produtoOuSevico the produtoOuSevico to set
     */
    public void setProdutoOuSevico(Produtoouservico produtoOuSevico) {
        this.produtoOuSevico = produtoOuSevico;
    }

    /**
     * @return the planoFinanceiro
     */
    public Planofinanceiro getPlanoFinanceiro() {
        return planoFinanceiro;
    }

    /**
     * @param planoFinanceiro the planoFinanceiro to set
     */
    public void setPlanoFinanceiro(Planofinanceiro planoFinanceiro) {
        this.planoFinanceiro = planoFinanceiro;
    }

    /**
     * @return the emailEmpreendedor
     */
    public String getEmailEmpreendedor() {
        return emailEmpreendedor;
    }

    /**
     * @param emailEmpreendedor the emailEmpreendedor to set
     */
    public void setEmailEmpreendedor(String emailEmpreendedor) {
        this.emailEmpreendedor = emailEmpreendedor;
    }

    /**
     * @return the listaEmpreendedor
     */
    public List<Empreendedor> getListaEmpreendedor() {
        return listaEmpreendedor;
    }

    /**
     * @param listaEmpreendedor the listaEmpreendedor to set
     */
    public void setListaEmpreendedor(List<Empreendedor> listaEmpreendedor) {
        this.listaEmpreendedor = listaEmpreendedor;
    }

    /**
     * @return the empreedendoresAdicionados
     */
    public List<Empreendedor> getEmpreedendoresAdicionados() {
        return empreedendoresAdicionados;
    }

    /**
     * @param empreedendoresAdicionados the empreedendoresAdicionados to set
     */
    public void setEmpreedendoresAdicionados(List<Empreendedor> empreedendoresAdicionados) {
        this.empreedendoresAdicionados = empreedendoresAdicionados;
    }

    /**
     * @return the empreendedorSelected
     */
    public Empreendedor getEmpreendedorSelected() {
        return empreendedorSelected;
    }

    /**
     * @param empreendedorSelected the empreendedorSelected to set
     */
    public void setEmpreendedorSelected(Empreendedor empreendedorSelected) {
        this.empreendedorSelected = empreendedorSelected;
    }

    public void enviaNovoProjetoCadastrar() {
        Projeto pjto = new Projeto();
        Analiseemprego analiseemprego = new Analiseemprego();
        Produtoouservico produtoouservico = new Produtoouservico();
        Negocio negocio = new Negocio();
        Planofinanceiro planofinanceiro = new Planofinanceiro();
        ProjetoDao daoP = new ProjetoDao();

        pjto.setAnaliseemprego(analiseemprego);
        pjto.setNegocio(negocio);
        pjto.setPlanofinanceiro(planofinanceiro);
        pjto.setProdutoouservico(produtoouservico);
        pjto.getEmpreendedores().add(empreendedorSession);
        pjto.setStatus(Projeto.EM_EDICAO);
//        pjto.setNome("");

        Date data = new Date(System.currentTimeMillis());
        pjto.setDataEnvio(data);
//        pjto = (Projeto) daoP.salvarRetornandoProjeto(pjto);
        HttpSession secao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        empreendedorSession = Empreendedor.buscaPorEmail(empreendedorSession.getEmail());
        secao.setAttribute("empreendedor", empreendedorSession);
        secao.setAttribute("projetoSelecionado", pjto);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("enviarProjeto.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ProjetoBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean verificaCadastroProjeto() {
        HttpSession secao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Empreendedor emp = (Empreendedor) secao.getAttribute("empreendedor");
        return emp.getProjetos().isEmpty();
    }

    /**
     * Método verifica se todos os campos do formulario de cadastro de projeto
     * estão preenchidos.
     *
     * @return true se todos os campos do formulario estao preenchidos.
     * @since 21/09/2015
     */
    public int verificarCampos() {
        int FLAG = 0;
        if (projeto.getNome().trim().isEmpty()) {
            FacesUtil.addErrorMessage("Campo não pode estar vazio", "formulario_cadastro_projeto:empresaProjeto");
            FLAG = FLAG + 1;
        }
        if (projeto.getNegocio().getSegmentoClientes().trim().isEmpty()) {
            FacesUtil.addErrorMessage("Campo não pode estar vazio", "formulario_cadastro_projeto:segmentoDeClientes");
            FLAG = FLAG + 1;
        }
        if (projeto.getNegocio().getPropostaValor().trim().isEmpty()) {
            FacesUtil.addErrorMessage("Campo não pode estar vazio", "formulario_cadastro_projeto:propostaDeValor");
            FLAG = FLAG + 1;
        }
        if (projeto.getNegocio().getAtividadesChaves().trim().isEmpty()) {
            FacesUtil.addErrorMessage("Campo não pode estar vazio", "formulario_cadastro_projeto:atividadesChave");
            FLAG = FLAG + 1;
        }
        if (projeto.getAnaliseemprego().getRelacoesClientes().trim().isEmpty()) {
            FacesUtil.addErrorMessage("Campo não pode estar vazio", "formulario_cadastro_projeto:relacoComClientes");
            FLAG = FLAG + 1;
        }
        if (projeto.getAnaliseemprego().getParceriasChaves().trim().isEmpty()) {
            FacesUtil.addErrorMessage("Campo não pode estar vazio", "formulario_cadastro_projeto:parceriasChaves");
            FLAG = FLAG + 1;
        }
        if (projeto.getAnaliseemprego().getCanais().trim().isEmpty()) {
            FacesUtil.addErrorMessage("Campo não pode estar vazio", "formulario_cadastro_projeto:canais");
            FLAG = FLAG + 1;
        }
        if (projeto.getAnaliseemprego().getRecursosPrincipais().trim().isEmpty()) {
            FacesUtil.addErrorMessage("Campo não pode estar vazio", "formulario_cadastro_projeto:recursosPrincipais");
            FLAG = FLAG + 1;
        }
        if (projeto.getAnaliseemprego().getConcorrentes().trim().isEmpty()) {
            FacesUtil.addErrorMessage("Campo não pode estar vazio", "formulario_cadastro_projeto:concorrentes");
            FLAG = FLAG + 1;
        }
        if (selectedButton.equals("Outro:") && descricaoButtonOutro.trim().isEmpty()) {
            FacesUtil.addErrorMessage("Se a opção selecionada for (Outro) então o campo acima não pode estar vazio", "formulario_cadastro_projeto:descricaoOutroEstagio");
            FLAG = FLAG + 1;
        }
        if (projeto.getProdutoouservico().getTecnologiaProcessos().trim().isEmpty()) {
            FacesUtil.addErrorMessage("Campo não pode estar vazio", "formulario_cadastro_projeto:tecnologiaProcessos");
            FLAG = FLAG + 1;
        }
        if (projeto.getProdutoouservico().getPotencialInovacaoTecnologica().trim().isEmpty()) {
            FacesUtil.addErrorMessage("Campo não pode estar vazio", "formulario_cadastro_projeto:potencialInovacaoTecnologica");
            FLAG = FLAG + 1;
        }
        if (projeto.getProdutoouservico().getAplicacoes().trim().isEmpty()) {
            FacesUtil.addErrorMessage("Campo não pode estar vazio", "formulario_cadastro_projeto:aplicacoes");
            FLAG = FLAG + 1;
        }
        if (projeto.getProdutoouservico().getDificuldadesEsperadas().trim().isEmpty()) {
            FacesUtil.addErrorMessage("Campo não pode estar vazio", "formulario_cadastro_projeto:dificuldadesEsperadas");
            FLAG = FLAG + 1;
        }
        if (projeto.getProdutoouservico().getInteracaoEmpresaUniversidade().trim().isEmpty()) {
            FacesUtil.addErrorMessage("Campo não pode estar vazio", "formulario_cadastro_projeto:interacaoEmpresaUniversidade");
            FLAG = FLAG + 1;
        }
        if (projeto.getProdutoouservico().getInteracaoEmpresaComunidadeGoverno().trim().isEmpty()) {
            FacesUtil.addErrorMessage("Campo não pode estar vazio", "formulario_cadastro_projeto:interacaoEmpresaComunidadeGoverno");
            FLAG = FLAG + 1;
        }
        if (projeto.getProdutoouservico().getInfraestrutura().trim().isEmpty()) {
            FacesUtil.addErrorMessage("Campo não pode estar vazio", "formulario_cadastro_projeto:infraestrutura");
            FLAG = FLAG + 1;
        }
        if (projeto.getParticipacaoacionaria().trim().isEmpty()) {
            FacesUtil.addErrorMessage("Campo não pode estar vazio", "formulario_cadastro_projeto:participacaoAcionaria");
            FLAG = FLAG + 1;
        }
        if (projeto.getPotencialEmprego().trim().isEmpty()) {
            FacesUtil.addErrorMessage("Campo não pode estar vazio", "formulario_cadastro_projeto:potencialEmprego");
            FLAG = FLAG + 1;
        }
        if (projeto.getPlanofinanceiro().getFontesReceita().trim().isEmpty()) {
            FacesUtil.addErrorMessage("Campo não pode estar vazio", "formulario_cadastro_projeto:fontesDeReceita");
            FLAG = FLAG + 1;
        }
        if (projeto.getPlanofinanceiro().getEstruturaCusto().trim().isEmpty()) {
            FacesUtil.addErrorMessage("Campo não pode estar vazio", "formulario_cadastro_projeto:estruturaCustos");
            FLAG = FLAG + 1;
        }
        if (projeto.getPlanofinanceiro().getInvestimentoInicial().trim().isEmpty()) {
            FacesUtil.addErrorMessage("Campo não pode estar vazio", "formulario_cadastro_projeto:investimentoInicial");
            FLAG = FLAG + 1;
        }
        if (projeto.getPlanofinanceiro().getCustosfixos().trim().isEmpty()) {
            FacesUtil.addErrorMessage("Campo não pode estar vazio", "formulario_cadastro_projeto:custosfixos");
            FLAG = FLAG + 1;
        }
        if (projeto.getPlanofinanceiro().getCustosvariaveis().trim().isEmpty()) {
            FacesUtil.addErrorMessage("Campo não pode estar vazio", "formulario_cadastro_projeto:custosvariaveis");
            FLAG = FLAG + 1;
        }
        return FLAG;
    }

    /**
     * Envia o projeto para a avaliação.
     */
    public void enviarProjeto() {
        int FLAG = verificarCampos();
        int FLAG_STATUS = 0;

        if (FLAG > 0) {
            FacesUtil.addErrorMessage("Sistema encontrou " + FLAG + " campos não preenchidos",
                    "formulario_cadastro_projeto:tituloMensagem");

        } else {
            try {

                HttpSession secao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                Empreendedor emp = (Empreendedor) secao.getAttribute("empreendedor");

                if (projeto.getStatus() == Empreendedor.ENVIADO) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("PaginaBuscaProjeto.xhtml");
                } else {
                    salvarProjeto();
                    if (emp.enviarProjeto(projeto) == Empreendedor.ENVIADO) {
                        System.out.println("status enviado");
//                        salvarProjeto();
                        atualizarProjetoSessao();
                        FacesContext.getCurrentInstance().getExternalContext().redirect("PaginaBuscaProjeto.xhtml");
                    } else {

                        FacesUtil.addErrorMessage("Ainda há Empreendedores que precisam terminar o cadastro no sistema.",
                                "formulario_cadastro_projeto:tituloMensagem");
                    }
                }

            } catch (Exception e) {
                System.out.println("exeção = " + e);
            }
        }

    }

    /**
     * @return the selectedButton
     */
    public String getSelectedButton() {
        return selectedButton;
    }

    /**
     * @param selectedButton the selectedButton to set
     */
    public void setSelectedButton(String selectedButton) {
        this.selectedButton = selectedButton;
    }

    /**
     * @return the descricaoButtonOutro
     */
    public String getDescricaoButtonOutro() {
        return descricaoButtonOutro;
    }

    /**
     * @param descricaoButtonOutro the descricaoButtonOutro to set
     */
    public void setDescricaoButtonOutro(String descricaoButtonOutro) {
        this.descricaoButtonOutro = descricaoButtonOutro;
    }
}
