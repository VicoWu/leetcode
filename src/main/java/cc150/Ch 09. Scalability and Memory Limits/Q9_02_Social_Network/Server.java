package Q9_02_Social_Network;

import java.util.HashMap;

public class Server {
	HashMap<Integer, Machine> machines = new HashMap<Integer, Machine>(); //所有的服务器
	HashMap<Integer, Integer> personToMachineMap = new HashMap<Integer, Integer>(); //用户id到服务器id的映射关系
	
	public Machine getMachineWithId(int machineID) {
		return machines.get(machineID);
	}
	
	public int getMachineIDForUser(int personID) {
		Integer machineID = personToMachineMap.get(personID);
		return machineID == null ? -1 : machineID;
	}
	
	public Person getPersonWithID(int personID) {
		Integer machineID = personToMachineMap.get(personID);
		if (machineID == null) {
			return null;
		}
		Machine machine = getMachineWithId(machineID);
		if (machine == null) {
			return null;
		}
		return machine.getPersonWithID(personID);
	}
}
