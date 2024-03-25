package com.llama.api.billings.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600) // change later to only accept from frontend applications
@RestController
@RequestMapping("/api/management/billing")
public class BillingManagementController {
}
