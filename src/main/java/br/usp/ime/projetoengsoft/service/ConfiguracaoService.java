package br.usp.ime.projetoengsoft.service;

import br.usp.ime.projetoengsoft.dto.ConfiguracaoDto;
import br.usp.ime.projetoengsoft.model.Configuracao;
import br.usp.ime.projetoengsoft.repository.ConfiguracaoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfiguracaoService {

    @Autowired
    private final ConfiguracaoRepository configuracaoRepository;

    private ModelMapper mapper = new ModelMapper();

    public ConfiguracaoService(ConfiguracaoRepository configuracaoRepository) {
        this.configuracaoRepository = configuracaoRepository;
    }

    public ConfiguracaoDto findByModo(String modo) {
        Configuracao configuracao =  configuracaoRepository.findByModo(modo);
        return mapper.map(configuracao, ConfiguracaoDto.class);
    }
}
