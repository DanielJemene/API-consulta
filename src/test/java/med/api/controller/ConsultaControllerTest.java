package med.api.controller;

import med.api.domain.agendamento.AgendaDeConsultas;
import med.api.domain.agendamento.DadosAgendamentoConsulta;
import med.api.domain.agendamento.DadosDetalhamentoConsulta;
import med.api.domain.medico.Especialidade;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJson;

    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJson;

    @MockBean
    private AgendaDeConsultas agendaDeConsultas;



    @Test
    @DisplayName("Deveria retornar HTTP 400 quando informações estão invalidas")
    @WithMockUser
    void agendarCenario1() throws Exception {
        var response = mvc.perform(MockMvcRequestBuilders.post("/consultas"))
                .andReturn().getResponse();
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria retornar HTTP 200 quando informações estão validas")
    @WithMockUser
    void agendarCenario2() throws Exception {
        var data = LocalDateTime.now().plusHours(1);
        var dadosDetalhamento = new DadosDetalhamentoConsulta(null, 2l, 5l, data) ;
        Mockito.when(agendaDeConsultas.agendar(any())).thenReturn(dadosDetalhamento);


        var response = mvc.perform(MockMvcRequestBuilders.post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosAgendamentoConsultaJson.write(
                                new DadosAgendamentoConsulta(2l,5l, data, Especialidade.CARDIOLOGIA)
                        ).getJson())
                )
                .andReturn().getResponse();

        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = dadosDetalhamentoConsultaJson.write(dadosDetalhamento).getJson();

        Assertions.assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

}