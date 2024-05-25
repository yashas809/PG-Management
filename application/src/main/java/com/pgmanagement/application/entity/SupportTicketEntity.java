package com.pgmanagement.application.entity;

import com.pgmanagement.application.enums.Reason;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name = "supporttickets")
@NoArgsConstructor
@AllArgsConstructor(staticName = "construct")
@Data
public class SupportTicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TicketID")
    private long TicketID;

    @Column(name = "caseIDFK")
    private long caseIDFK;

    @Column(name = "Reason")
    @Enumerated(EnumType.STRING)
    private Reason reason;

    @Column(name = "appUserFK")
    private long appUserFK;
}
