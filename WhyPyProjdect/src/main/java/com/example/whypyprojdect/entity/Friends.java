package com.example.whypyprojdect.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "Friends")
public class Friends {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "friends_id")
    private int friendsId;

    @Column(name = "friends_sender_id")
    private long senderId;

    @Column(name = "friends_receiver_id")
    private long receiverId;

    @Column(name = "friends_state")
    private String state;
}
