package fr.dauphine.miageif.msa.Microservice;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface OperationRepository extends JpaRepository<Operation, Long>{
    List<Operation> findAll();
    List<Operation> findAllByDate(String date);
    List<Operation> findAllByType(String type);
    List<Operation> findAllByIbanSource(String iban);
    List<Operation> findAllByIbanDestination(String iban);
}

