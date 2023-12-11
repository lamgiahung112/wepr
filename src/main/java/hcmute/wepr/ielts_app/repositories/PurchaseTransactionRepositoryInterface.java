package hcmute.wepr.ielts_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import hcmute.wepr.ielts_app.Models.PurchaseTransaction;

public interface PurchaseTransactionRepositoryInterface extends JpaRepository<PurchaseTransaction, String> {

}
