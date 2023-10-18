package hcmute.wepr.ielts_app.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import hcmute.wepr.ielts_app.Models.WithdrawTransaction;

public interface WithdrawTransactionRepository extends JpaRepository<WithdrawTransaction, Long> {
}