package med.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.api.domain.medico.MedicoRepository;
import med.api.domain.medico.DadosAtualizacaoMedico;
import med.api.domain.medico.DadosCadastroMedico;
import med.api.domain.medico.DadosListagemMedico;
import med.api.domain.medico.Medico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("medicos")
public class MedicoController  {

    @Autowired
    private MedicoRepository repository;


    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder builder) {
        var medico = new Medico(dados);
        repository.save(medico);
        var uri = builder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosListagemMedico(medico));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listar(Pageable pageable){
        var page =  repository.findAllByAtivoTrue(pageable).map(DadosListagemMedico::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalharMedico(@PathVariable Long id) {
        var medico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosListagemMedico(medico));
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        Medico medico = repository.getReferenceById(dados.id());
        medico.atualizar(dados);
        return ResponseEntity.ok(new DadosListagemMedico(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        Medico medico = repository.getReferenceById(id);
        medico.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
