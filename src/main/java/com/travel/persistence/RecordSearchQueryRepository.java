package com.travel.persistence;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.entity.QMember;
import com.travel.entity.QTravelRecordEntity;
import com.travel.entity.TravelRecordEntity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class RecordSearchQueryRepository {
	private final JPAQueryFactory queryFactory;
	
	QTravelRecordEntity tr = QTravelRecordEntity.travelRecordEntity;
	QMember member = QMember.member;
	
	public List<TravelRecordEntity> findBytTitleContaining(String title, String theme, Integer season, Integer personnel){
		return queryFactory.select(tr)
				.from(tr)
				.where(tr.tTitle.contains(title), theme(theme), season(season), personnel(personnel))
				.fetch();
	}
	
	private BooleanExpression theme(String theme) {
		if(theme == null || theme == "") {
			return null;
		}
		return tr.tTheme.eq(theme);
	}
	
	private BooleanExpression personnel(Integer personnel) {
		if(personnel == null) {
			return null;
		}
		
		switch(personnel) {
			case 1 :
				return tr.t_personnel.eq(1);
			case 2 : 
				return tr.t_personnel.eq(2);
			case 3 :
				return tr.t_personnel.eq(3);
			case 4 :
				return tr.t_personnel.eq(4);
			case 5 :
				return tr.t_personnel.goe(5);
		}
		return null;
	}
	
	private BooleanExpression season(Integer season) {
		if(season == null) {
			return null;
		}
		if(season == 3) {
			return tr.tStartDay.month().between(3, 5);
		}
		if(season == 6) {
			return tr.tStartDay.month().between(6, 8);
		}
		if(season == 9) {
			return tr.tStartDay.month().between(9, 11);
		}
		if(season == 12) {
			return tr.tStartDay.month().between(12, 2);
		}
			
		return null;
	}

}
