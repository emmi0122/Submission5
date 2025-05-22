package se.yrgo.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import se.yrgo.domain.Action;
import se.yrgo.domain.Call;
import se.yrgo.domain.Customer;
import se.yrgo.services.calls.CallHandlingService;
import se.yrgo.services.customers.CustomerManagementService;
import se.yrgo.services.customers.CustomerNotFoundException;
import se.yrgo.services.diary.DiaryManagementService;

public class SimpleClient {

    public static void main(String[] args) {
        // Del 1
        /*
         * ClassPathXmlApplicationContext container = new
         * ClassPathXmlApplicationContext("application.xml");
         * CustomerManagementService service =
         * container.getBean("CustomerManagementService",
         * CustomerManagementService.class);
         * 
         * 
         * List<Customer> allCustomers = service.getAllCustomers();
         * for (Customer customer : allCustomers) {
         * System.out.println(customer);
         * }
         * 
         * container.close();
         */

        // Del 2
        /*ClassPathXmlApplicationContext container = new ClassPathXmlApplicationContext("application.xml");
        
        CallHandlingService callService = container.getBean("CallHandlingService", CallHandlingService.class);

        Call call = new Call("Test call from client");

        Calendar requiredBy = Calendar.getInstance();
        requiredBy.add(Calendar.DAY_OF_YEAR, 7);

        Action action1 = new Action("Follow up", requiredBy, "user1");
        Action action2 = new Action("Send email", requiredBy, "user1");

        try {
            callService.recordCall("OB74", call, Arrays.asList(action1, action2));
            System.out.println("Samtal och actions registrerade!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        container.close();*/

        //Del 3
        ClassPathXmlApplicationContext container = new ClassPathXmlApplicationContext("application.xml");

		CustomerManagementService customerService = container.getBean(CustomerManagementService.class);
		CallHandlingService callService = container.getBean(CallHandlingService.class);
		DiaryManagementService diaryService = container.getBean(DiaryManagementService.class);

		customerService.newCustomer(new Customer("CS03939", "Acme", "Good Customer"));

		Call newCall = new Call("Larry Wall called from Acme Corp");
		Action action1 = new Action("Call back Larry to ask how things are going", new GregorianCalendar(2016, 0, 0), "rac");
		Action action2 = new Action("Check our sales dept to make sure Larry is being tracked", new GregorianCalendar(2016, 0, 0), "rac");

		List<Action> actions = new ArrayList<Action>();
		actions.add(action1);
		actions.add(action2);

		try{
			callService.recordCall("CS03939", newCall, actions);
		}catch (CustomerNotFoundException e){
			System.out.println("That customer doesn't exist");
		}

		System.out.println("Here are the outstanding actions:");
		Collection<Action> incompleteActions = diaryService.getAllIncompleteActions("rac");
		for (Action next: incompleteActions){
			System.out.println(next);
		}

		container.close();
    }
}