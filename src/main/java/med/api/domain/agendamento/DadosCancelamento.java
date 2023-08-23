package med.api.domain.agendamento;

import jakarta.validation.constraints.NotNull;

public record DadosCancelamento(
        @NotNull
        Long idConsulta,
        @NotNull
        MotivoCancelamento motivo) {

}
