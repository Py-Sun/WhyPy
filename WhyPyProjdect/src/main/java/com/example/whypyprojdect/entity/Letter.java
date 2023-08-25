package com.example.whypyprojdect.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Letter {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "letter_id")
    private int letterId;

    @Column(name="letter_receiver_id")
    private long receiverId;

    @Column(name="letter_sender_id")
    private long senderId;

    @Column(name="letter_title")
    private String title;

    @Column(name="letter_content")
    private String content;

    @Column(name="letter_sendDate")
    private Date sendDate;

    @Column(name = "letter_read")
    private boolean read;
}
