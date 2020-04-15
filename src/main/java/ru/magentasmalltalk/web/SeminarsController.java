package ru.magentasmalltalk.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.magentasmalltalk.db.SeminarsDAO;
import ru.magentasmalltalk.model.Seminar;
import ru.magentasmalltalk.web.viewmodels.SeminarViewModel;
import ru.magentasmalltalk.web.viewmodels.SeminarsListViewModel;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Controller
public class SeminarsController {

    @Autowired
    private SeminarsDAO seminarsDAO;

    @ModelAttribute("form")
    public SeminarViewModel createSeminarViewModel() {
        SeminarViewModel seminarViewModel = new SeminarViewModel();
        seminarViewModel.setId(0);
        seminarViewModel.setDate(new Date());
        seminarViewModel.setTopic("");
        seminarViewModel.setDescription("");
        seminarViewModel.setAuditory("");
        seminarViewModel.setPlacesNumber(0);
        return seminarViewModel;
    }

    @ModelAttribute("list")
    public SeminarsListViewModel createSeminarsListViewModel() {
        SeminarsListViewModel seminarsListViewModel = new SeminarsListViewModel();
        seminarsListViewModel.setSeminars(new LinkedList());
        return seminarsListViewModel;
    }

    @GetMapping(path = "/seminar")
    public String getEditSeminarForm(@RequestParam(value = "id", required = false) int id,
                                     @ModelAttribute("form") SeminarViewModel form,
                                     BindingResult validationResult,
                                     ModelMap modelMap,
                                     HttpSession session) {
        if (session.getAttribute("userId") != null) {
            return "redirect:/";
        }

        if (id != 0) {
            Seminar seminar = null;
            try {
                seminar = seminarsDAO.findSeminarById(id);
            } catch (Throwable cause) {
                validationResult.addError(new FieldError("form", "id", "Seminar with id=" + id + " does not exist"));
            }

            if (seminar == null) {
                validationResult.addError(new FieldError("form", "id", "Seminar with id=" + id + " does not exist"));
            }
        }

        return "seminars/editSeminar";
    }

    @GetMapping(path = "/seminars")
    public String getSeminarsList(@ModelAttribute("list") SeminarsListViewModel seminarsListViewModel,
                                  BindingResult validationResult,
                                     ModelMap modelMap,
                                     HttpSession session) {
        if (session.getAttribute("userId") != null) {
            return "redirect:/";
        }

        List<Seminar> seminars = null;
        try {
            seminars = seminarsDAO.getAllSeminars();
        } catch (Throwable cause) {
            validationResult.addError(new FieldError("form", "seminars", "Can't obtain list of seminars"));
        }

        if (seminars == null) {
            validationResult.addError(new FieldError("form", "seminars", "Can't obtain list of seminars"));
        }

        if (validationResult.hasErrors()) {
            return "seminars/seminarDetails";
        }

        for (int i = 0; i < seminars.size(); ++i) {
            Seminar seminar = seminars.get(i);

            SeminarViewModel seminarViewModel = new SeminarViewModel();
            seminarViewModel.setId(seminar.getId());
            seminarViewModel.setDate(seminar.getDate());
            seminarViewModel.setTopic(seminar.getTopic());
            seminarViewModel.setDescription(seminar.getDescription());
            seminarViewModel.setAuditory(seminar.getAuditory());
            seminarViewModel.setPlacesNumber(seminar.getPlacesNumber());

            seminarsListViewModel.getSeminars().add(seminarViewModel);
        }

        return "seminars/seminarDetails";
    }

    @GetMapping(path = "/seminarDetails")
    public String getSeminarDetails(@RequestParam(value = "id") int id,
                                  BindingResult validationResult,
                                  ModelMap modelMap,
                                  HttpSession session) {
return "";
    }

    @PostMapping(path = "/seminar")
    public String createOrUpdateSeminar(@Validated
                                        @ModelAttribute("form") SeminarViewModel form,
                                        BindingResult validationResult,
                                        ModelMap modelMap,
                                        HttpSession session) {

        if (session.getAttribute("userId") != null) {
            return "redirect:/";
        }

        if (validationResult.hasErrors()) {
            return "seminars/editSeminar";
        }

        Seminar seminar = null;

        if (form.getId() == 0){
            // create a new seminar
            try {
                seminar = seminarsDAO.createSeminar(form.getDate(), form.getTopic(), form.getDescription(), form.getAuditory(), form.getPlacesNumber());
            } catch (Throwable ex) {}
        } else {
            // edit existing seminar
            Seminar seminarEntity = new Seminar();
            seminarEntity.setId(form.getId());
            seminarEntity.setDate(form.getDate());
            seminarEntity.setTopic(form.getTopic());
            seminarEntity.setDescription(form.getDescription());
            seminarEntity.setAuditory(form.getAuditory());
            seminarEntity.setPlacesNumber(form.getPlacesNumber());
            try {
                seminar = seminarsDAO.updateSeminar(seminarEntity);
            } catch (Throwable ex) {}
        }

        if (seminar == null) {
            validationResult.addError(new FieldError("form", "id", "Operation failed"));
        }

        if (validationResult.hasErrors()) {
            return "seminars/editSeminar";
        }

        return "redirect:/";
    }
}
