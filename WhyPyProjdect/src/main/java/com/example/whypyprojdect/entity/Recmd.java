package com.example.whypyprojdect.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "Recmd")
public class Recmd {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "recmd_id")
    private int recmdId;

    @Column(name = "post_id")
    private int postId;

    @Column(name = "member_id")
    private long memberId;
}