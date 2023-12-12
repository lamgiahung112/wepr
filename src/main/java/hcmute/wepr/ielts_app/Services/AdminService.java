package hcmute.wepr.ielts_app.Services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import hcmute.wepr.ielts_app.Models.PurchaseTransaction;
import hcmute.wepr.ielts_app.Services.Interfaces.AdminServiceInterface;
import hcmute.wepr.ielts_app.Utilities.Requests.AdminStatisticsRequest;
import hcmute.wepr.ielts_app.Utilities.responses.AdminStatisticsResponse;
import hcmute.wepr.ielts_app.repositories.PurchaseTransactionRepositoryInterface;

@Service
public class AdminService implements AdminServiceInterface {
	@Value("${config.share_percentage}")
	private double AUTHOR_REVENUE_PERCENTAGE;
	
	@Autowired
	private PurchaseTransactionRepositoryInterface purchaseTransactionRepository;

	@Override
	public AdminStatisticsResponse getStatistics(AdminStatisticsRequest request) {
		LocalDateTime startTime = getStartTimeInMillis(request.getRange());
		LocalDateTime endTime = getEndTimeInMillis(request.getRange());

		List<PurchaseTransaction> allPurchases = purchaseTransactionRepository.findWithTransactionDetailsByCreatedAtBetween(startTime, endTime);
		
		return AdminStatisticsResponse.builder()
				.numberOfStudents(0)
				.numberOfSoldCourses(0)
				.totalCourseValue(0)
				.totalRevenue(0).build();
	}

	private LocalDateTime getStartTimeInMillis(AdminStatisticsRequest.StatsRange range) {
		// Get the current date
        LocalDate today = LocalDate.now();
		switch (range) {
		case DAY: {
			// Get the starting time of today at midnight (00:00:00) in the default time
			// zone
			Instant startOfToday = today.atStartOfDay().toInstant(ZoneOffset.UTC);

			// Convert Instant to milliseconds
			return LocalDateTime.ofInstant(startOfToday, ZoneOffset.UTC);
		}
		case MONTH:
			// Get the starting time of the current month at midnight (00:00:00) in the
			// default time zone
			LocalDate firstDayOfMonth = today.withDayOfMonth(1);
			Instant startOfThisMonth = firstDayOfMonth.atStartOfDay().toInstant(ZoneOffset.UTC);

			// Convert Instant to milliseconds
			return LocalDateTime.ofInstant(startOfThisMonth, ZoneOffset.UTC);
		case YEAR:
	        // Get the starting time of the current year at midnight (00:00:00) in the default time zone
	        LocalDate firstDayOfYear = today.withDayOfYear(1);
	        Instant startOfThisYear = firstDayOfYear.atStartOfDay().toInstant(ZoneOffset.UTC);

	        // Convert Instant to milliseconds
	        return LocalDateTime.ofInstant(startOfThisYear, ZoneOffset.UTC);
		default:
			throw new IllegalArgumentException("Unexpected value: " + range);
		}
	}
	
	private LocalDateTime getEndTimeInMillis(AdminStatisticsRequest.StatsRange range) {
	    // Get the current date
	    LocalDate today = LocalDate.now();

	    switch (range) {
	        case DAY: {
	            // Get the ending time of today at just before midnight (23:59:59) in the default time zone
	            Instant endOfToday = today.atTime(23, 59, 59).toInstant(ZoneOffset.UTC);

	            // Convert Instant to milliseconds
	            return LocalDateTime.ofInstant(endOfToday, ZoneOffset.UTC);
	        }
	        case MONTH:
	            // Get the ending time of the current month at just before midnight (23:59:59) in the default time zone
	            LocalDate lastDayOfMonth = today.withDayOfMonth(today.lengthOfMonth());
	            Instant endOfThisMonth = lastDayOfMonth.atTime(23, 59, 59).toInstant(ZoneOffset.UTC);

	            // Convert Instant to milliseconds
	            return LocalDateTime.ofInstant(endOfThisMonth, ZoneOffset.UTC);
	        case YEAR:
	            // Get the ending time of the current year at just before midnight (23:59:59) in the default time zone
	            LocalDate lastDayOfYear = today.withDayOfYear(today.lengthOfYear());
	            Instant endOfThisYear = lastDayOfYear.atTime(23, 59, 59).toInstant(ZoneOffset.UTC);

	            // Convert Instant to milliseconds
	            return LocalDateTime.ofInstant(endOfThisYear, ZoneOffset.UTC);
	        default:
	            throw new IllegalArgumentException("Unexpected value: " + range);
	    }
	}
}
