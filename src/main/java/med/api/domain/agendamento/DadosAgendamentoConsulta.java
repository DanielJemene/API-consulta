package med.api.domain.agendamento;

// Trecho de código suprimido

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.api.domain.medico.Especialidade;

import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(
        Long idMedico,

        @NotNull
        Long idPaciente,

        @NotNull
        @Future
        LocalDateTime data,

        Especialidade especialidade) {
}
