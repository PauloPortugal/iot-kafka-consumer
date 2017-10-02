package com.pmonteiro.iot.db;


import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import com.pmonteiro.iot.core.TopicMessage;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.UUID;

@Transactional
public class TopicMessageDAO extends BaseDAO {

    @Inject
    public TopicMessageDAO(final Provider<EntityManager> entityManager) {
        super(entityManager);
    }

    public Optional<TopicMessage> findById(final UUID id) {
        return findById(TopicMessage.class, id);
    }
}