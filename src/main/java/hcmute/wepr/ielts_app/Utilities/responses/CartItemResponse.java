package hcmute.wepr.ielts_app.Utilities.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartItemResponse {
	private int userId;
	private int courseId;
	private String courseImage;
	private String courseName;
	private float price;
}
