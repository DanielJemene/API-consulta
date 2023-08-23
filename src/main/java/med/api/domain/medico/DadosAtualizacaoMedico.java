package med.api.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.api.domain.endereco.DadosEndereco;

public record DadosAtualizacaoMedico(

        @NotNull
        Long id,

        String nome,

        String telefone,

        DadosEndereco endereco) {

}
