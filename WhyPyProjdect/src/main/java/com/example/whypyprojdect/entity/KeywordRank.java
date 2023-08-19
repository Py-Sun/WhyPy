package com.example.whypyprojdect.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@AllArgsConstructor
@Entity
@Data
@Table(name = "Keyword_rank")
public class KeywordRank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="keyword")
    private String keyword;

    @Column(name="search_count")
    private long searchCount;

    @Column(name="search_date")
    private Date searchDate;
    public KeywordRank() {
    }

    public KeywordRank(String keyword, long searchCount, Date searchDate) {
        this.keyword = keyword;
        this.searchCount = searchCount;
        this.searchDate = searchDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public long getSearchCount() {
        return searchCount;
    }

    public void setSearchCount(long searchCount) {
        this.searchCount = searchCount;
    }

    public void setSearchDate(Date searchDate) {
        this.searchDate = searchDate;
    }
}