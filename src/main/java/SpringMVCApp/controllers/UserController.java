package SpringMVCApp.controllers;

import SpringMVCApp.dao.UserDAO;
import SpringMVCApp.models.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private UserDAO userDao;

    @Autowired
    public UserController(UserDAO userDao) {
        this.userDao = userDao;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users",userDao.index());
        return "users/index";
    }

    @GetMapping("/user")//страница со всеми юзерами
    public String user(@RequestParam("id") int userId, Model model) {
        User user = userDao.show(userId);
        model.addAttribute("user", user);
        return "users/show";
    }

    @GetMapping("/new")
    public String newUser (@ModelAttribute("user") User user) {
        return "users/new";
    }

    @PostMapping()
    public String create (@ModelAttribute("user") User user ,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "users/new";

        userDao.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute("user", userDao.show(id));
        return "users/edit";
    }

    @PostMapping("/edit")
    public String update (@ModelAttribute("user") User user,
                          BindingResult bindingResult, @RequestParam("id") int id ){
        if (bindingResult.hasErrors())
            return "users/edit";

        userDao.update(id,user);
        return "redirect:/users";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam ("id") int id, Model model) {
        User user = userDao.show(id);
        model.addAttribute("user", user);
        userDao.delete(id);
        return "redirect:/users";
    }

}
