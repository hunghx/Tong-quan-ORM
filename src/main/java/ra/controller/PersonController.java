package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ra.model.dto.PersonDtoForm;
import ra.model.service.IPersonService;

@Controller
public class PersonController {
    @Autowired
    private IPersonService personService;
    @GetMapping
    public String home(Model model){
        model.addAttribute("list",personService.findAll());
        return "home";
    }
    @GetMapping("/add")
    public ModelAndView add(){
        return new ModelAndView("add","person", new PersonDtoForm());
    }
     @GetMapping("/edit/{id}")
    public ModelAndView findById(@PathVariable Long id){
        return new ModelAndView("edit","person", personService.findByID(id));
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        personService.delete(id);
        return "redirect:/";
    }
    @PostMapping("/add")
    public String doAdd(@ModelAttribute PersonDtoForm person){
        personService.save(person);
        return "redirect:/";
    }
    @PostMapping("/update")
    public String doUpdate(@ModelAttribute PersonDtoForm person){
        personService.save(person);
        return "redirect:/";
    }
}
