package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view

        Job someJob = jobData.findById(id);
        model.addAttribute("job", someJob);

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        if (errors.hasErrors()) {
            model.addAttribute("jobForm", jobForm);
            return "new-job";
        }

        String newName = jobForm.getName();
        int newEmployerId = jobForm.getEmployerId();
        int newLocationId = jobForm.getLocationId();
        int newPositionTypeId = jobForm.getPositionTypeId();
        int newCoreCompetencyId = jobForm.getCoreCompetencyId();


        Job newJob = new Job(newName, jobData.getEmployers().findById(newEmployerId),
                jobData.getLocations().findById(newLocationId), jobData.getPositionTypes().findById(newPositionTypeId),
                jobData.getCoreCompetencies().findById(newCoreCompetencyId));

        jobData.add(newJob);

        return "redirect:/job?id=" + newJob.getId();

    }
}
