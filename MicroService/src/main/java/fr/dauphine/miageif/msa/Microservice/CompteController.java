package fr.dauphine.miageif.msa.Microservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("compte")
public class CompteController {

    @Autowired
    private CompteRepository compteRepository;

    @GetMapping("/touslescomptes")
    public List<Compte> tousLesComptes()
    {
        List<Compte> listeComptes= compteRepository.findAll();
        return listeComptes;
    }


    @PostMapping("/nouveaucompte")
    public ResponseEntity<Void> nouveauCompte(@RequestBody Compte compte) {
        Compte cpt = compteRepository.save(compte);

        if (cpt == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
                "/{id}").buildAndExpand(cpt.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/recherchercompte/{id}")
    public Compte rechercheParId(@PathVariable long id) {
        Optional<Compte> compte = compteRepository.findById(id);
        try {
            return compte.get();
        }catch (Exception e){
            return null;
        }
    }

    @GetMapping("/recherchercomptepariban/{iban}")
    public Compte rechercheParIban(@PathVariable String iban) {
        Optional<Compte> compte = compteRepository.findByIban(iban);
        try {
            return compte.get();
        }catch (Exception e){
            return null;
        }
    }

    @GetMapping("/recherchercomptepartype/{iban}")
    public List<Compte> rechercheParType(@PathVariable String type) {
        List<Compte> compte = compteRepository.findAllByType(type);
        try {
            return compte;
        }catch (Exception e){
            return null;
        }
    }

    @GetMapping("/creditercompte/{iban}/{montant}")
    public String crediterCompte(@PathVariable String iban, @PathVariable float montant) {
        System.out.println("Je suis dans créditer");
        Optional<Compte> compte = compteRepository.findByIban(iban);

        try {
            compte.get();
        }catch (Exception e){
            return ("nimp");
        }

        compte.get().crediter(montant);
        compteRepository.save(compte.get());
        return "oui";
    }

    @GetMapping("/debitercompte/{iban}/{montant}")
    public String debiterCompte(@PathVariable String iban, @PathVariable float montant) {
        Optional<Compte> compte = compteRepository.findByIban(iban);

        try {
            compte.get();
        }catch (Exception e){
            return ("nimp");
        }

        if (compte.get().debiter(montant)){
            compteRepository.save(compte.get());
            return "oui";
        }
        else return "noDebit";
    }

    @DeleteMapping("/supprimercompte/{id}")
    void supprimerCompte(@PathVariable Long id) {
        compteRepository.deleteById(id);
    }

    @DeleteMapping("/supprimertouslescomptes")
    void supprimerTousLesComptes() {
        compteRepository.deleteAll();
    }

    @RequestMapping("/error")
    String erreur(){
        return "erreur";
    }
}
