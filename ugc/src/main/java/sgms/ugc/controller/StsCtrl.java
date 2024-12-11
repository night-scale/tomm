package sgms.ugc.controller;

import sgms.ugc.dto.ApiResponse;
import sgms.ugc.sts.StsSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sts")
public class StsCtrl {
    private final StsSvc stsSvc;

    public StsCtrl(StsSvc stsSvc) {
        this.stsSvc = stsSvc;
    }

    @GetMapping("/stsToken")
    public ApiResponse<String> getStsToken() {
        String result = stsSvc.remoteGenToken();
        return ApiResponse.ok(result);
    }
}
