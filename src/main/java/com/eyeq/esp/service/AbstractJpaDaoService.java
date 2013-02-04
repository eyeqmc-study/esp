package com.eyeq.esp.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Hana Lee
 * @since 0.1.1 2013. 2. 3. 오전 1:27:12
 * @revision $LastChangedRevision: 5912 $
 * @date $LastChangedDate: 2013-02-03 01:27:27 +0900 (일, 03 2월 2013) $
 * @by $LastChangedBy: voyaging $
 */
public abstract class AbstractJpaDaoService {

	@PersistenceContext
	private EntityManager em;

	public EntityManager getEntityManager() {
		return this.em;
	}
}
