package med.api.domain.agendamento.validacoes;

import med.api.domain.ValidacaoException;
import med.api.domain.agendamento.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioAntecedencia implements ValidadorAgendamentoConsulta{

    public void validar(DadosAgendamentoConsulta dados){
        var dataConsulta = dados.data();
        var agora = LocalDateTime.now();
        var diferencaMinutos = Duration.between(agora,dataConsulta).toMinutes();

        if (diferencaMinutos < 30) {
            throw new ValidacaoException("Consulta deve ser agendado com no minímo 30 minutos de antecedência");
        }
    }
}
