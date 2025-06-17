package com.cashcontrol.cashcontrol_api.model;

/**
 * Enumeração dos tipos de transação suportados pelo sistema.
 *
 * <ul>
 *   <li>{@link #APOSTA} – Uma aposta realizada pelo usuário.</li>
 *   <li>{@link #INVESTIMENTO} – Um valor investido pelo usuário (em simulações, etc).</li>
 *   <li>{@link #RETORNO} – Retorno financeiro recebido (ex: prêmio ou resgate).</li>
 * </ul>
 *
 * Exemplo de uso:
 * <pre>
 * Transacao t = new Transacao();
 * t.setTipo(TipoTransacao.APOSTA);
 * </pre>
 */
public enum TipoTransacao {
    APOSTA,
    INVESTIMENTO,
    RETORNO
}
