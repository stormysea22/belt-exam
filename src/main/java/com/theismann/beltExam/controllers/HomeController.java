package com.theismann.beltExam.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.theismann.beltExam.models.Idea;
import com.theismann.beltExam.models.User;
import com.theismann.beltExam.services.AppService;
import com.theismann.beltExam.validation.UserValidator;



@Controller
public class HomeController {
	
	private final UserValidator userValidator;
	private final AppService appService;
	
	public HomeController(AppService appService, UserValidator userValidator) {
		this.appService = appService;
		this.userValidator = userValidator;
	}
	
	
	
	//**********************
	//  Events dashboard
	//**********************
	
	@RequestMapping("/ideas")
	public String dashboard(HttpSession session, Model model) {
		Long id = (Long)session.getAttribute("userid");
		User loggedinuser = this.appService.findUserById(id);		
		model.addAttribute("loggedinuser", loggedinuser);
		List<Idea> allIdeas=appService.findAll();
		model.addAttribute("allIdeas", allIdeas);
		return "dashboard.jsp";
	}
//	
	
	//**********************
	//  Events create--post
	//**********************
	
	@RequestMapping(value="/ideas/new")
	public String newIdea(@ModelAttribute("idea") Idea idea) {
		return "new.jsp";
	}
	
	
	@PostMapping(value="/ideas/create")
	public String createIdea(@Valid @ModelAttribute("idea") Idea idea, BindingResult result, HttpSession session, Model model) {
		Long id = (Long) session.getAttribute("userid");
		User loggedinuser = this.appService.findUserById(id);
		if(result.hasErrors()) {
			
			return "new.jsp";
		} else{
		
		idea.setCreator(loggedinuser);
		appService.createIdea(idea);
		return "redirect:/ideas";
		}
}

//	
//	//**********************************
//	// liking and unliking
	//**********************************
	
	@RequestMapping("/ideas/{id}/like")
	public String like(@PathVariable("id")Long id, HttpSession session) {
		Idea idea = appService.findIdeaById(id);
		Long loggedinuserid = (Long)session.getAttribute("userid");
		User loggedinuser = this.appService.findUserById(loggedinuserid);
		
		List<User> likedIdeas=idea.getLikedUser();
		likedIdeas.add(loggedinuser);
		appService.updateIdea(idea);
		
		return "redirect:/ideas";
	}
	
	@RequestMapping("/ideas/{id}/unlike")
	public String unlike(@PathVariable("id")Long id, HttpSession session) {
		Idea idea = appService.findIdeaById(id);
		Long loggedinuserid = (Long)session.getAttribute("userid");
		User loggedinuser = this.appService.findUserById(loggedinuserid);
		
		List<User> likedIdeas=idea.getLikedUser();
		likedIdeas.remove(loggedinuser);
		appService.updateIdea(idea);
		
		return "redirect:/ideas";
	}
	
	
	
	
//	@RequestMapping(value="/events/{event_id}/join")
//	public String addAttendee(@PathVariable("event_id") Long event_id, HttpSession session) {
//		User attendee = appService.findUserById((Long)session.getAttribute("userid"));
//		Event attending_event = appService.findEventById(event_id);
//		List<User> attendees = attending_event.getAttendees();
//		attendees.add(attendee);
//		attending_event.setAttendees(attendees);
//		appService.updateUser(attendee);
//		return "redirect:/events";
//	}
//	
//	@RequestMapping(value="/events/{event_id}/remove")
//	public String removeAttendee(@PathVariable("event_id") Long event_id, HttpSession session) {
//		
//		User attendee = appService.findUserById((Long)session.getAttribute("userid"));
//    	Event attending_event = appService.findEventById(event_id);
//		List<User> attendees = attending_event.getAttendees();
//		attendees.remove(attendee);
//		attending_event.setAttendees(attendees);
//		appService.updateUser(attendee);
//		return "redirect:/events";
//	}
	
	//**********************************
	// Delete and update Event
	//**********************************	
//	@RequestMapping("/ideas/edit/{id}")
//	public String editIdea(@PathVariable("id") Long id, Model model, HttpSession session) {
//		
//				
//		model.addAttribute("idea", this.appService.findIdeaById(id));
//		
//		model.addAttribute("allusers", this.appService.findAllUsers() );
//		return "edit.jsp";
//	}
	
	@RequestMapping("/ideas/edit/{id}")
	public String editIdea(@PathVariable("id") Long id, Model model, HttpSession session) {
		Idea idea = appService.findIdeaById(id);
		model.addAttribute("idea", idea);
		
		Long loggedinuserid = (Long)session.getAttribute("userid");
//		User loggedinuser = this.appService.findUserById(loggedinuserid);
		
		if(idea.getCreator().getId().equals(loggedinuserid)) {
			return "edit.jsp";
	} else {
		return "redirect:/ideas";
	}
	}
	
	@PostMapping("/ideas/update/{id}")
	public String update(@PathVariable("id") Long id, @Valid @ModelAttribute("idea") Idea idea, BindingResult result, Model model, HttpSession session ) {
		if(result.hasErrors()) {
			
			model.addAttribute("allusers", this.appService.findAllUsers() );
			
			return "edit.jsp";
		}
		
		Idea i = this.appService.findIdeaById(id);
		idea.setLikedUser(i.getLikedUser());
//		
		
		Long loggedinuserid = (Long)session.getAttribute("userid");
		User loggedinuser = this.appService.findUserById(loggedinuserid);
		
		idea.setCreator(loggedinuser);
	
		
		this.appService.updateIdea(idea);
		
		return "redirect:/ideas";
	}
	
	@RequestMapping("/ideas/delete/{id}")
	public String deleteIdea(@PathVariable("id")Long id) {
		Idea i = this.appService.findIdeaById(id);
		this.appService.deleteIdea(i);
		
		return "redirect:/ideas";
	}
	
	//**********************************
	// Show Event
	//**********************************
	
	@RequestMapping(value="/ideas/{id}")
	public String showIdea(@PathVariable("id") Long id, Model model, HttpSession session) {
		Long loggedinuserid = (Long)session.getAttribute("userid");
		User loggedinuser = this.appService.findUserById(loggedinuserid);
		model.addAttribute("loggedinuser", loggedinuser);
		model.addAttribute("ideaToshow", this.appService.findIdeaById(id));
		return "show.jsp";
	}
	
	
	//**********************************
	// Login and Reg
	//**********************************
		
	@RequestMapping("/")
	public String index(@ModelAttribute("user") User user) {
		return "login.jsp";
	}
	
	@PostMapping("/registration")
	public String register(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session) {
		
		userValidator.validate(user, result);
		if(result.hasErrors() ) {
			return "login.jsp";
		}
		
		//add validation for duplicate emails
		
		User u = this.appService.registerUser(user);
		session.setAttribute("userid", u.getId());
		
		return "redirect:/ideas";
	}
	
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	@RequestMapping("/login")
	public String login(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session, RedirectAttributes redirectAttributes) {
		Boolean isLegit = this.appService.authenticateUser(email, password);
		
		if(isLegit) {
			
			User user = this.appService.findByEmail(email);
			session.setAttribute("userid", user.getId());
			return "redirect:/ideas";
		}
		redirectAttributes.addFlashAttribute("error", "Invalid login Attempt");
		return "redirect:/";
	}
	
	
}
