package com.labreportapp.labreport.core.common;

import com.labreportapp.labreport.infrastructure.constant.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author thangncph26123
 */

@Controller
public final class MetaController {

    @GetMapping("/version")
    public String getVersion(Model model) {
        model.addAttribute("version", Constants.VERSION);
        return "version";
    }
}
