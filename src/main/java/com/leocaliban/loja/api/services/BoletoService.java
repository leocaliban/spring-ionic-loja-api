package com.leocaliban.loja.api.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.leocaliban.loja.api.domain.PagamentoBoleto;

@Service
public class BoletoService {

	public void realizarPagamentoComBoleto(PagamentoBoleto pagamento, Date dataDoPedido) {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(dataDoPedido);
		calendario.add(Calendar.DAY_OF_MONTH, 7);
		pagamento.setDataVencimento(calendario.getTime());
	}
}
