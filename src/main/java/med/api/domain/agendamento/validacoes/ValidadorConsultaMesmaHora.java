package med.api.domain.agendamento.validacoes;

import med.api.domain.ValidacaoException;
import med.api.domain.agendamento.ConsultaRepository;
import med.api.domain.agendamento.DadosAgendamentoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorConsultaMesmaHora implements ValidadorAgendamentoConsulta{

    @Autowired
    ConsultaRepository consultaRepository;
    @Override
    public void validar(DadosAgendamentoConsulta dados) {
        var existeConsulta =  consultaRepository.existsByMedicoIdAndData(dados.idMedico(), dados.data());
        if(existeConsulta){
            throw new ValidacaoException("Esse médico já possui uma consulta para essa hora");
        }
    }
}
