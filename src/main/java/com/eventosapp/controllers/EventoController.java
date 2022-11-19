package com.eventosapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.eventosapp.models.Convidado;
import com.eventosapp.models.Evento;
import com.eventosapp.repository.ConvidadoRepository;
import com.eventosapp.repository.EventoRepository;

@Controller
public class EventoController {
	
	@Autowired //injeção de dependencia ao utilizar a interface
	private EventoRepository er;
	
	@Autowired //injeção de dependencia ao utilizar a interface
	private ConvidadoRepository cr;

	@RequestMapping(value = "/cadastrarEvento", method = RequestMethod.GET)
	public String form() {
		return "evento/formEvento";
	}
	@RequestMapping(value = "/cadastrarEvento", method = RequestMethod.POST)
	public String form(Evento evento) {
		er.save(evento);
		return "redirect:/cadastrarEvento";
	}
	
	@RequestMapping("/eventos")
	public ModelAndView listaEventos() {
		ModelAndView mv = new ModelAndView("index");
		Iterable<Evento> evento =  er.findAll();
		mv.addObject("evento", evento); //inserir o mesmo nome que foi colocado na view chamado pelo th
		return mv;
	}
	
	@RequestMapping(value= "/evento_id={id}", method=RequestMethod.GET)
	public ModelAndView detalhesEvento(@PathVariable("id") long id) {
		Evento evento = er.findById(id);
		ModelAndView mv = new ModelAndView("evento/detalhesEvento");
		mv.addObject("evento", evento);
		return mv;
	}
	
	@RequestMapping(value= "/evento_id={id}", method=RequestMethod.POST)
	public String detalhesEventoPost(@PathVariable("id") long id, Convidado convidado) {
		Evento evento = er.findById(id);
		convidado.setEvento(evento);
		cr.save(convidado);
		return "redirect:/evento_id={id}";
	}
	
}
