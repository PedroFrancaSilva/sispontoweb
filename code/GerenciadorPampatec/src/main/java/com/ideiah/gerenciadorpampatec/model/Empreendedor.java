package com.ideiah.gerenciadorpampatec.model;
// Generated 31/08/2015 13:49:28 by Hibernate Tools 4.3.1

import com.ideiah.gerenciadorpampatec.dao.ProjetoDao;
import com.ideiah.gerenciadorpampatec.dao.EmpreendedorDao;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.ejb.EJB;

/**
 * Empreendedor generated by hbm2java
 */
public class Empreendedor extends Usuario implements java.io.Serializable {

    public static final int FALTANDO_DADOS = 0;
    public static final int ENVIADO = 1;
    public static final int ERRO_AO_SALVAR = 2;

    private String formacao;
    private String experiencia;
    private String bairro;
    private String rua;
    private Integer numero;
    private String complemento;
    private String idUnico;
    private Set projetos = new HashSet(0);
    private Set<Notificacao> notificacoes = new HashSet<>();

    public Empreendedor() {
    }

    /**
     * @return the empreededorDao
     */
    public static EmpreendedorDao getEmpreededorDao() {
        return new EmpreendedorDao();
    }

    public void setIdUnico(String idUnico) {
        this.idUnico = idUnico;
    }

    public String getIdUnico() {
        return idUnico;
    }

    /**
     * @return the projetoDao
     */
    public static ProjetoDao getProjetoDao() {
        return new ProjetoDao();
    }

    public Empreendedor(Integer idUsuario, String nome, String cpf, String formacao, String email, String senha, String telefone, String rua, int numero, String bairro, String complemento) {
        this.setIdUsuario(idUsuario);
        this.setNome(nome);
        this.setCpf(cpf);
        this.setEmail(email);
        this.setSenha(senha);
        this.setTelefone(telefone);
        this.formacao = formacao;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.complemento = complemento;

    }

    public Empreendedor(Integer idUsuario, String nome, String telefone, String cpf, String email, String formacao, String experiencia, String competencia, String participacaoAcionaria, Set projetos, Set notificacoes, String senha) {
        this.setIdUsuario(idUsuario);
        this.setNome(nome);
        this.setCpf(cpf);
        this.setEmail(email);
        this.setSenha(senha);
        this.setTelefone(telefone);
        this.formacao = formacao;
        this.notificacoes = notificacoes;
    }

    public Empreendedor(Integer idUsuario, String nome, String telefone, String cpf, String email, String formacao, String experiencia, String participacaoAcionaria, Set projetos, Set notificacoes) {
        this.setIdUsuario(idUsuario);
        this.setNome(nome);
        this.setCpf(cpf);
        this.setEmail(email);
        this.setTelefone(telefone);
        this.formacao = formacao;
        this.experiencia = experiencia;
        this.projetos = projetos;
        this.notificacoes = notificacoes;
    }

    /**
     * Método usado na view enviarProjeto verifica se o empreendedor finalizou
     * seu cadastro
     *
     * @return cadastroCompleto se o empreendedor já finalizou o seu cadastro
     */
    public String retornaStatus() {
        if (verificaDadosEmpreendedor(this)) {
            return "Cadastro Completo";
        }
        return "Cadastro Incompleto";
    }

    /**
     * Verifica se o empreendedor correspondente disponibilizado é igual a este
     * empreendedor e retorna o tipo dele.
     *
     * @param empreendedorCorrespondente empreendedor correspondente para se
     * comparar.
     * @return
     */
    public String retornaTipoEmpreendedor(Empreendedor empreendedorCorrespondente) {
        if (Objects.equals(this.getIdUsuario(), empreendedorCorrespondente.getIdUsuario())) {
            return "Empreendedor Correspondente";
        } else {
            return "Empreendedor Observador";
        }
    }

    /**
     * Verifica se o empreendedor correspondente disponibilizado é igual a este
     * empreendedor.
     *
     * @param empreendedorCorrespondente
     * @return trur se o empreendedor disponibilizado é igual a esse
     * empreendedor.
     */
    public boolean verificaTipoEmpreendedor(Empreendedor empreendedorCorrespondente) {
        return Objects.equals(this.getIdUsuario(), empreendedorCorrespondente.getIdUsuario());
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

    public Set getProjetos() {
        return this.projetos;
    }

    public void setProjetos(Set projetos) {
        this.projetos = projetos;
    }

    public static Projeto salvarProjeto(Projeto projeto) {

        return getProjetoDao().salvar(projeto);
    }

    public static Projeto atualizarProjeto(Projeto projeto) {

        return getProjetoDao().update(projeto);
    }

    /**
     * Busca os empreendedores no banco.
     *
     * @return lista de empreendedores cadastrados.
     */
    public static List<Empreendedor> retornarEmpreendedores() {
        return getEmpreededorDao().buscar();
    }

    public Empreendedor cadastrarEmpreendedor(Empreendedor empreendedorNovo) {
//        boolean retorno = empreendedorDao.buscarDados(empreendedorNovo.getEmail(), empreendedorNovo.getNome());
//        int idEndereco = 0;
//        if (retorno == true) {
        return getEmpreededorDao().salvar(empreendedorNovo);
//        }
//        return false;
    }

    /**
     * Envia o projeto para a pré-avaliação. Altera o Status para EM PRE
     * AVALIAÇAO se for enviado com sucesso
     *
     * @param projeto Projeto para ser enviado
     * @return 0 se os empreendedores do projeto estão faltando dados, 1 se o
     * projeto foi enviado com sucesso e 2 se aconteceu algum erro ao salvar.
     */
    public int enviarProjeto(Projeto projeto) {

        if (!verificaDadosEmpreendedores(projeto)) {
            return FALTANDO_DADOS;
        } else if (projeto.getStatus() != ENVIADO) {
            projeto.mudarStatus(Projeto.SUBMETIDO);
        }

        Date data = new Date(System.currentTimeMillis());
        projeto.setDataEnvio(data);
        if (getProjetoDao().update(projeto) != null) {
            return ENVIADO;
        } else {
            return ERRO_AO_SALVAR;
        }
    }

    /**
     * Verifica se os empreendedores do projetos estão completamentes
     * cadastrados.
     *
     * @param projeto Projeto para se verificar os empreendedores
     * @return true se todos os empreendedores estão completamente cadastrados.
     */
    public boolean verificaDadosEmpreendedores(Projeto projeto) {
        Empreendedor empreendedor;
        for (Object objEmpreendedor : projeto.getEmpreendedores().toArray()) {
            empreendedor = (Empreendedor) objEmpreendedor;
            if (!verificaDadosEmpreendedor(empreendedor)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Verifica se o Empreendedor foi totalmente cadastrado no sistema.
     *
     * @param empreendedor Empreendedor para se verificar a completude.
     * @return true se ele está completamente cadastrado.
     */
    public static boolean verificaDadosEmpreendedor(Empreendedor empreendedor) {
        boolean completo = true;
        if (empreendedor.getCpf() == null) {
            completo = false;
        }
        return completo;
    }

    public Empreendedor atualizarEmpreendedor(Empreendedor emp) {
        return (Empreendedor) getEmpreededorDao().update(emp);
    }

    public Empreendedor buscarPorEmail(String user) {
        return getEmpreededorDao().buscarPorEmail(user);
    }

    public Empreendedor buscarPorCpf(String user) {
        return getEmpreededorDao().buscarPorCpf(user);
    }

    /*
     *
     */
    public void realizarCadastro() {
        getEmpreededorDao().salvar(this);
    }

    public static ArrayList<Empreendedor> buscaEmpreendedores() {
        return getEmpreededorDao().buscar();
    }

    /**
     * @return the numero
     */
    public Integer getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    /**
     * @return the complemento
     */
    public String getComplemento() {
        return complemento;
    }

    /**
     * @param complemento the complemento to set
     */
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    /**
     * @return the bairro
     */
    public String getBairro() {
        return bairro;
    }

    /**
     * @param bairro the bairro to set
     */
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    /**
     * @return the rua
     */
    public String getRua() {
        return rua;
    }

    /**
     * @param rua the rua to set
     */
    public void setRua(String rua) {
        this.rua = rua;
    }

    public boolean deletarEmpreendedor(Empreendedor empreendedor) {
        return getEmpreededorDao().deletarPorEmail(empreendedor.getEmail());
    }
//VERIFICA SE O EMPREENDEDOR EXISTE EM ALGUM PROJETO DO BANCO

    public boolean verificarProjetoHasEmpreendedor(Empreendedor empreendedor) {
        for (Projeto projeto : getProjetoDao().buscar()) {
            Empreendedor emp;
            for (Object object : projeto.getEmpreendedores()) {
                emp = (Empreendedor) object;
                if (emp.getEmail().equals(empreendedor.getEmail())) {
                    return true;

                }

            }

//            if (projeto.getEmpreendedors().equals(empreendedor)) {
//                return true;
//                
//            }
        }
        return false;
    }

//VERIFICA SE O EMPREENDEDOR ESTA CONTIDO NO PROJETO PASSADO
    public boolean verificaProjetoEmpreendedor(Empreendedor empreendedor, Projeto proj) {
        return getProjetoDao().verificaEmpreendedor(empreendedor, proj);
    }

    public static Empreendedor buscaEmpreendedorID(String id) {
        return getEmpreededorDao().buscarPorIdUnico(id);
    }

    public static Empreendedor buscaPorEmail(String email) {
        return getEmpreededorDao().buscarPorEmail(email);
    }

    /**
     * @return the notificacoes
     */
    public Set<Notificacao> getNotificacoes() {
        return notificacoes;
    }

    /**
     * @param notificacoes the notificacoes to set
     */
    public void setNotificacoes(Set<Notificacao> notificacoes) {
        this.notificacoes = notificacoes;
    }

    /**
     *
     * @return quantidade de notificacoes
     */
    public int getQuantidadeDeNotificacoes() {
        int quantidadeNotificacoes = notificacoes.size();
        for (Notificacao notificacao : notificacoes) {
            if (notificacao.isVisualizado()) {
                quantidadeNotificacoes--;
            }
        }
        return quantidadeNotificacoes;
    }

    /**
     *
     * @return ArrayList com as descrições de todas as notificações
     */
    public ArrayList<String> getDescricaoDasNotificacoes() {
        ArrayList<String> descricoes = new ArrayList<String>();

        for (Notificacao notificacao : notificacoes) {
            descricoes.add(notificacao.getDescricao());
        }
        return descricoes;
    }

    public void removeCustoProjeto(Custo custo) {
        ProjetoDao dao = new ProjetoDao();
        dao.excluir(custo.getIdCusto(), Custo.class);
    }
}