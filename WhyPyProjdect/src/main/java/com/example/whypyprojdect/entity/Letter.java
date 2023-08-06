package com.example.whypyprojdect.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.lang.reflect.Member;
import jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Letter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int letterId;

    @Column
    private String letterTitle;

    @Column
    @Lob
    private String letterContent;

    @Column
    private boolean deletedByLetterSender;

    @Column
    private boolean deletedByLetterReceiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="letterSenderId")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private MemberEntity letterSender;
    //MemberEntity 타입으로 sender 생성


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="letterReceiverId")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private MemberEntity letterReceiver;

    public void deleteByLetterSender() {
        this.deletedByLetterSender=true;
    }

    public void deleteByLetterReceiver() {
        this.deletedByLetterReceiver=true;
    }

    public boolean isDeleted() {
        return isDeletedByLetterSender() && isDeletedByLetterReceiver();
    }
}
