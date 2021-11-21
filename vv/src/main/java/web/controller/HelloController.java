package web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.dao.DAO;
import web.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HelloController {
    private DAO dao;

    @Autowired
    public HelloController(DAO dao) {
        this.dao = dao;
    }

    @GetMapping()
    public String printWelcome(ModelMap model) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello! Mister @Semyon_Samolet!");
        messages.add("Hope, you are doing good today!)");
        messages.add("Are you really want to chek it?)))");
        messages.add("So - all yours and good luck!!)))");
        messages.add("Best regards. Vladimir");
        model.addAttribute("messages", messages);
        return "view/hello";
    }
    @ModelAttribute("newUser")
    public User getPerson(){
        return new User();
    }
    @GetMapping("/people")
    public String index(Model model){
        model.addAttribute("people",dao.getAllUsers());
        return "view/index";
    }

    @PostMapping("/people")
    public String creat(@ModelAttribute("newUser")@Valid User user,
                        BindingResult bindingResult,Model model) {
        if (bindingResult.hasErrors()){
            model.addAttribute("people",dao.getAllUsers());
            return "view/index";
        }
        dao.saveUser(user);
        return "redirect:people";
    }

    @DeleteMapping("/people/{id}")
    public String deletePerson(@PathVariable("id") int id){
        dao.removeUserById(id);
        return "redirect:/people";
    }
    @GetMapping("/people/{id}/edit")
    public String edit(@ModelAttribute("id") int id,Model model){
        model.addAttribute("user",dao.getUserById(id));
        return "view/edit";
    }

    @PatchMapping("/people/{id}")
    public String updatePerson(@ModelAttribute("user")@Valid User updateUser, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "view/edit";
        }
        dao.updateUser(updateUser);
        return "redirect:/people";
    }
}