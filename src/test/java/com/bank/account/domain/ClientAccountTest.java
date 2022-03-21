package com.bank.account.domain;

import com.bank.account.exception.CustomFunctionalException;
import com.bank.account.exception.CustomInternalException;
import com.bank.account.exception.MessageError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class ClientAccountTest {

	ClientAccount account;

	@BeforeEach
	void setUp() {
		account = new ClientAccount();
	}


	@Test
	public void when_deposit_expected_add_into_balance() {
		account.deposit(BigDecimal.valueOf(36.3666));
		assertEquals(BigDecimal.valueOf(36.37), account.getCurrentBalance());
		assertEquals(account.getHitories().size(), 1);
		assertEquals(account.getHitories().get(0).operation(), OperationEnum.DEPOSIT);
		assertEquals(account.getHitories().get(0).newBalance(), BigDecimal.valueOf(36.37));
	}

	@Test
	public void when_retrieve_expected_remove_from_balance() {
		account.deposit(BigDecimal.valueOf(36.55));
		account.retrieve(BigDecimal.valueOf(15.66));
		assertEquals(BigDecimal.valueOf(20.89), account.getCurrentBalance());
		assertEquals(account.getHitories().size(), 2);
		assertEquals(account.getHitories().get(1).operation(), OperationEnum.RETRIEVE);
		assertEquals(account.getHitories().get(1).newBalance(), BigDecimal.valueOf(20.89));
	}

	@Test
	public void when_deposit_amount_negative_expected_exception() {
		CustomInternalException exception = assertThrows(CustomInternalException.class,
				() -> account.deposit(BigDecimal.valueOf(-15)));
		assertEquals(MessageError.AMOUNT_NEGATIVE, exception.getMessage());
	}


	@Test
	public void when_retrieve_and_no_enough_balance_expected_exception() {
		account.deposit(BigDecimal.valueOf(10));
		CustomFunctionalException exception = assertThrows(CustomFunctionalException.class,
				() -> account.retrieve(BigDecimal.valueOf(15)));
		assertEquals(MessageError.DO_NOT_HAVE_ENOUGH_BALANCE, exception.getMessage());
	}

	@Test
	public void when_retrieve_amount_negative_expected_exception() {
		CustomInternalException exception = assertThrows(CustomInternalException.class,
				() -> account.retrieve(BigDecimal.valueOf(-15)));
		assertEquals(MessageError.AMOUNT_NEGATIVE, exception.getMessage());
	}

	@Test
	public void when_retrieve_expected_removeOrAdd_balance_checkHistory() throws InterruptedException {
		account.deposit(BigDecimal.valueOf(36));
		assertEquals(1, account.getHitories().size());
		sortHistoryAndCheckLastSave(OperationEnum.DEPOSIT, BigDecimal.valueOf(36));

		TimeUnit.MILLISECONDS.sleep(50);
		account.deposit(BigDecimal.valueOf(36));
		assertEquals(2, account.getHitories().size());
		sortHistoryAndCheckLastSave(OperationEnum.DEPOSIT, BigDecimal.valueOf(72));

		TimeUnit.MILLISECONDS.sleep(50);
		account.retrieve(BigDecimal.valueOf(15));
		assertEquals(3, account.getHitories().size());
		sortHistoryAndCheckLastSave(OperationEnum.RETRIEVE, BigDecimal.valueOf(57));

		TimeUnit.MILLISECONDS.sleep(50);
		account.deposit(BigDecimal.valueOf(36));
		assertEquals(4, account.getHitories().size());
		sortHistoryAndCheckLastSave(OperationEnum.DEPOSIT, BigDecimal.valueOf(93));

		TimeUnit.MILLISECONDS.sleep(50);
		account.retrieve(BigDecimal.valueOf(58));
		assertEquals(5, account.getHitories().size());
		sortHistoryAndCheckLastSave(OperationEnum.RETRIEVE, BigDecimal.valueOf(35));

	}


	private void sortHistoryAndCheckLastSave(OperationEnum operation, BigDecimal balance) {
		account.getHitories().stream().findFirst().ifPresent(item -> {
			assertEquals(operation, item.operation());
			assertEquals(balance, item.newBalance());
		});
	}
}
