package dev.arcanjo.encurtaAI.links;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class LinkService {
        @Autowired
        private LinkRepository linkRepository;
        public LinkService(LinkRepository linkRepository){
            this.linkRepository = linkRepository;
        }
    public String gerarUrlAleatoria(){
        int length = ThreadLocalRandom.current().nextInt(5, 11); // Gera um número aleatório entre 5 (inclusive) e 10 (inclusive)
        return RandomStringUtils.randomAlphanumeric(length); // Gera uma string alfanumérica com o comprimento gerado
    }
        public Link encurtarUrl(String urlOriginal){
            Link link = new Link();
            link.setUrlLonga(urlOriginal);
            link.setUrlEncurtada(gerarUrlAleatoria());
            link.setUrlCriadaEm(LocalDateTime.now());
            link.setUrlQrCode("ainda nao existe");
            return linkRepository.save(link);
        }
        public Link obterUrlOriginal(String obterUrlOriginal ){
            try{
                    return linkRepository.findByUrlEncurtada(obterUrlOriginal );
            }catch (Exception erro) {
                throw new RuntimeException("essa URL n existe", erro);
            }
        }
}
