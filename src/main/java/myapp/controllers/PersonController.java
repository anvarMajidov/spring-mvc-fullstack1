package myapp.controllers;

import myapp.dao.PersonDao;
import myapp.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class PersonController {
    private final PersonDao personDao;
    @Autowired
    PersonController(PersonDao personDao) {
        this.personDao = personDao;
    }

    @GetMapping("/")
    public String indexPage(Model model) {
        model.addAttribute("people", personDao.getPeople());
        return "person/index";
    }

    @GetMapping("/add")
    public String addPage(Model model) {
        model.addAttribute("person", new Person());
        return "person/add";
    }

    @PostMapping("/add")
    public String addPost(@ModelAttribute("person") @Valid Person person,
                          BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return "person/add";

        personDao.addPerson(person);
        return "redirect:/";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDao.getPersonWithId(id));
        return "person/edit";
    }

    @PatchMapping("/edit/{id}")
    public String editPerson(@PathVariable("id") int id ,@ModelAttribute("person") @Valid Person person,
                             BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return "person/edit";

        personDao.editPerson(person);
        return "redirect:/";
    }

    @DeleteMapping("/{id}/delete")
    public String deletePerson(@PathVariable("id") int id) {
        personDao.deletePerson(id);
        return "redirect:/";
    }
}
