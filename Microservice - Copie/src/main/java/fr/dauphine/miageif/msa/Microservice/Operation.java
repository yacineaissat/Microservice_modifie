package fr.dauphine.miageif.msa.Microservice;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;
import org.springframework.stereotype.Service;

import java.io.Serializable;



@Entity
@Service
public class Operation implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOperation;
    private String type;
    private String ibanSource;
    private String ibanDestination;
    private float montant;
    private String date;

    public Operation(){}

    public Operation(String type, String ibanSource, String ibanDestination, float montant, String date){
        this.type = type;
        this.ibanSource = ibanSource;
        this.ibanDestination = ibanDestination;
        this.montant = montant;
        this.date = date;
    }

    public String getType(){
        return type;
    }

    public String getIbanSource(){
        return ibanSource;
    }

    public String getIbanDestination(){
        return ibanDestination;
    }

    public float getMontant(){
        return montant;
    }

    public String getDate(){
        return date;
    }

    public Long getIdOperation(){
        return idOperation;
    }

}

