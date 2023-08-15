package com.example.whypyprojdect.dto;

import com.example.whypyprojdect.entity.LectureWatch;
import lombok.Data;

import java.util.Date;

@Data
public class LectureWatchDto {
    private int lwatchId;
    private int lectureId;
    private long memberId;
    private String lDate;

    public void setLwatchId(int lectureId){this.lwatchId = lwatchId;}
    public void setLectureId(int lectureId){this.lectureId = lectureId;}
    public void setMemberId(long memberId){this.memberId = memberId;}
    public void setldate(String lDate){this.lDate = lDate;}

    public LectureWatchDto()
    {

    }

    public LectureWatchDto(int lectureId, long memberId){
        this.lectureId = lectureId;
        this.memberId = memberId;
    }

    public LectureWatch toEntity(){
        return LectureWatch.builder()
                .id(lwatchId)
                .lectureId(lectureId)
                .memberId(memberId)
                .lDate(lDate)
                .build();
    }
}
