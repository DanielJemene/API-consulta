package med.api.domain.agendamento;

import med.api.domain.ValidacaoException;
import med.api.domain.agendamento.validacoes.ValidadorAgendamentoConsulta;
import med.api.domain.medico.Medico;
import med.api.domain.medico.MedicoRepository;
import med.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository repository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    ConsultaRepository consultaRepository;

    @Autowired
    private List<ValidadorAgendamentoConsulta> validadores;

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {
        if (!pacienteRepository.existsById(dados.idPaciente())){
            throw new ValidacaoException("Id do paciente informado não existe");
        }
        if (dados.idMedico()!= null && !medicoRepository.existsById(dados.idMedico())){
            throw new ValidacaoException("Id do médico informado não existe");
        }

        validadores.forEach(v->v.validar(dados));

        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var medico = escolherMedico(dados);
        if (medico == null){
            throw new ValidacaoException("Não existe médico disponível nessa data");
        }
        var consulta = new Consulta(null, medico, paciente, null, dados.data());
        repository.save(consulta);
        return new DadosDetalhamentoConsulta(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if(dados.idMedico()!= null){
            return medicoRepository.getReferenceById(dados.idMedico());
        }
        if (dados.especialidade() == null){
            throw new ValidacaoException("Especialidade é obrigatoria quando não se escolhe médico");
        }
        return medicoRepository.escolherMedicoAleatorioNaData(dados.especialidade(), dados.data());
    }

    public void cancelar(DadosCancelamento dados) {
        if (!consultaRepository.existsById(dados.idConsulta())) {
            throw new ValidacaoException("Id da consulta informado não existe!");
        }

        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
    }
}
