package hcmute.wepr.ielts_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import hcmute.wepr.ielts_app.Models.TransactionDetail;
import hcmute.wepr.ielts_app.Models.TransactionDetailId;

public interface TransactionDetailsRepositoryInterface extends JpaRepository<TransactionDetail, TransactionDetailId>{

}
