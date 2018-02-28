package Q7_02_Call_Center;

import java.util.ArrayList;
import java.util.List;

/* CallHandler represents the body of the program,
 * and all calls are funneled first through it. 
 */
public class CallHandler {	
	/* We have 3 levels of employees: respondents, managers, directors. */
    private final int LEVELS = 3; 
    
    /* Initialize with 10 respondents, 4 managers, and 2 directors. */
    private final int NUM_RESPONDENTS = 10;
    private final int NUM_MANAGERS = 4;
    private final int NUM_DIRECTORS = 2;

    /* List of employees, by level.
     * employeeLevels[0] = respondents
     * employeeLevels[1] = managers
     * employeeLevels[2] = directors
     */
    List<List<Employee>> employeeLevels;

	/* queues for each call�s rank */
    List<List<Call>> callQueues; 

    public CallHandler() {
    	employeeLevels = new ArrayList<List<Employee>>(LEVELS);
    	callQueues = new ArrayList<List<Call>>(LEVELS); 
    	
        // Create respondents.
        ArrayList<Employee> respondents = new ArrayList<Employee>(NUM_RESPONDENTS);
        for (int k = 0; k < NUM_RESPONDENTS - 1; k++) {
            respondents.add(new Respondent(this));
        }
        employeeLevels.add(respondents);

        // Create managers.
        ArrayList<Employee> managers = new ArrayList<Employee>(NUM_MANAGERS);
        managers.add(new Manager(this));
        employeeLevels.add(managers);

        // Create directors.
        ArrayList<Employee> directors = new ArrayList<Employee>(NUM_DIRECTORS);
        directors.add(new Director(this));
        employeeLevels.add(directors);
    }
    
    /* Gets the first available employee who can handle this call. */
    public Employee getHandlerForCall(Call call) {
        for (int level = call.getRank().getValue(); level < LEVELS - 1; level++) {//从较低的LEVEL开始遍历，寻找一个空闲的Employee，注意，每一个Call对象都有一个最低的level，因此接待者的level必须等于或者大于Call的level
            List<Employee> employeeLevel = employeeLevels.get(level);
            for (Employee emp : employeeLevel) {
                if (emp.isFree()) {
                    return emp;
                }
            }
        }
        return null;
    }

    /* Routes the call to an available employee, or saves in a queue if no employee available. */
    public void dispatchCall(Caller caller) {
    	Call call = new Call(caller);
    	dispatchCall(call);
    }
    
    /* Routes the call to an available employee, or saves in a queue if no employee available. */
    public void dispatchCall(Call call) {
    	/* Try to route the call to an employee with minimal rank. */
        Employee emp = getHandlerForCall(call); //对于一个呼叫，按照respondent -> manager -> director 的顺序找到一个处理者
        if (emp != null) { //找到了
        	emp.receiveCall(call);
        	call.setHandler(emp);
        } else {
	        /* Place the call into corresponding call queue according to its rank. */
	        call.reply("Please wait for free employee to reply"); //没有找到，则放入到等待队列
	        callQueues.get(call.getRank().getValue()).add(call);
        }
    }    

    /* An employee got free. Look for a waiting call that he/she can serve. Return true
     * if we were able to assign a call, false otherwise. */
    public boolean assignCall(Employee emp) {
        /* Check the queues, starting from the highest rank this employee can serve. */
        for (int rank = emp.getRank().getValue(); rank >= 0; rank--) { //某一个级别的Employee，只有权限处理级别等于或者低于自己级别的呼叫
            List<Call> que = callQueues.get(rank);
            
            /* Remove the first call, if any */
            if (que.size() > 0) {
	            Call call = que.remove(0); 
	            if (call != null) {
	                emp.receiveCall(call);
	                return true;
	            }
            }
        }
        return false;
    }
}


