package com.eyeq.esp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author Samkwang Na
 * @since 0.1.1 2013. 2. 12. 오후 7:56:39
 * @revision $LastChangedRevision: 6112 $
 * @date $LastChangedDate: 2013-02-22 23:59:39 +0900 (금, 22 2월 2013) $
 * @by $LastChangedBy: jmlim $
 */
@Entity
@Table(name = "PENALTIES")
@NamedQueries({ @NamedQuery(name = "com.eyeq.esp.model.Penalty@getPenalties()", query = "from Penalty as penalty") })
public class Penalty extends BaseEntity {

	@Column(name = "REASON")
	private String reason;

	@Column(name = "SCORE")
	private Integer score;

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason
	 *            the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @return the score
	 */
	public Integer getScore() {
		return score;
	}

	/**
	 * @param score
	 *            the score to set
	 */
	public void setScore(Integer score) {
		this.score = score;
	}

}
