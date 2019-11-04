package com.young.controller;

import com.young.Pager;
import com.young.domain.KsCase;
import com.young.domain.KsUser;
import com.young.service.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/case")
public class CaseController {

    @Autowired
    CaseService caseService;

    @RequestMapping("/toCase")
    public ModelAndView toCase(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("/case");
    }

    @RequestMapping("/toMyCase")
    public ModelAndView toMyCase(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView model = new ModelAndView("/mycaselist");
        KsUser u = (KsUser) request.getSession().getAttribute("young");
        if (null == u) return new ModelAndView("/error");
        model.addObject("userID", u.getUserID());
        return model;
    }

    @RequestMapping("/subCase")
    @ResponseBody
    public int subCase(HttpServletRequest request, HttpServletResponse response) {
        String companyName = request.getParameter("companyName");
        String dept = request.getParameter("dept");
        String name = request.getParameter("name");
        String mobile = request.getParameter("mobile");
        String problem = request.getParameter("problem");
        String fileName = request.getParameter("extra");
        KsCase tempCase = new KsCase();
        tempCase.setCompanyName(companyName);
        tempCase.setDept(dept);
        tempCase.setName(name);
        tempCase.setMobile(mobile);
        tempCase.setProblem(problem);
        tempCase.setFilePath(fileName);
        tempCase.setInsertTime(new Date());
        tempCase.setStatus(0);
        KsUser ksUser = (KsUser) request.getSession().getAttribute("young");
        if (null != ksUser) {
            tempCase.setSubUser(ksUser.getUserID());
        }
        return caseService.saveCase(tempCase);
    }

    @RequestMapping("/replyCase")
    @ResponseBody
    public int replyCase(HttpServletRequest request, HttpServletResponse response) {
        String reply = request.getParameter("reply");
        String caseID = request.getParameter("caseID");
        KsCase tempCase = caseService.getCaseById(Long.parseLong(caseID));
        tempCase.setReply(reply);
        tempCase.setStatus(1);
        KsUser ksUser = (KsUser) request.getSession().getAttribute("young");
        if (null != ksUser) tempCase.setReplyUser(ksUser.getLoginName());
        return caseService.saveCase(tempCase);
    }

    @RequestMapping("/getPageList")
    @ResponseBody
    public Map getPageList(HttpServletRequest request, HttpServletResponse response) {
        Map resultMap = new HashMap();
        String companyName = StringUtils.isEmpty(request.getParameter("companyName")) ? null : request.getParameter("companyName");
        String dept = StringUtils.isEmpty(request.getParameter("dept")) ? null : request.getParameter("dept");
        String mobile = StringUtils.isEmpty(request.getParameter("mobile")) ? null : request.getParameter("mobile");
        Integer status = StringUtils.isEmpty(request.getParameter("status")) ? null : Integer.parseInt(request.getParameter("status"));
        String name = StringUtils.isEmpty(request.getParameter("name")) ? null : request.getParameter("name");
        Long userID = null == request.getParameter("userID") ? null : Long.parseLong(request.getParameter("userID"));
        int pageIndex = null == request.getParameter("page") ? 1 : Integer.parseInt(request.getParameter("page"));
        int pageSize = null == request.getParameter("limit") ? 10 : Integer.parseInt(request.getParameter("limit"));
        Pager<KsCase> list = caseService.getCaseListForPage(companyName, dept, mobile, status, userID, name, pageIndex, pageSize);
        resultMap.put("total", list.getTotalCount());
        resultMap.put("rows", list.getList());
        return resultMap;
    }

    @RequestMapping("/toList")
    public ModelAndView toList(HttpServletRequest request, HttpServletResponse response) {
        if (null == request.getSession().getAttribute("young")) return new ModelAndView("/error");
        return new ModelAndView("/caselist");
    }

    @RequestMapping("/toCaseDetail")
    public ModelAndView toCaseDetail(HttpServletRequest request, HttpServletResponse response) {
        KsUser u = (KsUser) request.getSession().getAttribute("young");
        if (null == u) return new ModelAndView("/error");
        ModelAndView model = new ModelAndView("/casedetail");
        Long caseID = Long.parseLong(request.getParameter("caseID"));
        KsCase c = caseService.getCaseById(caseID);
        model.addObject("idAdmin", 1 == u.getRole() ? 1 : 0);
        model.addObject("caseDetail", c);
        return model;
    }
}
