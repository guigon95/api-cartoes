package com.guigon.api_cartoes.domain.exceptions

class CriteriosJovemException(mensagem: String = "O cliente não atende aos critérios de salário para o cartão") : CriteriosNaoAceitosException(mensagem)