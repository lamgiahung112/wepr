package hcmute.wepr.ielts_app.Models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="withdraw_transaction")
public class WithdrawTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="withdraw_transaction_id")
    private Long id;
    private double amount;
    private Date createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="teacher_id", foreignKey = @ForeignKey(name="FK_withdraw_transaction_teacher"))
    private Teacher teacher;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}