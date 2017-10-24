package br.com.jhonatan.gpquickstart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.jhonatan.gpquickstart.model.Pessoa;
import br.com.jhonatan.gpquickstart.repository.filter.PessoaFilter;
import br.com.jhonatan.gpquickstart.service.PessoaService;

@Controller
@RequestMapping("/pessoas")
public class PessoaController {

	private static final String CADASTRO_PESSOA_VIEW = "CadastroPessoa";

	@Autowired
	private PessoaService pessoaService;
	
	@GetMapping("/novo")
	public ModelAndView novo() {
		final ModelAndView modelAndView = new ModelAndView(CADASTRO_PESSOA_VIEW);
		modelAndView.addObject(new Pessoa());
		return modelAndView;
	}
	
	@PostMapping
	public String salvar(@Validated Pessoa pessoa, Errors errors, RedirectAttributes redirectAttributes) {
		if (errors.hasErrors()) {
			return CADASTRO_PESSOA_VIEW;
		}
		
		try {
			pessoaService.salvar(pessoa);
			redirectAttributes.addFlashAttribute("mensagem", "Pessoa salva com sucesso!");
			return "redirect:/pessoas/novo";
		} catch (IllegalArgumentException e) {
			errors.rejectValue("dataNascimento", null, e.getMessage());
			return CADASTRO_PESSOA_VIEW;
		}
	}
	
	@GetMapping
	public ModelAndView pesquisar(@ModelAttribute("filtro") PessoaFilter filtro) {
		final List<Pessoa> todasPessoasFiltradas = pessoaService.filtrar(filtro);
		
		final ModelAndView modelAndView = new ModelAndView("ListaPessoas");
		modelAndView.addObject("pessoas", todasPessoasFiltradas);
		return modelAndView;
	}
	
	@GetMapping("{id}")
	public ModelAndView edicao(@PathVariable("id") Pessoa pessoa) {
		final ModelAndView modelAndView = new ModelAndView(CADASTRO_PESSOA_VIEW);
		modelAndView.addObject(pessoa);
		return modelAndView;
	}
	
	@DeleteMapping("{id}")
	public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		pessoaService.excluir(id);
		
		redirectAttributes.addFlashAttribute("mensagem", "Pessoa excluida com sucesso!");
		return "redirect:/pessoas";
	}
}
