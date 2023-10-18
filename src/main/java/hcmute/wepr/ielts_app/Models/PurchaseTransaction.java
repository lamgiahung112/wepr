package hcmute.wepr.ielts_app.Models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="purchase_transaction")
public class PurchaseTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="purchase_transaction_id")
    private Long id;
    private Double amount;
    private Date CreatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="course_id", foreignKey = @ForeignKey(name = "FK_purchase_transaction_course"))
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="student_id", foreignKey = @ForeignKey(name = "FK_purchase_transaction_student"))
    private Student student;

}