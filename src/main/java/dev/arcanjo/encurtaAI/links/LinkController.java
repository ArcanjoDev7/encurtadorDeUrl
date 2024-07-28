package dev.arcanjo.encurtaAI.links;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
public class LinkController {
    @Autowired
    private LinkService linkService;
    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }
    @PostMapping("/encurta-ai")
    public ResponseEntity<LinkResponse> gerarUrlEncurtada(@RequestBody Map<String, String> request){
        String urlOriginal = request.get("urlOriginal");
        Link link = linkService.encurtarUrl(urlOriginal);

        String gerarUrlDeRedirecionamentoDoUsario = "http://localhost:8080/r/" + link.getUrlEncurtada();

        LinkResponse response = new LinkResponse(
                link.getId(),
                link.getUrlLonga(),
                gerarUrlDeRedirecionamentoDoUsario,
                link.getUrlQrCode(),
                link.getUrlCriadaEm()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("/r/{urlEncurtada}")
    public void redirecionarLink(@PathVariable String urlEncurtada, HttpServletResponse response) throws IOException {
        Link link = linkService.obterUrlOriginal(urlEncurtada);
        if(link != null){
            response.sendRedirect(link.getUrlLonga());
        }else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }
}
