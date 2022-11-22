package com.example.inztagram.utility;

import com.example.inztagram.Service.LocalAuthService;
import com.example.inztagram.Service.apiService.RetrofitService;

public class EndpointBuilder {
    public static String getImageUrl(String fileId) {
        String userIdToReplace = "<enter-userId-here>";
        String fileIdNameToReplace = "<enter-fileId-here>";
        String endpoint = RetrofitService.baseURL+"fetch-image-query?userId="+userIdToReplace+"&fileId="+fileIdNameToReplace;
        String userId = LocalAuthService.getInstance().getSecretKey();
        endpoint = endpoint.replace(userIdToReplace, userId);
        endpoint = endpoint.replace(fileIdNameToReplace, fileId);
        return endpoint;
    }
}
