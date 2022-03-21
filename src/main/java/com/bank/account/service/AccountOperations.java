package com.bank.account.service;

import com.bank.account.domain.ClientAccount;

import java.math.BigDecimal;

public interface AccountOperations {

	void deposit(ClientAccount clientAccount, BigDecimal amount);

	void retrieve(ClientAccount clientAccount, BigDecimal amount);
}
