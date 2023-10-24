package hcmute.wepr.ielts_app.Models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name="student")
@Data
@Builder
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="student_id")
    private Long id;
    @Column(unique = true)
    private String username;
    private String passwordHash;
    private String studentName;
    private String email;
    private double balance;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    Set<PurchaseTransaction> purchaseTransactions;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    Set<TopupTransaction> topupTransactions;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    Set<AnswerAttempt> answerAttempts;
}