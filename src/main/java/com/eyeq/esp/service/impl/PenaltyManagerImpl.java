package com.eyeq.esp.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eyeq.esp.model.Penalty;
import com.eyeq.esp.service.AbstractJpaDaoService;
import com.eyeq.esp.service.PenaltyManager;

/**
 * @author Samkwang Na
 * @since 0.3.1 2013. 2. 12. 오후 10:34:10
 * @revision $LastChangedRevision: 6070 $
 * @date $LastChangedDate: 2013-02-16 12:31:02 +0900 (토, 16 2월 2013) $
 * @by $LastChangedBy: jmlim $
 */
@Service("penaltyManager")
@Transactional
public class PenaltyManagerImpl extends AbstractJpaDaoService implements
		PenaltyManager {

	/**
	 * @see com.eyeq.esp.service.PenaltyManager#getPenalty(java.lang.Integer)
	 */
	@Transactional(readOnly = true)
	public Penalty getPenalty(Integer penaltyId) {
		return getEntityManager().find(Penalty.class, penaltyId);
	}

	/**
	 * @see com.eyeq.esp.service.PenaltyManager#updatePenalty(com.eyeq.esp.model.Penalty)
	 */
	public void updatePenalty(Penalty penalty) {
		penalty.setModifiedDate(new Date());
		getEntityManager().merge(penalty);
	}

	/**
	 * @see com.eyeq.esp.service.PenaltyManager#createPenalty(com.eyeq.esp.model.Penalty)
	 */
	public void createPenalty(Penalty penalty) {
		penalty.setCreatedDate(new Date());
		getEntityManager().persist(penalty);
	}

	/**
	 * @see com.eyeq.esp.service.PenaltyManager#getPenalties()
	 */

	@Transactional(readOnly = true)
	public List<Penalty> getPenalties() {
		List<Penalty> results = getEntityManager().createNamedQuery(
				"com.eyeq.esp.model.Penalty@getPenalties()", Penalty.class)
				.getResultList();

		if (results != null && results.size() > 0) {
			return results;
		}
		return null;
	}
}
