package fr.dauphine.miageif.msa.Microservice;

import javax.persistence.*;

import org.springframework.stereotype.Service;

import java.io.Serializable;



@Entity
@Service
public class Compte implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    @Column(unique=true)
    private String iban;
    private float interet;
    private String frais;
    private float solde=0;

    public Compte(){}

    public Compte (String type, String iban, float interet, String frais){
        this.type = type;
        this.iban = iban;
        this.interet = interet;
        this.frais = frais;
    }

    public String getType(){
        return type;
    }

    public String getIban(){
        return iban;
    }

    public float getInteret(){
        return interet;
    }

    public String getFrais(){
        return frais;
    }

    public void setFrais(String frais){
        this.frais = frais;
    }

    public void setInteret(float interet){
        this.interet = interet;
    }

    public Long getId(){
        return id;
    }

    public float getSolde(){
        return solde;
    }

    public void crediter (float montant){
        solde += montant;
    }

    public boolean debiter (float montant){
        if (solde >= montant) {
            solde -= montant;
            return true;
        }else {
            return false;
        }
    }
}

