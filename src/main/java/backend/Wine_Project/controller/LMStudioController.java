package backend.Wine_Project.controller;

import backend.Wine_Project.service.LMStudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LMStudioController {


        private final LMStudioService lmStudioService;

        @Autowired
        public LMStudioController(LMStudioService lmStudioService) {
            this.lmStudioService = lmStudioService;
        }

        @PostMapping("/process-text")
        public String processText(@RequestBody String text) {
            return lmStudioService.callLocalLMStudio(text);
        }

}
