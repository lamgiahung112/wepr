package hcmute.wepr.ielts_app.Services.Interfaces;

import java.util.List;

import hcmute.wepr.ielts_app.Models.PurchaseTransaction;

public interface PurchaseTransactionServiceInteface {
	List<PurchaseTransaction> getFullDetailsPurchaseTransactions(int userId);
	PurchaseTransaction getLastestPurchaseTransaction(int userId);
}
