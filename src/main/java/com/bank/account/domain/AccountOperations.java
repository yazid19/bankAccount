package com.bank.account.domain;


import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

public interface AccountOperations {

	/**
	 * deposit amount in balance
	 * @param amount
	 */
	void deposit(BigDecimal amount);

	/**
	 * retrieve amount from balance
	 * @param amount
	 */
	void retrieve(BigDecimal amount);

	/**
	 * get current balance
	 * @return
	 */
	BigDecimal getCurrentBalance();

	/**
	 *
	 * @return List of operations histories
	 */
	List<OperationHistory> getHitories();
}
