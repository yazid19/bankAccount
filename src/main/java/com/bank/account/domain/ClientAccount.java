package com.bank.account.domain;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClientAccount {

	private BigDecimal currentBalance = BigDecimal.valueOf(0);

	private final List<OperationHistory> hitories = new ArrayList<>();


	public BigDecimal getCurrentBalance() {
		return currentBalance;
	}

	public List<OperationHistory> getHitories() {
		return hitories;
	}

	public void deposit(BigDecimal amount, MathContext mc) {
		this.currentBalance = this.currentBalance.add(amount, mc);
		addHistory(OperationEnum.DEPOSIT, amount);
	}

	public void retrieve(BigDecimal amount, MathContext mc) {
		this.currentBalance = this.currentBalance.subtract(amount, mc);
		addHistory(OperationEnum.RETRIEVE, amount);
	}

	public void addHistory(OperationEnum operation, BigDecimal amount) {
		OperationHistory history = new OperationHistory(operation, new Date(),
				amount, this.currentBalance);
		this.hitories.add(history);
	}
}
