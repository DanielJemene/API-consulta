package med.api.domain.agendamento.validacoes;

import med.api.domain.ValidacaoException;
import med.api.domain.agendamento.ConsultaRepository;
import med.api.domain.agendamento.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ValidadorConsultaMesmaDia implements ValidadorAgendamentoConsulta{

    @Autowired
    ConsultaRepository consultaRepository;
    @Override
    public void validar(DadosAgendamentoConsulta dados) {
        var primeiraHora = dados.data().withHour(7);
        var ultimaHora = dados.data().withHour(18);
        var existeConsulta =  consultaRepository.existsByPacienteIdAndDataBetween(dados.idPaciente(), primeiraHora, ultimaHora);
        if(existeConsulta){
            throw new ValidacaoException("Esse paciente já possui uma consulta para esse dia");
        }
    }
}
