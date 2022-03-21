package com.bank.account.service;

import com.bank.account.domain.ClientAccount;
import com.bank.account.domain.OperationEnum;
import com.bank.account.exception.CustomFunctionalException;
import com.bank.account.exception.CustomInternalException;
import com.bank.account.exception.MessageError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ClientAccountServiceTest {

	ClientAccountService service;
	ClientAccount account;

	@BeforeEach
	void setUp() {
		service = new ClientAccountService();
		account = new ClientAccount();
	}


	@Test
	public void when_deposit_expected_add_into_balance() {
		service.deposit(account, BigDecimal.valueOf(36));
		assertEquals(BigDecimal.valueOf(36), account.getCurrentBalance());
	}

	@Test
	public void when_deposit_amount_negative_expected_exception() {
		CustomInternalException exception = assertThrows(CustomInternalException.class,
				() -> service.deposit(account, BigDecimal.valueOf(-15)));
		assertEquals(MessageError.AMOUNT_NEGATIVE, exception.getMessage());
	}


	@Test
	public void when_retrieve_expected_remove_from_balance() {
		service.deposit(account, BigDecimal.valueOf(36));
		service.retrieve(account, BigDecimal.valueOf(15));
		assertEquals(BigDecimal.valueOf(21), account.getCurrentBalance());
	}

	@Test
	public void when_retrieve_and_no_enough_balance_expected_exception() {
		service.deposit(account, BigDecimal.valueOf(10));
		CustomFunctionalException exception = assertThrows(CustomFunctionalException.class,
				() -> service.retrieve(account, BigDecimal.valueOf(15)));
		assertEquals(MessageError.DO_NOT_HAVE_ENOUGH_BALANCE, exception.getMessage());
	}

	@Test
	public void when_retrieve_amount_negative_expected_exception() {
		CustomInternalException exception = assertThrows(CustomInternalException.class,
				() -> service.retrieve(account, BigDecimal.valueOf(-15)));
		assertEquals(MessageError.AMOUNT_NEGATIVE, exception.getMessage());
	}

	@Test
	public void when_retrieve_expected_removeOrAdd_balance_checkHistory() throws InterruptedException {
		service.deposit(account, BigDecimal.valueOf(36));
		assertEquals(1, account.getHitories().size());
		sortHistoryAndCheckLastSave(OperationEnum.DEPOSIT, BigDecimal.valueOf(36));

		TimeUnit.MILLISECONDS.sleep(50);
		service.deposit(account, BigDecimal.valueOf(36));
		assertEquals(2, account.getHitories().size());
		sortHistoryAndCheckLastSave(OperationEnum.DEPOSIT, BigDecimal.valueOf(72));

		TimeUnit.MILLISECONDS.sleep(50);
		service.retrieve(account, BigDecimal.valueOf(15));
		assertEquals(3, account.getHitories().size());
		sortHistoryAndCheckLastSave(OperationEnum.RETRIEVE, BigDecimal.valueOf(57));

		TimeUnit.MILLISECONDS.sleep(50);
		service.deposit(account, BigDecimal.valueOf(36));
		assertEquals(4, account.getHitories().size());
		sortHistoryAndCheckLastSave(OperationEnum.DEPOSIT, BigDecimal.valueOf(93));

		TimeUnit.MILLISECONDS.sleep(50);
		service.retrieve(account, BigDecimal.valueOf(58));
		assertEquals(5, account.getHitories().size());
		sortHistoryAndCheckLastSave(OperationEnum.RETRIEVE, BigDecimal.valueOf(35));

	}


	private void sortHistoryAndCheckLastSave(OperationEnum operation, BigDecimal balance) {
		account.getHitories().stream().min(ClientAccountService.sortHistoriesByDate).ifPresent(item -> {
			assertEquals(operation, item.operation());
			assertEquals(balance, item.newBalance());
		});
	}
}
