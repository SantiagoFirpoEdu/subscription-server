package org.grupofort.subscription_server.persistence.jpa_repositories;

import org.grupofort.domain.entities.Subscription;
import org.grupofort.subscription_server.persistence.entities.SubscriptionJpaEntity;
import org.grupofort.use_cases.subscriptions.query_subscription.ESubscriptionStatusFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface SubscriptionJpaRepository extends JpaRepository<SubscriptionJpaEntity, Long>
{
	@Query("SELECT s FROM SubscriptionJpaEntity s WHERE s.id = :customerId")
	List<SubscriptionJpaEntity> getSubscriptionsForCustomer(long customerId);

	@Query("SELECT s FROM SubscriptionJpaEntity s " +
	       "WHERE s.endDate >= CURRENT_DATE AND s.startDate <= CURRENT_DATE AND :statusFilter = 'ACTIVE' " +
	       "OR s.endDate < CURRENT_DATE AND :statusFilter = 'INACTIVE' " +
	       "OR s.startDate > CURRENT_DATE AND :statusFilter = 'PENDING' " +
	       "OR :statusFilter = 'ALL'")
	List<SubscriptionJpaEntity> querySubscriptions(String statusFilter);
}
