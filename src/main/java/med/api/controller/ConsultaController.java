package med.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.api.domain.agendamento.AgendaDeConsultas;
import med.api.domain.agendamento.DadosAgendamentoConsulta;
import med.api.domain.agendamento.DadosCancelamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("consultas")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultas agenda;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {
        var consultaDados = agenda.agendar(dados);
        return ResponseEntity.ok(consultaDados);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamento dados){
        agenda.cancelar(dados);
        return ResponseEntity.noContent().build();
    }
}
