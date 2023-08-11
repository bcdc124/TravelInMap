package com.travel.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * 장소번호	lNum
 * 일차 		lDays
 * 순번		lSeq
 * 장소명		lName
 * 주소		lAddr
 * 위도		lLat
 * 경도		lLon
 * 일정글번호	sNum
 * 장소ID		lId
 * 
 * @author bcdc124
 *
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name="tim_location")
@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "l_num")
    private Integer lNum;

    @Column(name = "l_days", nullable = false)
    private int lDays;

    @Column(name = "l_seq", nullable = false)
    private int lSeq;

    @Column(name = "l_name", length = 100, nullable = false)
    private String lName;

    @Column(name = "l_addr", length = 100, nullable = false)
    private String lAddr;

    @Column(name = "l_lat")
    private Double lLat;

    @Column(name = "l_lon")
    private Double lLon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "s_num", nullable = false)
    private Schedule sNum;

    @Column(name = "l_id", length = 100, nullable = false)
    private String lId;
}
