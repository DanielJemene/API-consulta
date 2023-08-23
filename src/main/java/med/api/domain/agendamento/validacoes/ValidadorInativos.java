package med.api.domain.agendamento.validacoes;

import med.api.domain.ValidacaoException;
import med.api.domain.agendamento.DadosAgendamentoConsulta;
import med.api.domain.medico.MedicoRepository;
import med.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorInativos implements ValidadorAgendamentoConsulta{

    @Autowired
    MedicoRepository medicoRepository;

    @Autowired
    PacienteRepository pacienteRepository;
    @Override
    public void validar(DadosAgendamentoConsulta dados) {
        Boolean paciente = pacienteRepository.findAtivoById(dados.idPaciente());
        var medico = medicoRepository.findAtivoByid(dados.idMedico());
        if (!paciente){
            throw new ValidacaoException("Paciente não esta ativo");
        }

        if (!medico){
            throw new ValidacaoException("Medico não esta ativo");
        }
    }
}
