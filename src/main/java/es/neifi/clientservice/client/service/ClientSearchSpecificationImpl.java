package es.neifi.clientservice.client.service;

import es.neifi.clientservice.client.persistence.entity.ClientEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;

@RequiredArgsConstructor
public class ClientSearchSpecificationImpl implements ClientSearchSpecification {

    private final Optional<String> name;
    private final Optional<String> surnames;
    private final Optional<String> legal_id;
    private final Optional<String> email;
    private final Pageable pageable;



    private Specification<ClientEntity> specClientName = new Specification<ClientEntity>() {
        @Override
        public Predicate toPredicate(Root<ClientEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            if(name.isPresent()) {
                return criteriaBuilder
                        .like(criteriaBuilder
                                .lower(root.get("name")), "%" + name.get() + "%");
            }else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        }
    };
    private Specification<ClientEntity> specClientSurname = new Specification<ClientEntity>() {
        @Override
        public Predicate toPredicate(Root<ClientEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            if(surnames.isPresent()) {
                return criteriaBuilder
                        .like(criteriaBuilder
                                .lower(root.get("surnames")), "%" + surnames.get() + "%");
            }else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        }
    };
    private Specification<ClientEntity> specClientLegalId = new Specification<ClientEntity>() {
        @Override
        public Predicate toPredicate(Root<ClientEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            if(legal_id.isPresent()) {
                return criteriaBuilder
                        .like(criteriaBuilder
                                .lower(root.get("legalID")), "%" + legal_id.get() + "%");
            }else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        }
    };

    private Specification<ClientEntity> specClientEmail = new Specification<ClientEntity>() {
        @Override
        public Predicate toPredicate(Root<ClientEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            if(email.isPresent()) {
                return criteriaBuilder
                        .like(criteriaBuilder
                                .lower(root.get("email")), "%" + email.get() + "%");
            }else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
        }
    };


    @Override
    public Specification<ClientEntity> getSpecClientName() {
        return specClientName;
    }

    @Override
    public Specification<ClientEntity> getSpecClientSurname() {
        return specClientSurname;
    }

    @Override
    public Specification<ClientEntity> getSpecClientLegalId() {
        return specClientLegalId;
    }

    @Override
    public Specification<ClientEntity> getSpecClientEmail() {
        return specClientEmail;
    }
}
