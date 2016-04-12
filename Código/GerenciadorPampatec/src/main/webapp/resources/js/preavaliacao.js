/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function carregaPagina() {

    var etapa1 = document.getElementById("etapa1");
    var etapa2 = document.getElementById("etapa2");
    var etapa3 = document.getElementById("etapa3");
    var etapa4 = document.getElementById("etapa4");
    var etapa5 = document.getElementById("etapa5");

    switch (andamentoProjeto) {

//      SUBMETIDO = 1 e RESUBMETIDO = 11;
        case 1 || 11:
            etapa3.innerHTML = "<b>Avaliação</b>";
            etapa4.innerHTML = "<b>Formalização</b>";
            etapa5.innerHTML = "<b>Incubação</b>";
            etapa1.setAttribute("class", "active");
            etapa2.setAttribute("class", "active, etapaAtual");
            var botao_preavaliacao = document.getElementById("menuSuperior:botao_resultado_preavaliacao");

            etapaAtualDoWorkflow = "etapa2";

            mostraDIV('divPreVisualizar');
            botao_preavaliacao.setAttribute("class", "btn btn-danger btnEstadoAtual");

            var etapa = document.getElementById("etapa3");
            etapa.setAttribute("style", "cursor: default;");
            var etapa = document.getElementById("etapa4");
            etapa.setAttribute("style", "cursor: default;");
            var etapa = document.getElementById("etapa5");
            etapa.setAttribute("style", "cursor: default;");

            break;

//      ACEITO PARA AVALIAÇÃO;    
        case 2:
            etapa4.innerHTML = "<b>Formalização</b>";
            etapa5.innerHTML = "<b>Incubação</b>";
            etapa1.setAttribute("class", "active");
            etapa2.setAttribute("class", "active");
            etapa3.setAttribute("class", "active, etapaAtual");
            
            etapaAtualDoWorkflow = "etapa3";
            
            mostraDIV('sessao_resultado_avaliador');
            var botaoResultadoPreAvaliacao = document.getElementById("menuSuperior:botao_resultado_preavaliacao");
            botaoResultadoPreAvaliacao.setAttribute("class", "btn btn-danger btnEstadoAtual");
            

            var etapa = document.getElementById("etapa4");
            etapa.setAttribute("style", "cursor: default;");
            var etapa = document.getElementById("etapa5");
            etapa.setAttribute("style", "cursor: default;");

            break;

//      REPROVADO = 6;
        case 6:
            etapa3.innerHTML = "<b>Avaliação</b>";
            etapa4.innerHTML = "<b>Formalização</b>";
            etapa5.innerHTML = "<b>Incubação</b>";
            etapa1.setAttribute("class", "active");
            etapa2.setAttribute("class", "active, etapaAtual");
            var botaoResultadoPreAvaliacao = document.getElementById("menuSuperior:botao_resultado_preavaliacao");
            botaoResultadoPreAvaliacao.setAttribute("class", "btn btn-danger btnEstadoAtual");

            mostraDIV('sessao_resultado_avaliador');

            var etapa = document.getElementById("etapa3");
            etapa.setAttribute("style", "cursor: default;");
            var etapa = document.getElementById("etapa4");
            etapa.setAttribute("style", "cursor: default;");
            var etapa = document.getElementById("etapa5");
            etapa.setAttribute("style", "cursor: default;");
            break;
            
//     NECESSITA_MELHORIA = 7
        case 7:
            etapa3.innerHTML = "<b>Avaliação</b>";
            etapa4.innerHTML = "<b>Formalização</b>";
            etapa5.innerHTML = "<b>Incubação</b>";
            etapa1.setAttribute("class", "active");
            etapa2.setAttribute("class", "active, etapaAtual");
            var botaoResultadoPreAvaliacao = document.getElementById("menuSuperior:botao_resultado_preavaliacao");
            botaoResultadoPreAvaliacao.setAttribute("class", "btn btn-danger btnEstadoAtual");

            mostraDIV('sessao_resultado_avaliador');

            var etapa = document.getElementById("etapa3");
            etapa.setAttribute("style", "cursor: default;");
            var etapa = document.getElementById("etapa4");
            etapa.setAttribute("style", "cursor: default;");
            var etapa = document.getElementById("etapa5");
            etapa.setAttribute("style", "cursor: default;");
            break;
            
        default :
            break;
    }
}
/**
 * @description Função que exibe o menu vertical referente a etapa de elaboração
 * @returns {undefined}
 */
function mostra_vertical_elaboracao() {

    var divElaboracao = document.getElementById("vertical_etapa_elaboracao");
    divElaboracao.setAttribute("class", "col-md-2 text-center bounceInLeft animated estiloDivVertical noprint");
    var divPreAvaliacao = document.getElementById("vertical_etapa_pre_avaliacao");
    divPreAvaliacao.setAttribute("class", "esconder-div");
    var divAvaliacao = document.getElementById("vertical_etapa_avaliacao");
    divAvaliacao.setAttribute("class", "esconder-div");
    var divFormalizacao = document.getElementById("vertical_etapa_formalizacao");
    divFormalizacao.setAttribute("class", "esconder-div");
    var divIncubacao = document.getElementById("vertical_etapa_incubacao");
    divIncubacao.setAttribute("class", "esconder-div");
}



/**
 * @description Função que exibe o menu vertical referente a etapa de pré avaliação
 * @returns {undefined}
 */
function mostra_vertical_pre_avaliacao() {


    var divElaboracao = document.getElementById("vertical_etapa_elaboracao");
    divElaboracao.setAttribute("class", "esconder-div");
    var divPreAvaliacao = document.getElementById("vertical_etapa_pre_avaliacao");
    divPreAvaliacao.setAttribute("class", "col-md-2 text-center bounceInLeft animated estiloDivVertical noprint");
    var divAvaliacao = document.getElementById("vertical_etapa_avaliacao");
    divAvaliacao.setAttribute("class", "esconder-div");
    var divFormalizacao = document.getElementById("vertical_etapa_formalizacao");
    divFormalizacao.setAttribute("class", "esconder-div");
    var divIncubacao = document.getElementById("vertical_etapa_incubacao");
    divIncubacao.setAttribute("class", "esconder-div");


}

/**
 * @description Função que exibe o menu vertical referente a etapa de Avaliação
 * @returns {undefined}
 */
function mostra_avaliacao() {

    var divElaboracao = document.getElementById("vertical_etapa_elaboracao");
    divElaboracao.setAttribute("class", "esconder-div");
    var divPreAvaliacao = document.getElementById("vertical_etapa_pre_avaliacao");
    divPreAvaliacao.setAttribute("class", "esconder-div");
    var divAvaliacao = document.getElementById("vertical_etapa_avaliacao");
    divAvaliacao.setAttribute("class", "col-md-2 text-center bounceInLeft animated estiloDivVertical noprint");
    var divFormalizacao = document.getElementById("vertical_etapa_formalizacao");
    divFormalizacao.setAttribute("class", "esconder-div");
    var divIncubacao = document.getElementById("vertical_etapa_incubacao");
    divIncubacao.setAttribute("class", "esconder-div");

}

/**
 * @description Função que exibe o menu vertical referente a etapa de Formalização
 * @returns {undefined}
 */
function mostra_formalizacao() {

    var divElaboracao = document.getElementById("vertical_etapa_elaboracao");
    divElaboracao.setAttribute("class", "esconder-div");
    var divPreAvaliacao = document.getElementById("vertical_etapa_pre_avaliacao");
    divPreAvaliacao.setAttribute("class", "esconder-div");
    var divAvaliacao = document.getElementById("vertical_etapa_avaliacao");
    divAvaliacao.setAttribute("class", "esconder-div");
    var divFormalizacao = document.getElementById("vertical_etapa_formalizacao");
    divFormalizacao.setAttribute("class", "col-md-2 text-center bounceInLeft animated estiloDivVertical noprint");
    var divIncubacao = document.getElementById("vertical_etapa_incubacao");
    divIncubacao.setAttribute("class", "esconder-div");


}

/**
 * @description Função que exibe o menu vertical referente a etapa de Incubação
 * @returns {undefined}
 */
function mostra_incubacao() {

    var divElaboracao = document.getElementById("vertical_etapa_elaboracao");
    divElaboracao.setAttribute("class", "esconder-div");
    var divPreAvaliacao = document.getElementById("vertical_etapa_pre_avaliacao");
    divPreAvaliacao.setAttribute("class", "esconder-div");
    var divAvaliacao = document.getElementById("vertical_etapa_avaliacao");
    divAvaliacao.setAttribute("class", "esconder-div");
    var divFormalizacao = document.getElementById("vertical_etapa_formalizacao");
    divFormalizacao.setAttribute("class", "esconder-div");
    var divIncubacao = document.getElementById("vertical_etapa_incubacao");
    divIncubacao.setAttribute("class", "col-md-2 text-center bounceInLeft animated estiloDivVertical noprint");

}

/**
 * @description Função que bloqueia todos os campos da tela Enviar Projeto. Para o empreendedor apenas poder visualizar e não editar.
 * @returns {undefined}
 */
function bloquearCampos() {
    var d = document.getElementById('myTabContent').getElementsByTagName('input');
    var botaoOutro = document.getElementById('formulario_cadastro_projeto:estagioDeEvolucao');
    botaoOutro.disabled = "true";

    for (var i = 0; i < d.length; i++) {
        d[i].disabled = "true";
    }
    var d2 = document.getElementById('myTabContent').getElementsByTagName('textarea');
    for (var i = 0; i < d2.length; i++) {
        d2[i].disabled = "true";
    }
}


/**
 * @description Função para alternar entre as DIVs
 * @param {type} referencia
 * @returns {undefined}
 */
function mostraDIV(referencia) {
    div1 = document.getElementsByClassName("classeConteudo");
    for (var i = 0; i < div1.length; i++) {

        if (div1[i].getAttribute("id") === referencia) {
            div1[i].setAttribute("class", "classeConteudo col-md-10");
        } else {
            div1[i].setAttribute("class", "classeConteudo esconder-div");
        }
    }
}
/**
 * @description Esta função é chamada para validar que quando o plano está em pre avalaição os empreendedores poderão só Revisar o plano.
 * @param {type} referencia
 */
function mostraDIV2(referencia) {
    // se andamento está em elaboração
    if (andamentoProjeto === 0) {
        // se o empreendedor é correspondente
        if (tipoEmpreededor) {
            mostraDIV(referencia);
        } else {
            // se empreendedor não é correspondente, apenas mostra plano para revisar
            mostraDIV('div_revisar_plano');
        }
    } else {
        mostraDIV('div_revisar_plano');
    }
}


