package br.com.learn.AppRH.controllers;

import br.com.learn.AppRH.models.Candidato;
import br.com.learn.AppRH.models.Vaga;
import br.com.learn.AppRH.repositorys.CandidatoRepository;
import br.com.learn.AppRH.repositorys.VagaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class VagaController {

    @Autowired
    private VagaRepository vr;

    @Autowired
    private CandidatoRepository cr;

    // CADASTRA VAGA
    @RequestMapping(value = "/cadastrarVaga", method = RequestMethod.GET)
    public String form() {
        return "vaga/formVaga";
    }

    @RequestMapping(value = "/cadastrarVaga", method = RequestMethod.POST)
    public String form(@Valid Vaga vaga, BindingResult result, RedirectAttributes attributes) {

        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique os campos...");
            return "redirect:/cadastrarVaga";
        }

        vr.save(vaga);
        attributes.addFlashAttribute("mensagem", "Vaga cadastrada com sucesso!");
        return "redirect:/cadastrarVaga";
    }

    // LISTA VAGAS

    @RequestMapping("/vagas")
    public ModelAndView listaVagas() {
        ModelAndView mv = new ModelAndView("vaga/listaVaga");
        Iterable<Vaga> vagas = vr.findAll();
        mv.addObject("vagas", vagas);
        return mv;
    }

    //
    @RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
    public ModelAndView detalhesVaga(@PathVariable("codigo") String codigo) {
        Optional<Vaga> vaga = vr.findById(codigo);
        ModelAndView mv = new ModelAndView("vaga/detalhesVaga");
        mv.addObject("vaga", vaga.get());

        Iterable<Candidato> canditados = cr.findByVaga(vaga);
        mv.addObject("candidatos", canditados);

        return mv;

    }

    // DELETA VAGA
    @RequestMapping("/deletarVaga")
    public String deletarVaga(String codigo) {
        Optional<Vaga> vaga = vr.findById(codigo);
        vr.delete(vaga.get());
        return "redirect:/vagas";
    }

    // ADICIONAR CANDIDATO
    @RequestMapping(value = "/{codigo}", method = RequestMethod.POST)
    public String detalhesVagaPost(@PathVariable("codigo") String codigo, @Valid Candidato candidato,
                                   BindingResult result, RedirectAttributes attributes) {

        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique os campos");
            return "redirect:/{codigo}";
        }

        // rg duplicado
        if (cr.findByRg(candidato.getRg()) != null) {
            attributes.addFlashAttribute("mensagem_erro", "RG duplicado");
            return "redirect:/{codigo}";
        }

        Optional<Vaga> vaga = vr.findById(codigo);
        candidato.setVaga(vaga);
        cr.save(candidato);
        attributes.addFlashAttribute("mensagem", "Candidato adionado com sucesso!");
        return "redirect:/{codigo}";
    }

    // DELETA CANDIDATO pelo RG
    @RequestMapping("/deletarCandidato")
    public String deletarCandidato(String rg) {
        Candidato candidato = cr.findByRg(rg);
        Vaga vaga = candidato.getVaga();
        String codigo = "" + vaga.getId();

        cr.delete(candidato);

        return "redirect:/" + codigo;

    }

    // Métodos que atualizam vaga
    // formulário edição de vaga
    @RequestMapping(value = "/editar-vaga", method = RequestMethod.GET)
    public ModelAndView editarVaga(String codigo) {
        Optional<Vaga> vaga = vr.findById(codigo);
        ModelAndView mv = new ModelAndView("vaga/update-vaga");
        mv.addObject("vaga", vaga);
        return mv;
    }

    // UPDATE vaga
    @RequestMapping(value = "/editar-vaga", method = RequestMethod.POST)
    public String updateVaga(@Valid Vaga vaga, BindingResult result, RedirectAttributes attributes) {
        vr.save(vaga);
        attributes.addFlashAttribute("success", "Vaga alterada com sucesso!");

        String codigoLong = vaga.getId();
        String codigo = "" + codigoLong;
        return "redirect:/" + codigo;
    }

}
