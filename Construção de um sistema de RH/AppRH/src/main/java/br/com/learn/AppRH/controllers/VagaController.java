package br.com.learn.AppRH.controllers;

import br.com.learn.AppRH.models.Vaga;
import br.com.learn.AppRH.repositorys.CandidatoRepository;
import br.com.learn.AppRH.repositorys.VagaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/vaga")
public class VagaController {

    @Autowired
    private VagaRepository vagaRepository;

    @Autowired
    private CandidatoRepository candidatoRepository;

    @GetMapping("/cadastrarVaga")
    public String form() {
        return "vaga/formVaga";
    }

    @PostMapping("/cadastrarVaga")
    public String form(@Valid Vaga vaga, BindingResult result, RedirectAttributes redirectAttributes){

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("mensagem", "Verifique os campos...");
            return "redirect:/cadastrarVaga";
        }

        vagaRepository.save(vaga);
        redirectAttributes.addFlashAttribute("mensagem", "Vaga cadastrada com sucesso!");

        return "redirect:/cadastrarVaga";
    }

//    @RequestMapping("/vagas")
//    public ModelAndView listaVagas() {
//
//    }

}
