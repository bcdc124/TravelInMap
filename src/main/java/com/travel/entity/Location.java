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
 * 장소번호	l_num
 * 일차 		l_days
 * 순번		l_seq
 * 장소명		l_name
 * 주소		l_addr
 * 위도		l_lat
 * 경도		l_lon
 * 일정글번호	s_num
 * 장소ID		l_id
 * 
 * @author bcdc124
 *
 */
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
@Table(name="tim_location")
@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "l_num")
    private Integer l_num;

    @Column(name = "l_days", nullable = false)
    private int l_days;

    @Column(name = "l_seq", nullable = false)
    private int l_seq;

    @Column(name = "l_name", length = 100, nullable = false)
    private String l_name;

    @Column(name = "l_addr", length = 100, nullable = false)
    private String l_addr;

    @Column(name = "l_lat")
    private Double l_lat;

    @Column(name = "l_lon")
    private Double l_lon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "s_num", nullable = false)
    private Schedule schedule;

    @Column(name = "l_id", length = 100, nullable = false)
    private String l_id;
}
