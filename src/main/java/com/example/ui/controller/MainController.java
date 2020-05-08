package com.example.ui.controller;

import com.example.ui.service.AllModelsService;
import com.example.ui.service.FirstModelService;
import com.example.ui.service.SecondModelService;
import model.AllModels;
import model.firstmodel.FirstModel;
import model.secondmodel.SecondModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.concurrent.ExecutionException;

@Controller
public class MainController {

    @Autowired
    private FirstModelService firstModelService;

    @Autowired
    private AllModelsService allModelsService;

    @Autowired
    private SecondModelService secondModelService;

    @GetMapping("/")
    public String getHomePage() {
        return "home";
    }

    @GetMapping("/protected")
    public String getProtectedPage() {
        return "protected";
    }

    @GetMapping("/firstService")
    public String getFirstService(Model model) throws ExecutionException, InterruptedException {
        FirstModel firstModel = firstModelService.getFirstModel();
        model.addAttribute("firstModel", firstModel);
        return "firstService";

    }

    @GetMapping("/secondService")
    public String getSecondService(Model model) {
        SecondModel secondModel = secondModelService.getSecondModel();
        model.addAttribute("secondModel", secondModel);
        return "secondService";

    }

    @GetMapping("/allModelsViaFirstService")
    public String callFirstServiceThatCallsSecondService(Model model) {
        AllModels allModels = allModelsService.getAllModelsViaFirstService();
        model.addAttribute("allModels", allModels);
        return "allModels";
    }


    @GetMapping("/allModelsFromUi")
    public String callFirstServiceThanCallSecondService(Model model) throws ExecutionException, InterruptedException {
        FirstModel firstModel = firstModelService.getFirstModel();
        SecondModel secondModel = secondModelService.getSecondModel();
        AllModels allModels = new AllModels(firstModel,secondModel);
        model.addAttribute("allModels", allModels);
        return "allModels";
    }

    @GetMapping("/allModelsViaSecondService")
    public String callSecondServiceThanCallFirstService(Model model) {
        AllModels allModels = allModelsService.getAllModelsViaSecondService();
        model.addAttribute("allModels", allModels);
        return "allModels";
    }

    @ExceptionHandler({ExecutionException.class, InterruptedException.class})
    public void exceptionHandler() {
        System.out.println("----------- ExecutionException or InterruptedException");
    }

}
