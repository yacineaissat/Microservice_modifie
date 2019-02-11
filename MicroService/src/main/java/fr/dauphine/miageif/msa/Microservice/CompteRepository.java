package fr.dauphine.miageif.msa.Microservice;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CompteRepository extends JpaRepository<Compte, Long>{
    List<Compte> findAll();
    Optional<Compte> findByIban(String iban);
    List<Compte> findAllByType(String type);
}

