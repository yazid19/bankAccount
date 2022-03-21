package com.bank.account.domain;

import java.math.BigDecimal;
import java.util.Date;

public record OperationHistory(OperationEnum operation,
							   Date date,
							   BigDecimal amount,
							   BigDecimal newBalance) {


}
