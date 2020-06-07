package eu.polimi.tiw.response;

import eu.polimi.tiw.bean.*;

import java.util.*;

public class SaveMeetingResponse {

   private List<MeetingBean> invitedMeetingList;
   private MeetingBean newlyInsertedMeeting;

    public List<MeetingBean> getInvitedMeetingList() {
        return invitedMeetingList;
    }

    public void setInvitedMeetingList(List<MeetingBean> invitedMeetingList) {
        this.invitedMeetingList = invitedMeetingList;
    }

    public MeetingBean getNewlyInsertedMeeting() {
        return newlyInsertedMeeting;
    }

    public void setNewlyInsertedMeeting(MeetingBean newlyInsertedMeeting) {
        this.newlyInsertedMeeting = newlyInsertedMeeting;
    }
}
