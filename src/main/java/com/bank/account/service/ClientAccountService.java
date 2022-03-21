package com.bank.account.service;

import com.bank.account.domain.ClientAccount;
import com.bank.account.domain.OperationHistory;
import com.bank.account.exception.CustomFunctionalException;
import com.bank.account.exception.CustomInternalException;
import com.bank.account.exception.MessageError;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Comparator;

public class ClientAccountService implements AccountOperations {
	static final MathContext mc = new MathContext(2);
	static final Comparator<OperationHistory> sortHistoriesByDate = Comparator.comparing(OperationHistory::date).reversed();

	@Override
	public void deposit(ClientAccount clientAccount, BigDecimal amount) {
		if (amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new CustomInternalException(MessageError.AMOUNT_NEGATIVE);
		}
		clientAccount.deposit(amount, mc);
	}

	@Override
	public void retrieve(ClientAccount clientAccount, BigDecimal amount) {
		if (clientAccount.getCurrentBalance().compareTo(amount) < 0) {
			throw new CustomFunctionalException(MessageError.DO_NOT_HAVE_ENOUGH_BALANCE);
		}
		if (amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new CustomInternalException(MessageError.AMOUNT_NEGATIVE);
		}
		clientAccount.retrieve(amount, mc);
	}
}
