package com.bank.account.domain;

import com.bank.account.exception.CustomFunctionalException;
import com.bank.account.exception.CustomInternalException;
import com.bank.account.exception.MessageError;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ClientAccount implements AccountOperations {
	private static final Comparator<OperationHistory> sortHistoriesByDate =
			Comparator.comparing(OperationHistory::date).reversed();
	private static final MathContext mc = new MathContext(4);

	private BigDecimal currentBalance = BigDecimal.valueOf(0);

	private final List<OperationHistory> hitories = new ArrayList<>();


	@Override
	public BigDecimal getCurrentBalance() {
		return currentBalance;
	}

	@Override
	public List<OperationHistory> getHitories() {
		hitories.sort(sortHistoriesByDate);
		return hitories;
	}

	@Override
	public void deposit(BigDecimal amount) {
		if (amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new CustomInternalException(MessageError.AMOUNT_NEGATIVE);
		}
		this.currentBalance = this.currentBalance.add(amount, mc);
		addHistory(OperationEnum.DEPOSIT, amount);
	}

	@Override
	public void retrieve(BigDecimal amount) {
		if (this.currentBalance.compareTo(amount) < 0) {
			throw new CustomFunctionalException(MessageError.DO_NOT_HAVE_ENOUGH_BALANCE);
		}
		if (amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new CustomInternalException(MessageError.AMOUNT_NEGATIVE);
		}
		this.currentBalance = this.currentBalance.subtract(amount, mc);
		addHistory(OperationEnum.RETRIEVE, amount);
	}

	private void addHistory(OperationEnum operation, BigDecimal amount) {
		OperationHistory history = new OperationHistory(operation, new Date(),
				amount, this.currentBalance);
		this.hitories.add(history);
	}
}
