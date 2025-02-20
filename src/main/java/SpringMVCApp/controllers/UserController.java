package SpringMVCApp.controllers;

import SpringMVCApp.models.User;
import SpringMVCApp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users/index";
    }

    @GetMapping("/user")//страница со всеми юзерами
    public String user(@RequestParam("id") int userId, Model model) {
        User user = userService.findById(userId);
        model.addAttribute("user", user);
        return "users/show";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "users/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") User user,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/users";
        }

        userService.create(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "users/edit";
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute("user") User user,
                         BindingResult bindingResult, @RequestParam("id") int id) {
        if (bindingResult.hasErrors()) {
            return "users/edit";
        }

        userService.update(user,id);
        return "redirect:/users";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") int id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        userService.delete(user,id);
        return "redirect:/users";
    }

}
