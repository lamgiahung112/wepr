package hcmute.wepr.ielts_app.Models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="topup_transaction")
public class TopupTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="topup_transaction_id")
    private Long id;
    private Double amount;
    private Date createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", foreignKey = @ForeignKey(name="FK_topup_transaction_student"))
    private Student student;
}