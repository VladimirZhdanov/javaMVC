package com.foxminded.web.controllers;

import com.foxminded.exceptions.DAOException;
import com.foxminded.model.Group;
import com.foxminded.service.layers.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Vladimir Zhdanov (mailto:constHomeSpb@gmail.com)
 * @since 0.1
 */
@Controller
public class GroupController {

    private MessageSource messageSource;

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupController.class);

    private GroupService groupService;

    @Autowired
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping(value = {"groups"})
    public String showGroupPage(Model model) {
        model.addAttribute("group", new Group());
        return "groups";
    }

    @PostMapping(value = {"createGroup"})
    public String createGroup(@Valid @ModelAttribute("group") Group group, BindingResult errors) {
        if (errors.hasErrors()) {
            return "groups";
        } else {
            try {
                groupService.insert(group);
            } catch (DAOException e) {
                handleDAOException(e, errors);
                return "groups";
            }
            return "redirect:/groups";
        }
    }

    @GetMapping(value = {"findGroup"})
    public String findGroup(@Valid @ModelAttribute("group") Group group, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return "groups";
        } else {
            try {
                model.addAttribute("foundGroup", groupService.getByName(group.getName()));
            } catch (DAOException e) {
                handleDAOException(e, errors);
                return "groups";
            }
            return "groups";
        }
    }

    //TODO
    @PostMapping(value = {"updateGroup"})
    public String updateGroup(@Valid @ModelAttribute("group") Group group, BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            return "groups";
        } else {
            try {
                //TODO
                groupService.update(group);
            } catch (DAOException e) {
                handleDAOException(e, errors);
                return "groups";
            }
            return "groups";
        }
    }

    @PostMapping(value = {"deleteGroup"})
    public String deleteGroup(@Valid @ModelAttribute("group") Group group, BindingResult errors) {
        if (errors.hasErrors()) {
            return "groups";
        } else {
            try {
                groupService.delete(group.getName());
            } catch (DAOException e) {
                handleDAOException(e, errors);
                return "groups";
            }
            return "redirect:/groups";
        }
    }

    @ModelAttribute("groups")
    public List<Group> getGroups() {
        return groupService.getAll();
    }

    private void handleDAOException(Exception ex, BindingResult errors) {
        LOGGER.warn(ex.getMessage(), ex);
        FieldError error = new FieldError("group", "name", ex.getMessage());
        errors.addError(error);
    }
}
