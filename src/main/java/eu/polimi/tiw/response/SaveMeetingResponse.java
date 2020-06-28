package eu.polimi.tiw.response;

import eu.polimi.tiw.bean.*;

import java.util.*;

public class SaveMeetingResponse {

   private List<EmployeeBean> othersEmployeeList;
   private List<EmployeeBean> invitedEmployeeList;
   private HomePageResponse homePageResponse;

    public List<EmployeeBean> getOthersEmployeeList() {
        return othersEmployeeList;
    }

    public void setOthersEmployeeList(List<EmployeeBean> othersEmployeeList) {
        this.othersEmployeeList = othersEmployeeList;
    }

    public List<EmployeeBean> getInvitedEmployeeList() {
        return invitedEmployeeList;
    }

    public void setInvitedEmployeeList(List<EmployeeBean> invitedEmployeeList) {
        this.invitedEmployeeList = invitedEmployeeList;
    }

    public HomePageResponse getHomePageResponse() {
        return homePageResponse;
    }

    public void setHomePageResponse(HomePageResponse homePageResponse) {
        this.homePageResponse = homePageResponse;
    }
}
