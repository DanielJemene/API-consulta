package med.api.domain.agendamento.validacoes;

import med.api.domain.ValidacaoException;
import med.api.domain.agendamento.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidadorHorarioFuncionamento implements ValidadorAgendamentoConsulta{

    public void validar(DadosAgendamentoConsulta dados){
        var dataConsulta = dados.data();
        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesAbertura = dataConsulta.getHour() < 7;
        var depoisFechamento = dataConsulta.getHour() > 18;

        if (domingo || antesAbertura || depoisFechamento){
            throw new ValidacaoException("Horário fora do funcionamento da clínica");
        }
    }
}
