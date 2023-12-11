package hcmute.wepr.ielts_app.Models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;


@Embeddable
@Data
@Accessors(chain = true)
public class CartItemId implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "course_id")
	private int courseId;
	
	
}
