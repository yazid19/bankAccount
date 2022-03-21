package com.bank.account.domain;

import com.bank.account.domain.ClientAccount;

import java.math.BigDecimal;
import java.math.MathContext;

public interface AccountOperations {

	void deposit(BigDecimal amount, MathContext mc);

	void retrieve(BigDecimal amount, MathContext mc);
}
