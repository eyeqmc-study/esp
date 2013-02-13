package com.eyeq.esp.service;

import java.util.List;

import com.eyeq.esp.model.Penalty;

/**
 * @author Samkwang Na
 * @since 0.3.1 2013. 2. 12. 오후 10:12:54
 * @revision $LastChangedRevision: 6010 $
 * @date $LastChangedDate: 2013-02-13 12:48:54 +0900 (수, 13 2월 2013) $
 * @by $LastChangedBy: voyaging $
 */
public interface PenaltyManager {
	public Penalty getPenalty(Integer penaltyId);

	public void updatePenalty(Penalty penalty);

	public void createPenalty(Penalty penalty);

	public List<Penalty> getPenalties();
}
