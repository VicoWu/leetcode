package Q7_02_Call_Center;

/* Employee is a super class for the Director, Manager, and Respondent classes. It is implemented as an
 * abstract class, since there should be no reason to instantiated an Employee type directly.
 */
abstract class Employee {
	private Call currentCall = null; //当前正在进行的一次呼叫
	protected Rank rank;//我的级别
	private CallHandler callHandler; //整个呼叫系统的句柄

	public Employee(CallHandler handler) {
		callHandler = handler;
	}

	/* Start the conversation */
	public void receiveCall(Call call) { //开始接听一个会话
		currentCall = call;
	}

	/* the issue is resolved, finish the call */
	public void callCompleted() {
		if (currentCall != null) {
			/* Disconnect the call. */
			currentCall.disconnect();

			/* Free the employee */
			currentCall = null;
		}

		/* Check if there is a call waiting in queue */
		assignNewCall(); //服务结束，自己从等待队列里面取出一个来
	}

	/*
	 * The issue has not been resolved. Escalate the call, and assign a new call
	 * to the employee.
	 */
	public void escalateAndReassign() {
		if (currentCall != null) {
			/* escalate call */
			currentCall.incrementRank();
			callHandler.dispatchCall(currentCall); //不呼叫分派给别人

			/* free the employee */
			currentCall = null;
		}

		/* assign a new call */
		assignNewCall();//重新获取一个等待着的呼叫
	}

	/* Assign a new call to an employee, if the employee is free. */
	public boolean assignNewCall() {
		if (!isFree()) {
			return false;
		}
		return callHandler.assignCall(this);
	}

	/* Returns whether or not the employee is free. */
	public boolean isFree() {
		return currentCall == null;
	}

	public Rank getRank() {
		return rank;
	}
}
