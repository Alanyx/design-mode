package com.beauty_design_mode.lecture11_12.anemia_model.service;

import com.beauty_design_mode.lecture11_12.anemia_model.entity.Status;
import com.beauty_design_mode.lecture11_12.anemia_model.entity.VirtualWalletEntity;
import com.beauty_design_mode.lecture11_12.anemia_model.entity.VirtualWalletTransactionEntity;
import com.beauty_design_mode.lecture11_12.anemia_model.error.InsufficientBalanceException;
import com.beauty_design_mode.lecture11_12.anemia_model.error.NoSufficientBalanceException;
import com.beauty_design_mode.lecture11_12.anemia_model.repository.VirtualWalletRepository;
import com.beauty_design_mode.lecture11_12.anemia_model.repository.VirtualWalletTransactionRepository;

import java.math.BigDecimal;

/**
 * Service 层的代码如下所示。
 * 这里省略了一些不重要的校验代码，比如，对 amount 是否小于 0、钱包是否存在的校验等等。
 *
 * @author Alan Yin
 * @date 2020/10/10
 */

public class VirtualWalletService {

    // 通过构造函数或者IOC框架注入
    private VirtualWalletRepository walletRepo;
    private VirtualWalletTransactionRepository transactionRepo;

    public VirtualWalletBo getVirtualWallet(Long walletId) {
        VirtualWalletEntity walletEntity = walletRepo.getWalletEntity(walletId);
        VirtualWalletBo walletBo = convert(walletEntity);
        return walletBo;
    }

    private VirtualWalletBo convert(VirtualWalletEntity walletEntity) {
        // ...
        return null;
    }

    public BigDecimal getBalance(Long walletId) {
        return walletRepo.getBalance(walletId);
    }

    public void debit(Long walletId, BigDecimal amount) {
        VirtualWalletEntity walletEntity = walletRepo.getWalletEntity(walletId);
        BigDecimal balance = walletEntity.getBalance();
        if (balance.compareTo(amount) < 0) {
            throw new NoSufficientBalanceException("...");
        }
        walletRepo.updateBalance(walletId, balance.subtract(amount));
    }

    public void credit(Long walletId, BigDecimal amount) {
        VirtualWalletEntity walletEntity = walletRepo.getWalletEntity(walletId);
        BigDecimal balance = walletEntity.getBalance();
        walletRepo.updateBalance(walletId, balance.add(amount));
    }

    /**
     * 为了保证转账操作的数据一致性，添加了一些跟 transaction 相关的记录和状态更新的代码。
     *
     * @param fromWalletId
     * @param toWalletId
     * @param amount
     */
    public void transfer(Long fromWalletId, Long toWalletId, BigDecimal amount) {
        VirtualWalletTransactionEntity transactionEntity = new VirtualWalletTransactionEntity();
        transactionEntity.setAmount(amount);
        transactionEntity.setCreateTime(System.currentTimeMillis());
        transactionEntity.setFromWalletId(fromWalletId);
        transactionEntity.setToWalletId(toWalletId);
        transactionEntity.setStatus(Status.TO_BE_EXECUTED);
        Long transactionId = transactionRepo.saveTransaction(transactionEntity);
        try {
            debit(fromWalletId, amount);
            credit(toWalletId, amount);
        } catch (InsufficientBalanceException e) {
            transactionRepo.updateStatus(transactionId, Status.CLOSED);
            // ...rethrow exception e...
        } catch (Exception e) {
            transactionRepo.updateStatus(transactionId, Status.FAILED);
            // ...rethrow exception e...
        }
        transactionRepo.updateStatus(transactionId, Status.EXECUTED);
    }
}
