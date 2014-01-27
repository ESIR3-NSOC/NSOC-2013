package esir.dom13.nsoc.googleCalendar.research;

import com.google.gdata.client.calendar.*;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarEventFeed;
import com.google.gdata.data.extensions.When;
import com.google.gdata.data.extensions.Where;
import com.google.gdata.util.*;

import java.net.*;
import java.io.*;


public class googleCalendar {

	/**
	 * @param args
	 * @throws com.google.gdata.util.ServiceException
	 * @throws java.io.IOException
	 */
	public static void main(String[] args) throws IOException, ServiceException {
        
        
      
		String mail = "projet.nsoc2013@gmail.com";
		String pw = "esir2013";
		
		/*Is Authorised Test*/
		
		String promo = "ESIR3";
		String option = "DOM";
		String speciality = "DOM-NSH";
		//DateTime start = DateTime.parseDateTime("2013-12-19T08:58:00+01:00");
		//DateTime start = DateTime.now();
		
		//Bat 41
		String urlRoom = "https://www.google.com/calendar/feeds/a4thv8s0785u7mo5370c8g39bvnt8jdj@import.calendar.google.com/private/full";
		boolean bool = isAuthorized(mail, pw, urlRoom, promo, option, speciality);
		if(bool){
			System.out.println("Unlock");
		}else{
			System.out.println("Lock");
		}
		
		/*Create Event Test*/
		
//		String title = "My event title.";
//		String content = "My details for this event : \n Les d�tails.";
//		DateTime startTime = DateTime.parseDateTime("2013-12-17T08:00:00");
//		DateTime endTime = DateTime.parseDateTime("2013-12-17T15:00:00");
//		String where = "B41-001";
//		createEvent(mail, pw, title, content, where, startTime, endTime);
       
	
	
	
	
	
	}

	/**
	 * To know if the person is authorised or not to access the room
	 * @param mail
	 * @param pw
	 * @param speciality
	 * @return
	 * @throws java.io.IOException
	 * @throws com.google.gdata.util.ServiceException
	 */
	public static boolean isAuthorized(String mail, String pw, String urlCalendar, String promo, String option, String speciality) throws IOException, ServiceException{

		boolean isAutho = false;
		URL feedUrl = new URL(urlCalendar);
		CalendarService myService = new CalendarService("Calendar ADE");
		myService.setUserCredentials(mail, pw);
		
		
		CalendarQuery myQuery = new CalendarQuery(feedUrl);
		//Current time
		DateTime time = new DateTime(DateTime.now().getValue()+3600000);
		//endTime = startTime + 1 minute
		DateTime end = new DateTime(time.getValue()+36000000);
		myQuery.setMinimumStartTime(time);
		myQuery.setMaximumStartTime(end);

		
		// Send the request and receive the response:
		CalendarEventFeed myResultsFeed = myService.query(myQuery,CalendarEventFeed.class);
		if (myResultsFeed.getEntries().size() > 0) {
			System.out.println("SIZE : " + myResultsFeed.getEntries().size());
			for(int i=0; i<myResultsFeed.getEntries().size();i++){
				CalendarEventEntry firstMatchEntry = (CalendarEventEntry)
						myResultsFeed.getEntries().get(i);

				String myEntryTitle = firstMatchEntry.getTitle().getPlainText();
				String myEntryWhere = firstMatchEntry.getLocations().get(0).getValueString();
				String myEntryContent = firstMatchEntry.getPlainTextContent();
                String[] test = myEntryContent.split("\n");
                System.out.println("prof :::  :::  ::: "+test[test.length-2]);
				System.out.println("\n\nTitle: " + myEntryTitle + "\nWhere: " + myEntryWhere + "\nDescription: " + myEntryContent);
				if(myEntryContent.contains(promo)){
					if(myEntryContent.contains(speciality)){
						System.out.println("AUTHORISED");
						return true;
					}else{
						if(myEntryContent.contains(option)){
							System.out.println("AUTHORISED");
							return true;
						}
					}
				}
			}
		}else{
			System.out.println("NOT AUTHORISED");
			return false;
		}
		return isAutho;
	}
	
	/**
	 * Create an event
	 * @param mail
	 * @param pw
	 * @param title
	 * @param content
	 * @param where
	 * @param start - StartTime example : 2013-12-04T08:00:00
	 * @param end
	 * @throws java.io.IOException
	 * @throws com.google.gdata.util.ServiceException
	 */
	public static void createEvent(String mail, String pw, String title, String content, String where, DateTime start, DateTime end) throws IOException, ServiceException{
		URL postUrl;
		
		postUrl = new URL("https://www.google.com/calendar/feeds/9u96e3jug29acreg69kc80c00s@group.calendar.google.com/private/full");
		CalendarService myService = new CalendarService("Calendar ADE");
		myService.setUserCredentials(mail, pw);
		
		
		CalendarEventEntry myEntry = new CalendarEventEntry();

		myEntry.setTitle(new PlainTextConstruct(title));
		myEntry.setContent(new PlainTextConstruct(content));

		When eventTimes = new When();
		eventTimes.setStartTime(start);
		eventTimes.setEndTime(end);
		myEntry.addTime(eventTimes);
		
		Where location = new Where();
		location.setValueString(where);
		myEntry.addLocation(location);

		// Send the request and receive the response:
		CalendarEventEntry insertedEntry = myService.insert(postUrl, myEntry);
	}

}
