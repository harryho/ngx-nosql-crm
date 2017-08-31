package com.nnc.app.cucumber.stepdefs;

import com.nnc.app.NgxNosqlCrmApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = NgxNosqlCrmApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
