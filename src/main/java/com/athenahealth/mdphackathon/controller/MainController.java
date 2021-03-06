package com.athenahealth.mdphackathon.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.*;

import com.athenahealth.mdphackathon.entity.Bestimate;
import com.athenahealth.mdphackathon.entity.StateCount;
import com.athenahealth.mdphackathon.service.Bestimator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class MainController {

    @RequestMapping(value = "/singleRequest", method = RequestMethod.GET)
    @ResponseBody
    public String singleRequest(@RequestParam("cpt") String cpt, @RequestParam("insurance") String insurance) {
        JSONObject json = new JSONObject();

        Bestimate result = Bestimator.getBestimate(cpt, insurance);
        json.put("insurance", insurance);
        if (result.getUniversalClaimCount() == -1) {
            json.put("universalCount", "N/A");
        } else {
            json.put("universalCount", result.getUniversalClaimCount());
        }
        if (result.getThisInsuranceClaimCount() == -1) {
            json.put("insuranceCount", "N/A");
        } else {
            json.put("insuranceCount", result.getThisInsuranceClaimCount());
        }
        json.put("universalRate", "50%");
        json.put("insuranceRate", "10%");
        return json.toJSONString();
    }

    @RequestMapping(value = "/byPayer", method = RequestMethod.GET)
    @ResponseBody
    public String byPayer(@RequestParam("cpt") String cpt) {
        JSONObject json = new JSONObject();
        json.putAll(Bestimator.getMapInsuranceToCptToCount());

        return json.toJSONString();
    }
}
