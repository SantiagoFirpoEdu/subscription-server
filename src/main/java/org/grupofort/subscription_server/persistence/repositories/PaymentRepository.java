package org.grupofort.subscription_server.persistence.repositories;

import org.grupofort.subscription_server.data_access.RegisterPaymentDataAccess;
import org.grupofort.subscription_server.persistence.entities.PaymentJpaEntity;
import org.grupofort.subscription_server.persistence.entities.SubscriptionJpaEntity;
import org.grupofort.subscription_server.persistence.exceptions.SubscriptionNotFoundException;
import org.grupofort.subscription_server.persistence.jpa_repositories.PaymentJpaRepository;
import org.grupofort.subscription_server.persistence.jpa_repositories.SubscriptionJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Component
public class PaymentRepository implements RegisterPaymentDataAccess
{
    @Autowired
    public PaymentRepository(PaymentJpaRepository paymentJpaRepository, SubscriptionJpaRepository subscriptionJpaRepository)
    {
        this.paymentJpaRepository = paymentJpaRepository;
        this.subscriptionJpaRepository = subscriptionJpaRepository;
    }

    @Override
    public void registerPayment(Date paymentDate, long subscriptionId, BigDecimal paidAmount) throws SubscriptionNotFoundException
    {
        Optional<SubscriptionJpaEntity> subscription = subscriptionJpaRepository.findById(subscriptionId);

        if (subscription.isEmpty())
        {
            throw new SubscriptionNotFoundException(subscriptionId);
        }

        PaymentJpaEntity payment = new PaymentJpaEntity(subscription.get(), paidAmount, paymentDate, "none");
        paymentJpaRepository.save(payment);
    }

    private final PaymentJpaRepository paymentJpaRepository;
    private final SubscriptionJpaRepository subscriptionJpaRepository;
}
