package fr.dauphine.miageif.msa.Microservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("operation")
public class OperationController {

    public static final String debiter = "http://localhost:8000/compte/debitercompte/{iban}/{montant}";
    public static final String crediter = "http://localhost:8000/compte/creditercompte/{iban}/{montant}";
    @Autowired
    private OperationRepository operationRepository;

    @GetMapping("/touteslesoperations")
    public List<Operation> toutesLesOperations()
    {
        List<Operation> listeOperations = operationRepository.findAll();
        return listeOperations;
    }


    @PostMapping("/nouvelleoperation")
    public ResponseEntity<String> nouvelleoperation(@RequestBody Operation operation) {
        RestTemplate restTemplate = new RestTemplate();

        // Définition de la requête pour débiter l'emmeteur
        HttpHeaders headersS = new HttpHeaders();
        headersS.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
        headersS.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entityS = new HttpEntity<String>(headersS);

        // Définition de la requete pour créditer le destinataire
        HttpHeaders headersD = new HttpHeaders();
        headersD.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
        headersD.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entityD = new HttpEntity<String>(headersD);

        // Requête pour débiter l'emmeteur
        ResponseEntity<String> reponseDebit = restTemplate.exchange(debiter, HttpMethod.GET, entityS, String.class, operation.getIbanSource(), operation.getMontant());
        if (reponseDebit.getBody().equals("oui")){
            // Requete pour créditer le destinataire
            ResponseEntity<String> reponseCredit = restTemplate.exchange(crediter, HttpMethod.GET, entityD, String.class, operation.getIbanDestination(), operation.getMontant());
            if (reponseCredit.getBody().equals("oui")){
                Operation op = operationRepository.save(operation);

                if (op == null)
                    return ResponseEntity.noContent().build();

                URI location = ServletUriComponentsBuilder.fromCurrentRequest().path(
                        "/{id}").buildAndExpand(op.getIdOperation()).toUri();

                return ResponseEntity.created(location).build();
            }else {
                return ResponseEntity.ok("Le compte destinataire n'éxiste pas");
            }
        }else {
            if (reponseDebit.getBody().equals("noDebit")) return ResponseEntity.ok("pas de crédit");
            else return ResponseEntity.ok("Le compte source n'éxiste pas");
        }
    }

    @GetMapping("/rechercheroperation/{id}")
    public Operation rechercheParId(@PathVariable long id) {

        try {
            Optional<Operation> operation = operationRepository.findById(id);
            return operation.get();
        }catch (java.util.NoSuchElementException e) {
            return null;
        }
    }

    @GetMapping("/rechercheroperationpartype/{type}")
    public List<Operation> rechercheParType(@PathVariable String type) {

        try {
            List<Operation> operation = operationRepository.findAllByType(type);
            return operation;
        }catch (java.util.NoSuchElementException e){
            return null;
        }
    }

    @GetMapping("/rechercheroperationpardate/{jour}/{mois}/{annee}")
    public List<Operation> rechercheParDate(@PathVariable String jour, @PathVariable String mois, @PathVariable String annee) {

        try {
            List<Operation> operation = operationRepository.findAllByDate(jour+"/"+mois+"/"+annee);
            return operation;
        }catch (java.util.NoSuchElementException e){
            return null;
        }
    }

    @GetMapping("/rechercheroperationparibansource/{iban}")
    public List<Operation> rechercheParIbanSource(@PathVariable String iban) {

        try {
            List<Operation> operation = operationRepository.findAllByIbanSource(iban);
            return operation;
        }catch (java.util.NoSuchElementException e){
            return null;
        }

    }

    @GetMapping("/rechercheroperationparibandestination/{iban}")
    public List<Operation> rechercheParIbanDestination(@PathVariable String iban) {

        try {
            List<Operation> operation = operationRepository.findAllByIbanDestination(iban);
            return operation;
        }catch (java.util.NoSuchElementException e){
            return null;
        }
    }

    @RequestMapping("/error")
    String erreur(){
        return "erreur";
    }
}
